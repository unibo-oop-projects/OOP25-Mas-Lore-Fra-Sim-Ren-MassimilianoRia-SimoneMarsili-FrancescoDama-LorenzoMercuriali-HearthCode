package it.unibo.oop.hearthcode.view.utility;

import java.awt.Image;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;

/**
 * Asynchronous image repository with in-memory caching.
 */
public final class AsyncCachedImageRepository implements ImageRepository {

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
            final ImageIcon icon = new ImageIcon(url);
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
        final String key = request.path() + "_" + request.width() + "x" + request.height();
        return this.scaledCache.computeIfAbsent(key, ignored -> this.loadRawAsync(request.path())
            .thenApplyAsync(icon -> {
                final Image scaled = icon.getImage().getScaledInstance(
                    request.width(),
                    request.height(),
                    Image.SCALE_SMOOTH
                );
                final ImageIcon scaledIcon = new ImageIcon(scaled);
                validateImageReady(scaledIcon, request.path());
                return scaledIcon;
            }, this.executor)
        );
    }

    /**
     * Verifies that the given icon has valid dimensions after loading.
     * 
     * @param icon the icon to validate
     * @param imagePath the original resource path
     */
    private static void validateImageReady(final ImageIcon icon, final String imagePath) {
        if (icon.getIconWidth() <= 0 || icon.getIconHeight() <= 0) {
            throw new IllegalStateException("Unable to load image: " + imagePath);
        }
    }

}
