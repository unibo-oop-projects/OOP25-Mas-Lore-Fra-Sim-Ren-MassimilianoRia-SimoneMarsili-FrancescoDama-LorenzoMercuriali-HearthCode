package it.unibo.oop.hearthcode.audio.model;

/**
 * Available sound effects.
 */
public enum SoundEffect {

    BUTTON_CLICK("/audio/effects/click_audio.wav");

    private final String resourcePath;

    SoundEffect(final String resourcePath) {
        this.resourcePath = resourcePath;
    }

    /**
     * @return the classpath resource path of the effect
     */
    public String resourcePath() {
        return this.resourcePath;
    }

}
