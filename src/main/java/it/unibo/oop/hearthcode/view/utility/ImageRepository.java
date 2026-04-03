package it.unibo.oop.hearthcode.view.utility;

import java.util.concurrent.CompletableFuture;

import javax.swing.ImageIcon;

/**
 * Repository for loading image resources.
 */
@FunctionalInterface
public interface ImageRepository {

    /**
     * Loads the requested image asynchronously.
     *
     * @param request the image request
     * @return the future containing the image
     */
    CompletableFuture<ImageIcon> loadAsync(ImageLoadRequest request);

}
