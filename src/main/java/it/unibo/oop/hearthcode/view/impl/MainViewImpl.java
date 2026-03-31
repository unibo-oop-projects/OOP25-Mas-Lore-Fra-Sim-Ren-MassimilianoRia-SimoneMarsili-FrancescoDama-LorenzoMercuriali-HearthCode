package it.unibo.oop.hearthcode.view.impl;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import it.unibo.oop.hearthcode.view.api.MainView;
import it.unibo.oop.hearthcode.view.api.Scene;
import it.unibo.oop.hearthcode.view.api.SceneId;

/**
 * Implementation of the main view of the application.
 */
public final class MainViewImpl implements MainView {

    private final JFrame frame;
    private final CardLayout cardLayout;
    private final JPanel cardsPanel;

    /**
     * Creates the main view.
     */
    public MainViewImpl() {
        this.frame = new JFrame("HearthCode");
        this.cardLayout = new CardLayout();
        this.cardsPanel = new JPanel(this.cardLayout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        SwingUtilities.invokeLater(() -> {
            this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            this.frame.setUndecorated(true);
            this.frame.setResizable(false);
            this.frame.setContentPane(this.cardsPanel);
            this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.frame.setVisible(true);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        SwingUtilities.invokeLater(this.frame::dispose);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addScene(final SceneId id, final Scene scene) {
        SwingUtilities.invokeLater(() -> this.cardsPanel.add(scene.getComponent(), id.name()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showScene(final SceneId id) {
        SwingUtilities.invokeLater(() -> {
            this.cardLayout.show(this.cardsPanel, id.name());
            this.cardsPanel.revalidate();
            this.cardsPanel.repaint();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean confirmExit() {
        final int result = JOptionPane.showConfirmDialog(
            this.frame,
            "Are you sure you want to exit?",
            "Exit Application",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }

}
