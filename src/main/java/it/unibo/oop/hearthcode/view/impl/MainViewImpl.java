package it.unibo.oop.hearthcode.view.impl;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import it.unibo.oop.hearthcode.view.api.MainView;

/**
 * It represents the implementation of the main view of the application which contains the various scenes.
 */
public final class MainViewImpl implements MainView {

    private final JFrame frame;
    private final MainScene scene;

    /**
     * Creates the main view.
     */
    public MainViewImpl() {
        this.frame = new JFrame("HearthCode");
        this.scene = new MainScene();
    }

    @Override
    public void show() {
        SwingUtilities.invokeLater(() -> {
            this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            this.frame.setUndecorated(true);
            this.frame.setResizable(false);

            final JPanel root = new JPanel(new BorderLayout());
            root.add(this.scene, BorderLayout.CENTER);
            this.frame.setContentPane(root);

            this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.frame.setVisible(true);
        });
    }

    @Override
    public void close() {
        SwingUtilities.invokeLater(() -> {
            this.frame.dispose();
        });
    }

}