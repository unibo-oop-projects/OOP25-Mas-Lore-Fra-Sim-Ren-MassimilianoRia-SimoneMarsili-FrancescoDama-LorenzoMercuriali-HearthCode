package it.unibo.oop.hearthcode.view.impl;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.oop.hearthcode.view.api.MainView;

/**
 * It represents the implementation of the main view of the application which contains the various scenes.
 */
public class MainViewImpl implements MainView {

    private final JFrame frame = new JFrame("HearthCode");
    private final MainScene scene = new MainScene();

    @Override
    public void show() {
        SwingUtilities.invokeLater(() -> {
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            final JPanel root = new JPanel(new BorderLayout());
            root.add(this.scene, BorderLayout.CENTER);
            this.frame.setContentPane(root);

            this.frame.pack();
            this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            this.frame.setLocationRelativeTo(null);
            this.frame.setVisible(true);
        });
    }
    
}
