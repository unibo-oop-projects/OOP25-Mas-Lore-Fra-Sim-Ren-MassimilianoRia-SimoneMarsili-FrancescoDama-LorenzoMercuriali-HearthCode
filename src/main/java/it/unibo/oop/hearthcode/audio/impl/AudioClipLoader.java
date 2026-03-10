package it.unibo.oop.hearthcode.audio.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Utility class that loads audio clips from classpath resources.
 */
public final class AudioClipLoader {

    private AudioClipLoader() {

    }

    /**
     * Loads a clip from the given classpath resource.
     *
     * @param resourcePath the classpath resource path
     * @return an optional containing the clip if loading succeeds
     */
    public static Optional<Clip> loadClip(final String resourcePath) {
        Objects.requireNonNull(resourcePath);

        try (InputStream input = AudioClipLoader.class.getResourceAsStream(resourcePath)) {
            if (input == null) {
                return Optional.empty();
            }

            try (BufferedInputStream buffered = new BufferedInputStream(input);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(buffered)) {
                final Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                return Optional.of(clip);
            }
        } catch (final IOException | UnsupportedAudioFileException | LineUnavailableException exception) {
            return Optional.empty();
        }
    }

    /**
     * Stops and closes the given clip if present.
     *
     * @param clip the clip to close
     */
    public static void closeClip(final Clip clip) {
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.flush();
            clip.close();
        }
    }
}
