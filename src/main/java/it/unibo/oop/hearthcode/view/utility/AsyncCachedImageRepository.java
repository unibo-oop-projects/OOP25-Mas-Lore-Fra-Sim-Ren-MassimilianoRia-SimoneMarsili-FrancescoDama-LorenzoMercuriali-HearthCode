package it.unibo.oop.hearthcode.view.utility;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BaseMultiResolutionImage;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Asynchronous image repository with in-memory caching.
 */
public final class AsyncCachedImageRepository implements ImageRepository {

    private static final double DEFAULT_DEVICE_SCALE = 1.0;
    private static final int DOWNSCALE_STEP_FACTOR = 2;
    private final Map<String, CompletableFuture<ImageIcon>> rawCache = new ConcurrentHashMap<>();
    private final Map<String, CompletableFuture<ImageIcon>> scaledCache = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(
        Math.max(2, Runtime.getRuntime().availableProcessors() / 2),
        runnable -> {
            final Thread thread = new Thread(runnable, "image-loader");
            thread.setDaemon(true);
            return thread;
        }
    );

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<ImageIcon> loadAsync(final ImageLoadRequest request) {
        Objects.requireNonNull(request, "requests must not be null");
        return request.isScaled() ? this.loadScaledAsync(request) : this.loadRawAsync(request.path());
    }

    /**
     * Loads and caches a raw image asynchronously.
     * 
     * @param path the resource path
     * @return a future completing with the raw image
     */
    private CompletableFuture<ImageIcon> loadRawAsync(final String path) {
        return this.rawCache.computeIfAbsent(path, key -> CompletableFuture.supplyAsync(() -> {
            final var url = ImageLoader.class.getResource(key);
            if (url == null) {
                throw new IllegalArgumentException("Image not found: " + key);
            }
            final BufferedImage image;
            try {
                image = ImageIO.read(url);
            } catch (final IOException exception) {
                throw new IllegalStateException("Unable to read image: " + key, exception);
            }
            if (image == null) {
                throw new IllegalStateException("Unsupported image format: " + key);
            }
            final ImageIcon icon = new ImageIcon(image);
            validateImageReady(icon, key);
            return icon;
        }, this.executor));
    }

    /**
     * Loads and caches a scaled image asynchronously.
     * 
     * @param request the scaled image request
     * @return a future completing with the scaled image
     */
    private CompletableFuture<ImageIcon> loadScaledAsync(final ImageLoadRequest request) {
        final Dimension deviceSize = deviceSize(request.width(), request.height());
        final String key = request.path() + "_" + request.width() + "x" + request.height()
            + "@" + deviceSize.width + "x" + deviceSize.height;
        return this.scaledCache.computeIfAbsent(key, ignored -> this.loadRawAsync(request.path())
            .thenApplyAsync(icon -> {
                final ImageIcon scaledIcon = createScaledIcon(
                    (BufferedImage) icon.getImage(),
                    request.width(),
                    request.height(),
                    deviceSize
                );
                validateImageReady(scaledIcon, request.path());
                return scaledIcon;
            }, this.executor)
        );
    }

    private static ImageIcon createScaledIcon(
        final BufferedImage source,
        final int logicalWidth,
        final int logicalHeight,
        final Dimension deviceSize
    ) {
        final BufferedImage logicalImage = scaleImage(source, logicalWidth, logicalHeight);
        if (deviceSize.width == logicalWidth && deviceSize.height == logicalHeight) {
            return new ImageIcon(logicalImage);
        }
        return new ImageIcon(new BaseMultiResolutionImage(
            logicalImage,
            scaleImage(source, deviceSize.width, deviceSize.height)
        ));
    }

    private static Dimension deviceSize(final int logicalWidth, final int logicalHeight) {
        final AffineTransform transform = defaultScreenTransform();
        return new Dimension(
            Math.max(logicalWidth, (int) Math.ceil(logicalWidth * transform.getScaleX())),
            Math.max(logicalHeight, (int) Math.ceil(logicalHeight * transform.getScaleY()))
        );
    }

    private static AffineTransform defaultScreenTransform() {
        if (GraphicsEnvironment.isHeadless()) {
            return AffineTransform.getScaleInstance(DEFAULT_DEVICE_SCALE, DEFAULT_DEVICE_SCALE);
        }
        return GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration()
            .getDefaultTransform();
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

    private static void setQualityRenderingHints(final Graphics2D graphics) {
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private static void validateImageReady(final ImageIcon icon, final String imagePath) {
        if (icon.getIconWidth() <= 0 || icon.getIconHeight() <= 0) {
            throw new IllegalStateException("Unable to load image: " + imagePath);
        }
    }

}
