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
     * @param request the image loading request
     * @return a future completing with the requested image
     */
    CompletableFuture<ImageIcon> loadAsync(ImageLoadRequest request);

}
