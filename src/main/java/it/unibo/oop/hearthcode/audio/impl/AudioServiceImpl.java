package it.unibo.oop.hearthcode.audio.impl;

import java.util.Objects;
import java.util.Optional;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

import it.unibo.oop.hearthcode.audio.api.AudioService;
import it.unibo.oop.hearthcode.audio.model.SoundEffect;
import it.unibo.oop.hearthcode.audio.model.SoundTrack;

/**
 * Default audio service based on Java Sound.
 */
public final class AudioServiceImpl implements AudioService {

    private Clip currentMusicClip;
    private SoundTrack currentTrack;
    private boolean musicEnabled;
    private boolean effectsEnabled;

    /**
     * Builds the audio service with both music and effects enabled.
     */
    public AudioServiceImpl() {
        this.musicEnabled = true;
        this.effectsEnabled = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void playMusic(final SoundTrack track) {
        Objects.requireNonNull(track);

        if (!this.musicEnabled) {
            return;
        }

        if (track == this.currentTrack && this.currentMusicClip != null && this.currentMusicClip.isRunning()) {
            return;
        }

        this.stopMusicInternal();

        final Optional<Clip> loadedClip = AudioClipLoader.loadClip(track.getResourcePath());
        if (loadedClip.isPresent()) {
            this.currentMusicClip = loadedClip.get();
            this.currentTrack = track;
            this.currentMusicClip.setFramePosition(0);
            this.currentMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void stopMusic() {
        this.stopMusicInternal();
        this.currentTrack = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void playEffect(final SoundEffect effect) {
        Objects.requireNonNull(effect);

        if (!this.effectsEnabled) {
            return;
        }

        final Optional<Clip> loadedClip = AudioClipLoader.loadClip(effect.resourcePath());
        if (loadedClip.isPresent()) {
            final Clip effectClip = loadedClip.get();
            effectClip.addLineListener(event -> {
                if (LineEvent.Type.STOP.equals(event.getType())) {
                    AudioClipLoader.closeClip(effectClip);
                }
            });
            effectClip.start();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setMusicEnabled(final boolean enabled) {
        this.musicEnabled = enabled;
        if (!enabled) {
            this.stopMusicInternal();
            this.currentTrack = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setEffectsEnabled(final boolean enabled) {
        this.effectsEnabled = enabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void shutdown() {
        this.stopMusicInternal();
        this.currentTrack = null;
    }

    private void stopMusicInternal() {
        AudioClipLoader.closeClip(this.currentMusicClip);
        this.currentMusicClip = null;
    }

}
