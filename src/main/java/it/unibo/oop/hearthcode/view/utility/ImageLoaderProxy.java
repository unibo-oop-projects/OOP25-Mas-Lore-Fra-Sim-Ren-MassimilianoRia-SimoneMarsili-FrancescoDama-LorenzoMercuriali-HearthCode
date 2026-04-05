package it.unibo.oop.hearthcode.view.utility;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import javax.swing.ImageIcon;

/**
 * Proxy that exposes synchronous access while warming the cache in background.
 */
public final class ImageLoaderProxy {

    private final ImageRepository repository;

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
     * @return the loaded image
     */
    public ImageIcon load(final String path) {
        return join(this.loadAsync(path));
    }

    /**
     * Loads a scaled image synchronously.
     * 
     * @param path the resource path
     * @param width the target width
     * @param height the target height
     * @return the loaded image
     */
    public ImageIcon load(final String path, final int width, final int height) {
        return join(this.loadAsync(path, width, height));
    }

    /**
     * Loads a raw image asynchronously.
     * 
     * @param path the resource path 
     * @return the future for the requested image
     */
    public CompletableFuture<ImageIcon> loadAsync(final String path) {
        return this.repository.loadAsync(ImageLoadRequest.raw(path));
    }

    /**
     * Loads a scaled image asynchronously.
     *
     * @param path the resource path
     * @param width the target width
     * @param height the target height
     * @return the future for the requested image
     */
    public CompletableFuture<ImageIcon> loadAsync(final String path, final int width, final int height) {
        return this.repository.loadAsync(ImageLoadRequest.scaled(path, width, height));
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

    private static ImageIcon join(final CompletableFuture<ImageIcon> future) {
        return future.join();
    }
}
