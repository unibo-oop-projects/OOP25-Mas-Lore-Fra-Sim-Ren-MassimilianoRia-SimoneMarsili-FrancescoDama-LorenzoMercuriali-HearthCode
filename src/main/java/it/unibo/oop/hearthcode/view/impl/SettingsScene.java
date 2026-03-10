package it.unibo.oop.hearthcode.view.impl;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComponent;

import it.unibo.oop.hearthcode.view.api.SettingsView;

/**
 * It represents the settings scene of the application.
 */
public final class SettingsScene extends AbstractBackgroundScene implements SettingsView {

    private static final long serialVersionUID = 1L;

    private static final String BACKGROUND_PATH = "/images/menu-background.png";

    private static final int BUTTON_WIDTH = 280;
    private static final int BUTTON_HEIGHT = 100;

    private final JButton backButton;

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

        this.initializeLayout();
    }

    private void initializeLayout() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(this.backButton, gbc);
    }

    /**
     * Binds the back action.
     *
     * @param action the action to execute
     */
    @Override
    public void onBack(final Runnable action) {
        this.backButton.addActionListener(event -> action.run());
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
}
