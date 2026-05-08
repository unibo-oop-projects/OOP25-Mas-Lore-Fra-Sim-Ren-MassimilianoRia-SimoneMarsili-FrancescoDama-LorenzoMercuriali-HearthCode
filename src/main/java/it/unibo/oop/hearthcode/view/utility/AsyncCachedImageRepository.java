package it.unibo.oop.hearthcode.view.utility;

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

    private final Map<String, CompletableFuture<ImageIcon>> rawCache = new ConcurrentHashMap<>();
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
        Objects.requireNonNull(request, "request must not be null");
        return this.loadRawAsync(request.path());
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

    private static void validateImageReady(final ImageIcon icon, final String imagePath) {
        if (icon.getIconWidth() <= 0 || icon.getIconHeight() <= 0) {
            throw new IllegalStateException("Unable to load image: " + imagePath);
        }
    }

}
