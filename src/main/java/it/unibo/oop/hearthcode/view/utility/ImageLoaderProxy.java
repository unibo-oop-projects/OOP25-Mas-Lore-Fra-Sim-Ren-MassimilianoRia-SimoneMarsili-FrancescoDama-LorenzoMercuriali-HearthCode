package it.unibo.oop.hearthcode.view.utility;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import javax.swing.ImageIcon;

/**
 * Proxy exposing synchronous access and background preloading over an {@link ImageRepository}.
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
     * @return the loaded raw image
     */
    public ImageIcon load(final String path) {
        return this.loadAsync(path).join();
    }

    /**
     * Loads a scaled image synchronously.
     * 
     * @param path the resource path
     * @param width the target width
     * @param height the target height
     * @return the loaded scaled image
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
     * Loads a scaled image asynchronously.
     *
     * @param path the resource path
     * @param width the target width
     * @param height the target height
     * @return a future completing with the requested scaled image
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

}
