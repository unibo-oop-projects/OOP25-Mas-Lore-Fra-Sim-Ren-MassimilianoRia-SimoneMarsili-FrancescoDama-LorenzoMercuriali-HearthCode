package it.unibo.oop.hearthcode.audio.api;

import it.unibo.oop.hearthcode.audio.model.SoundEffect;
import it.unibo.oop.hearthcode.audio.model.SoundTrack;

/**
 * Service responsible for audio playback.
 */
public interface AudioService {

    /**
     * Plays the given background soundtrack.
     * If the same soundtrack is already playing, nothing happens.
     *
     * @param track the soundtrack to play
     */
    void playMusic(SoundTrack track);

    /**
     * Stops the current background music.
     */
    void stopMusic();

    /**
     * Plays a one-shot sound effect without interrupting music.
     *
     * @param effect the effect to play
     */
    void playEffect(SoundEffect effect);

    /**
     * Enables or disables music playback.
     *
     * @param enabled true to enable music, false otherwise
     */
    void setMusicEnabled(boolean enabled);

    /**
     * Enables or disables sound effects playback.
     *
     * @param enabled true to enable effects, false otherwise
     */
    void setEffectsEnabled(boolean enabled);

    /**
     * Releases audio resources.
     */
    void shutdown();

}
