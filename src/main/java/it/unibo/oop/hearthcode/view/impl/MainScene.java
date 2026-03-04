package it.unibo.oop.hearthcode.view.impl;

import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.view.api.Scene;

public class MainScene extends JPanel implements Scene {

    public MainScene() {
        this.setLayout(new GridBagLayout()); // centra i componenti
        final JButton playButton = new JButton("Play");
        this.add(playButton);
    }

}
