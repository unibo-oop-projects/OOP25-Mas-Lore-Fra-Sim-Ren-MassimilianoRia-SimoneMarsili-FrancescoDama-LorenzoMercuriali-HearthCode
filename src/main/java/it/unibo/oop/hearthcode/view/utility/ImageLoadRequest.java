package it.unibo.oop.hearthcode.view.utility;

/**
 * Immutable description of an image loading request.
 * 
 * @param path the resource path
 */
public record ImageLoadRequest(String path) {

    /**
     * Creates a request for an image.
     * 
     * @param path the resource path
     * @return the image request
     */
    public static ImageLoadRequest raw(final String path) {
        return new ImageLoadRequest(path);
    }

}
