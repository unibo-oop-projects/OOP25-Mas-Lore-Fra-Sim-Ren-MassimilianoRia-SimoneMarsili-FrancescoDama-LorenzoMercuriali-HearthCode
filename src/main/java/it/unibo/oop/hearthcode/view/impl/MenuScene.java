package it.unibo.oop.hearthcode.view.impl;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;

import it.unibo.oop.hearthcode.view.api.MenuView;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Main menu scene.
 */
public final class MenuScene extends AbstractBackgroundScene implements MenuView {

    private static final long serialVersionUID = 1L;

    private static final String BACKGROUND_PATH = "/images/menu-background.png";
    private static final int BUTTON_WIDTH = ViewMetrics.menuButtonWidth();
    private static final int BUTTON_HEIGHT = ViewMetrics.menuButtonHeight();
    private static final int BUTTON_PADDING_Y = ViewMetrics.menuVerticalGap();
    private final JButton playButton;
    private final JButton databaseButton;
    private final JButton quitButton;

    /**
     * Builds the main menu scene.
     */
    public MenuScene() {
        super(BACKGROUND_PATH);
        this.setLayout(new GridBagLayout());
        this.playButton = this.createImageButton(
            "/images/play-normal.png",
            "/images/play-hover.png",
            "/images/play-pressed.png",
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );
        this.databaseButton = this.createImageButton(
            "/images/deck-normal.png",
            "/images/deck-hover.png",
            "/images/deck-pressed.png",
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );
        this.quitButton = this.createImageButton(
            "/images/quit-normal.png",
            "/images/quit-hover.png",
            "/images/quit-pressed.png",
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
        this.add(this.databaseButton, gbc);
        gbc.gridy = 2;
        this.add(this.quitButton, gbc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPlay(final Runnable action) {
        this.playButton.addActionListener(event -> action.run());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDatabase(final Runnable action) {
        this.databaseButton.addActionListener(event -> action.run());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onQuit(final Runnable action) {
        this.quitButton.addActionListener(event -> action.run());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JComponent getComponent() {
        return this;
    }

}

