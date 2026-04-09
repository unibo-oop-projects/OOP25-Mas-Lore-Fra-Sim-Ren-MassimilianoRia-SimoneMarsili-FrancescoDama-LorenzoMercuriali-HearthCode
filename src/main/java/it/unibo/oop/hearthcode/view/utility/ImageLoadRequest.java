package it.unibo.oop.hearthcode.view.utility;

/**
 * Immutable description of an image loading request.
 * 
 * @param path the resource path
 * @param width the target width, or null for raw image loading
 * @param height the target height, or null for raw image loading
 */
public record ImageLoadRequest(String path, Integer width, Integer height) {

    /**
     * Creates a request for a raw image.
     * 
     * @param path the resource path
     * @return the raw image request
     */
    public static ImageLoadRequest raw(final String path) {
        return new ImageLoadRequest(path, null, null);
    }

    /**
     * Creates a request for a scaled image.
     * 
     * @param path the resource path
     * @param width the target width
     * @param height the target height
     * @return the scaled image request
     */
    public static ImageLoadRequest scaled(final String path, final int width, final int height) {
        return new ImageLoadRequest(path, width, height);
    }

    /**
     * @return true when the request targets a scaled image
     */
    public boolean isScaled() {
        return this.width != null && this.height != null;
    }

}
