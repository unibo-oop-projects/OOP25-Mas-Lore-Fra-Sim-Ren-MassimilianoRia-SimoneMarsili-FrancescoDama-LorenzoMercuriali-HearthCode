package it.unibo.oop.hearthcode.view.impl;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComponent;

import it.unibo.oop.hearthcode.view.api.SettingsView;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Implementation of {@link SettingsView}.
 */
public final class SettingsScene extends AbstractBackgroundScene implements SettingsView {

    private static final long serialVersionUID = 1L;

    private static final String BACKGROUND_PATH = "/images/menu-background.png";
    private static final int BUTTON_WIDTH = ViewMetrics.menuButtonWidth();
    private static final int BUTTON_HEIGHT = ViewMetrics.menuButtonHeight();
    private final JButton backButton;
    private final JButton databaseButton;

    /**
     * Builds the settings scene.
     */
    public SettingsScene() {
        super(BACKGROUND_PATH);
        this.setLayout(new GridBagLayout());
        this.backButton = this.createImageButton(
            "/images/back-normal.png",
            "/images/back-hover.png",
            "/images/back-pressed.png",
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
        this.initializeLayout();
    }

    private void initializeLayout() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(this.databaseButton, gbc);
        gbc.gridy = 1;
        this.add(this.backButton, gbc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBack(final Runnable action) {
        this.backButton.addActionListener(event -> action.run());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void onDatabase(final Runnable action) {
        this.databaseButton.addActionListener(event -> action.run());
    }

}
