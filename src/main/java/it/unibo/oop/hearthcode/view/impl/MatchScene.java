package it.unibo.oop.hearthcode.view.impl;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.view.api.Scene;

/**
 * Placeholder match scene.
 */
public final class MatchScene extends JPanel implements Scene {

    private static final long serialVersionUID = 1L;

    private final JButton backButton;

    /**
     * Builds the match scene.
     */
    public MatchScene() {
        super(new GridBagLayout());

        this.backButton = new JButton("Back to menu");

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 0;
        this.add(new JLabel("Match"), gbc);

        gbc.gridy = 1;
        this.add(this.backButton, gbc);
    }

    /**
     * Binds the back action.
     *
     * @param action the action to execute
     */
    public void onBack(final Runnable action) {
        this.backButton.addActionListener(event -> action.run());
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
}