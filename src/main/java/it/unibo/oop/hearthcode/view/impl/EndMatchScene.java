package it.unibo.oop.hearthcode.view.impl;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComponent;

import it.unibo.oop.hearthcode.view.api.EndMatchView;

/**
 * EndMatch scene.
 */
public final class EndMatchScene extends AbstractBackgroundScene implements EndMatchView {

    private static final long serialVersionUID = 1L;

    private static final String BACKGROUND_PATH = "/images/menu-background.png";

    private static final int BUTTON_WIDTH = 280;
    private static final int BUTTON_HEIGHT = 100;

    private final JButton menuButton;

    /**
     * Build the end match scene.
     */
    public EndMatchScene() {
        super(BACKGROUND_PATH);

        this.setLayout(new GridBagLayout());

        this.menuButton = this.createImageButton(
            "/images/menu-normal.png",
            "/images/menu-hover.png",
            "/images/menu-pressed.png",
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );

        this.initializedLayout();
    }

    private void initializedLayout() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(this.menuButton, gbc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMenu(final Runnable action) {
        this.menuButton.addActionListener(event -> action.run());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JComponent getComponent() {
        return this;
    }

}
