package it.unibo.oop.hearthcode.view.impl;

import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.view.api.Scene;

/**
 * It represents the first scene in the application.
 */
public final class MainScene extends JPanel implements Scene {

    private static final long serialVersionUID = 1L;

    /**
     * Builds the panel.
     */
    public MainScene() {
        this.setLayout(new GridBagLayout());
        final JButton playButton = new JButton("Play");
        this.add(playButton);
    }

}
