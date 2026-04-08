package it.unibo.oop.hearthcode.view.utility;

import java.util.concurrent.CompletableFuture;

import javax.swing.ImageIcon;

/**
 * Static facade for image loading and background preloading.
 */
public final class ImageLoader {

    private static final ImageLoaderProxy PROXY = new ImageLoaderProxy(new AsyncCachedImageRepository());
    private static final CompletableFuture<Void> MENU_PRELOAD = PROXY.preload(ImagePreloadCatalog.menuAndNavigation());
    private static final CompletableFuture<Void> MATCH_PRELOAD = PROXY.preload(ImagePreloadCatalog.match());

    private ImageLoader() {
    }

    /**
     * Loads an image from the given resource path without resizing it.
     *
     * @param path the resource path
     * @return the corresponding raw {@link ImageIcon}
     */
    public static ImageIcon load(final String path) {
        return PROXY.load(path);
    }

    /**
     * Loads and scales an image from the given resource path.
     *
     * @param path the resource path
     * @param w the target width
     * @param h the target height
     * @return the corresponding scaled {@link ImageIcon}
     */
    public static ImageIcon load(final String path, final int w, final int h) {
        return PROXY.load(path, w, h);
    }

    /**
     * Returns a future representing the completion of the menu preload.
     * 
     * @return a future completed when the menu preload has finished
     */
    public static CompletableFuture<Void> preloadMenuAssets() {
        return MENU_PRELOAD.thenApply(v -> null);
    }

    /**
     * Returns a future representing the completion of the match preload.
     * 
     * @return a future completed when the match preload has finished
     */
    public static CompletableFuture<Void> preloadMatchAssets() {
        return MATCH_PRELOAD.thenApply(v -> null);
    }

}
