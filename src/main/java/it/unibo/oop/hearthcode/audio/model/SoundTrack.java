package it.unibo.oop.hearthcode.audio.model;

/**
 * Available background soundtracks.
 */
public enum SoundTrack {

    /**
     * Background music for menu-like scenes.
     */
    MENU("/menu_audio_background.wav"),

    /**
     * Background music for the match scene.
     */
    MATCH("/match_audio_background.wav");

    private final String resourcePath;

    SoundTrack(final String resourcePath) {
        this.resourcePath = resourcePath;
    }

    /**
     * @return the classpath resource path of the soundtrack
     */
    public String getResourcePath() {
        return this.resourcePath;
    }
    
}
