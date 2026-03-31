package it.unibo.oop.hearthcode.audio.model;

/**
 * Available background soundtracks.
 */
public enum SoundTrack {

    MENU("/audio/music/menu_audio_background.wav"),

    MATCH("/audio/music/match_audio_background.wav");

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
