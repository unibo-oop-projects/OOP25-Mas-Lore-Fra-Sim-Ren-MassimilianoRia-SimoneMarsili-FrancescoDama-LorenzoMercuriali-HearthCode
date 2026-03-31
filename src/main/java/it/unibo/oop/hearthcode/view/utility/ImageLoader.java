package it.unibo.oop.hearthcode.view.utility;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * A simple Image Loader.
 */
public final class ImageLoader {

    private static final Map<String, ImageIcon> RAW_CACHE = new HashMap<>();
    private static final Map<String, ImageIcon> CACHE = new HashMap<>();

    private ImageLoader() {
    }

    /**
     * Loads an image from a specific path without resizing it.
     *
     * @param path the resource path
     * @return the corresponding {@link ImageIcon}
     */
    public static ImageIcon load(final String path) {
        if (RAW_CACHE.containsKey(path)) {
            return RAW_CACHE.get(path);
        }
        final var url = ImageLoader.class.getResource(path);
        if (url == null) {
            throw new IllegalArgumentException("Immagine non trovata: " + path);
        }
        final ImageIcon icon = new ImageIcon(url);
        RAW_CACHE.put(path, icon);
        return icon;
    }

    /**
     * Loads an image from a specific path.
     *
     * @param path the resource path
     * @param w the target width
     * @param h the target height
     * @return the corresponding scaled {@link ImageIcon}
     */
    public static ImageIcon load(final String path, final int w, final int h) {
        final String key = path + "_" + w + "x" + h;
        if (CACHE.containsKey(key)) {
            return CACHE.get(key);
        }
        final ImageIcon icon = load(path);
        final Image scaled = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        final ImageIcon correctIcon = new ImageIcon(scaled);
        CACHE.put(key, correctIcon);
        return correctIcon;
    }

}
