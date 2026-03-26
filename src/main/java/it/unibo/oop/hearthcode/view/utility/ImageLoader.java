package it.unibo.oop.hearthcode.view.utility;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * A simple Image Loader.
 */
public final class ImageLoader {

    private static final Map<String, ImageIcon> CACHE = new HashMap<>();

    private ImageLoader() { }

    /**
     * Loads an Image from a specific path.
     * 
     * @param path the path of the file
     * @param w the width of the image
     * @param h the height of the image
     * @return the corresponding ImageIcon
     */
    public static ImageIcon load(final String path, final int w, final int h) {
        final String key = path + "_" + w + "x" + h;

        if (CACHE.containsKey(key)) {
            return CACHE.get(key);
        }
        final var url = ImageLoader.class.getResource(path);

        if (url == null) {
            throw new IllegalArgumentException("Immagine non trovata: " + path);
        }

        final ImageIcon icon = new ImageIcon(url);
        final Image scaled = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        final ImageIcon correctIcon = new ImageIcon(scaled);
        CACHE.put(key, correctIcon);
        return correctIcon;
    }
}
