package it.unibo.oop.hearthcode.view.utility;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.ImageIcon;

/**
 * Proxy exposing synchronous access and background preloading over an {@link ImageRepository}.
 */
public final class ImageLoaderProxy {

    private static final int DOWNSCALE_STEP_FACTOR = 2;
    private final ImageRepository repository;
    private final Map<String, CompletableFuture<ImageIcon>> sizedCache = new ConcurrentHashMap<>();

    /**
     * Creates the proxy.
     *
     * @param repository the underlying image repository
     */
    public ImageLoaderProxy(final ImageRepository repository) {
        this.repository = repository;
    }

    /**
     * Loads a raw image synchronously.
     * 
     * @param path the resource path
     * @return the loaded raw image
     */
    public ImageIcon load(final String path) {
        return this.loadAsync(path).join();
    }

    /**
     * Loads an image synchronously as an icon with the requested logical size.
     * 
     * @param path the resource path
     * @param width the logical icon width
     * @param height the logical icon height
     * @return the loaded size-aware icon
     */
    public ImageIcon load(final String path, final int width, final int height) {
        return this.loadAsync(path, width, height).join();
    }

    /**
     * Loads a raw image asynchronously.
     * 
     * @param path the resource path
     * @return the future completing with the requested raw image
     */
    public CompletableFuture<ImageIcon> loadAsync(final String path) {
        return this.repository.loadAsync(ImageLoadRequest.raw(path));
    }

    /**
     * Loads an image asynchronously as an icon with the requested logical size.
     *
     * @param path the resource path
     * @param width the logical icon width
     * @param height the logical icon height
     * @return a future completing with the requested size-aware icon
     */
    public CompletableFuture<ImageIcon> loadAsync(final String path, final int width, final int height) {
        validateIconSize(width, height);
        final String key = path + "_" + width + "x" + height;
        return this.sizedCache.computeIfAbsent(key, ignored -> this.loadAsync(path)
            .thenApply(icon -> new SizeAwareImageIcon(icon.getImage(), width, height)));
    }

    /**
     * Starts preloading all the provided requests in background.
     * 
     * @param requests the images to preload
     * @return a future completed when all requests have finished
     */
    public CompletableFuture<Void> preload(final Collection<ImageLoadRequest> requests) {
        return CompletableFuture.allOf(
            requests.stream()
                .map(this.repository::loadAsync)
                .toArray(CompletableFuture[]::new)
        );
    }

    private static void validateIconSize(final int width, final int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Icon size must be positive");
        }
    }

    private static void setQualityRenderingHints(final Graphics2D graphics) {
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private static BufferedImage toBufferedImage(final Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        final BufferedImage bufferedImage = new BufferedImage(
            image.getWidth(null),
            image.getHeight(null),
            BufferedImage.TYPE_INT_ARGB
        );
        final Graphics2D graphics = bufferedImage.createGraphics();
        try {
            graphics.drawImage(image, 0, 0, null);
        } finally {
            graphics.dispose();
        }
        return bufferedImage;
    }

    private static BufferedImage scaleImage(
        final BufferedImage source,
        final int targetWidth,
        final int targetHeight
    ) {
        BufferedImage currentImage = source;
        int currentWidth = source.getWidth();
        int currentHeight = source.getHeight();
        while (currentWidth > targetWidth * DOWNSCALE_STEP_FACTOR
                || currentHeight > targetHeight * DOWNSCALE_STEP_FACTOR) {
            currentWidth = Math.max(targetWidth, currentWidth / DOWNSCALE_STEP_FACTOR);
            currentHeight = Math.max(targetHeight, currentHeight / DOWNSCALE_STEP_FACTOR);
            currentImage = renderScaledImage(currentImage, currentWidth, currentHeight);
        }
        if (currentWidth != targetWidth || currentHeight != targetHeight) {
            currentImage = renderScaledImage(currentImage, targetWidth, targetHeight);
        }
        return currentImage;
    }

    private static BufferedImage renderScaledImage(
        final BufferedImage source,
        final int targetWidth,
        final int targetHeight
    ) {
        final BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics = scaledImage.createGraphics();
        try {
            setQualityRenderingHints(graphics);
            graphics.drawImage(source, 0, 0, targetWidth, targetHeight, null);
        } finally {
            graphics.dispose();
        }
        return scaledImage;
    }

    private static final class SizeAwareImageIcon extends ImageIcon {

        private static final long serialVersionUID = 1L;

        private final transient BufferedImage source;
        private final int width;
        private final int height;
        private final transient Map<String, BufferedImage> variants = new ConcurrentHashMap<>();

        private SizeAwareImageIcon(final Image image, final int width, final int height) {
            super(image);
            this.source = toBufferedImage(image);
            this.width = width;
            this.height = height;
        }

        @Override
        public int getIconWidth() {
            return this.width;
        }

        @Override
        public int getIconHeight() {
            return this.height;
        }

        @Override
        public void paintIcon(final Component component, final Graphics graphics, final int x, final int y) {
            final Graphics graphicsCopy = graphics.create();
            try {
                if (graphicsCopy instanceof Graphics2D) {
                    final Graphics2D graphics2D = (Graphics2D) graphicsCopy;
                    setQualityRenderingHints(graphics2D);
                    graphics2D.drawImage(this.variantFor(graphics2D), x, y, this.width, this.height, component);
                } else {
                    graphicsCopy.drawImage(this.source, x, y, this.width, this.height, component);
                }
            } finally {
                graphicsCopy.dispose();
            }
        }

        private BufferedImage variantFor(final Graphics2D graphics) {
            final AffineTransform transform = graphics.getTransform();
            final int deviceWidth = scaledSize(this.width, transform.getScaleX());
            final int deviceHeight = scaledSize(this.height, transform.getScaleY());
            final String key = deviceWidth + "x" + deviceHeight;
            return this.variants.computeIfAbsent(
                key,
                ignored -> scaleImage(this.source, deviceWidth, deviceHeight)
            );
        }

        private static int scaledSize(final int logicalSize, final double scale) {
            return Math.max(logicalSize, (int) Math.ceil(logicalSize * Math.abs(scale)));
        }

    }

}
