package it.unibo.oop.hearthcode.view.utility;

import java.util.concurrent.CompletableFuture;

import javax.swing.ImageIcon;

/**
 * Static facade for image loading and background preloading.
 */
public final class ImageLoader {

    private static final ImageLoaderProxy PROXY = new ImageLoaderProxy(new AsyncCachedImageRepository());
    private static CompletableFuture<Void> MENU_PRELOAD;
    private static CompletableFuture<Void> MATCH_PRELOAD;
    private static CompletableFuture<Void> DATABASE_PRELOAD;

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
        synchronized (ImageLoader.class) {
            if (MENU_PRELOAD == null) {
                MENU_PRELOAD = PROXY.preload(ImagePreloadCatalog.menuAndNavigation());
            }
            return MENU_PRELOAD.thenApply(v -> null);
        }
    }

    /**
     * Returns a future representing the completion of the match preload.
     * 
     * @return a future completed when the match preload has finished
     */
    public static CompletableFuture<Void> preloadMatchAssets() {
        synchronized (ImageLoader.class) {
            if (MATCH_PRELOAD == null) {
                MATCH_PRELOAD = PROXY.preload(ImagePreloadCatalog.match());
            }
            return MATCH_PRELOAD.thenApply(v -> null);
        }
    }

    /**
     * Returns a future representing the completion of the database preload.
     *
     * @return a future completed when database assets have been preloaded
     */
    public static CompletableFuture<Void> preloadDatabaseAssets() {
        synchronized (ImageLoader.class) {
            if (DATABASE_PRELOAD == null) {
                DATABASE_PRELOAD = PROXY.preload(ImagePreloadCatalog.database());
            }
            return DATABASE_PRELOAD.thenApply(v -> null);
        }
    }

}
