package it.unibo.oop.hearthcode.view.impl;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;

import it.unibo.oop.hearthcode.view.api.MenuView;

/**
 * It represents the menu scene of the application.
 */
public final class MenuScene extends AbstractBackgroundScene implements MenuView {

    private static final long serialVersionUID = 1L;

    private static final String BACKGROUND_PATH = "/menu-background.png";

    private static final int BUTTON_WIDTH = 280;
    private static final int BUTTON_HEIGHT = 100;
    private static final int BUTTON_PADDING_Y = 12;

    private final JButton playButton;
    private final JButton settingsButton;
    private final JButton quitButton;

    /**
     * Builds the panel.
     */
    public MenuScene() {
        super(BACKGROUND_PATH);

        this.setLayout(new GridBagLayout());

        this.playButton = this.createImageButton(
            "/play-normal.png",
            "/play-hover.png",
            "/play-pressed.png",
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );
        this.settingsButton = this.createImageButton(
            "/settings-normal.png",
            "/settings-hover.png",
            "/settings-pressed.png",
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );
        this.quitButton = this.createImageButton(
            "/quit-normal.png",
            "/quit-hover.png",
            "/quit-pressed.png",
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );

        this.initializeLayout();
    }

    private void initializeLayout() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(BUTTON_PADDING_Y, 0, BUTTON_PADDING_Y, 0);

        gbc.gridy = 0;
        this.add(this.playButton, gbc);

        gbc.gridy = 1;
        this.add(this.settingsButton, gbc);

        gbc.gridy = 2;
        this.add(this.quitButton, gbc);
    }

    /**
     * Binds the play action.
     *
     * @param action the action to execute
     */
    @Override
    public void onPlay(final Runnable action) {
        this.playButton.addActionListener(event -> action.run());
    }

    /**
     * Binds the settings action.
     *
     * @param action the action to execute
     */
    @Override
    public void onSettings(final Runnable action) {
        this.settingsButton.addActionListener(event -> action.run());
    }

    /**
     * Binds the quit action.
     *
     * @param action the action to execute
     */
    @Override
    public void onQuit(final Runnable action) {
        this.quitButton.addActionListener(event -> action.run());
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
}
