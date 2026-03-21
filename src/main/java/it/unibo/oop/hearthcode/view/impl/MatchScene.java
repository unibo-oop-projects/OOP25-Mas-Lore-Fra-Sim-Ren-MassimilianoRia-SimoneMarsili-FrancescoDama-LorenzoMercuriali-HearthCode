package it.unibo.oop.hearthcode.view.impl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.view.api.MatchView;

/**
 * Implementation of {@link MatchScene}.
 */
public final class MatchScene extends JPanel implements MatchView {

    private static final long serialVersionUID = 1L;
    private static final int ACTION_PANEL_WIDTH = 160;

    private final JButton attackButton;
    private final JButton endTurnButton;

    /**
     * Initializes the Scene.
     */
    public MatchScene() {
        super(new BorderLayout());

        this.attackButton = new JButton("ATTACK");
        this.endTurnButton = new JButton("END TURN");

        this.add(this.createTitledPanel("Opponent Hand"), BorderLayout.NORTH);
        this.add(this.createCenterPanel(), BorderLayout.CENTER);
        this.add(this.createTitledPanel("Player Hand"), BorderLayout.SOUTH);
    }

    private Component createCenterPanel() {
        final JPanel panel = this.createSimplePanel();
        panel.setLayout(new BorderLayout());

        final JPanel actionPanel = this.createTitledPanel("Actions");
        actionPanel.setPreferredSize(new Dimension(ACTION_PANEL_WIDTH, 0));
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));

        this.attackButton.setAlignmentX(CENTER_ALIGNMENT);
        this.endTurnButton.setAlignmentX(CENTER_ALIGNMENT);

        actionPanel.add(this.attackButton);
        actionPanel.add(this.endTurnButton);

        final JPanel armiesPanel = this.createSimplePanel();
        armiesPanel.setLayout(new GridLayout(2, 1, 0, 8));

        final JPanel opponentArmyPanel = this.createTitledPanel("Opponent Army");
        final JPanel playerArmyPanel = this.createTitledPanel("Player Army");

        armiesPanel.add(opponentArmyPanel);
        armiesPanel.add(playerArmyPanel);

        panel.add(actionPanel, BorderLayout.WEST);
        panel.add(armiesPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSimplePanel() {
        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createTitledPanel(final String title) {
        final JPanel panel = createSimplePanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void onAttack(final Runnable action) {
        this.attackButton.addActionListener(event -> action.run());
    }

    @Override
    public void onEndTurn(final Runnable action) {
        this.endTurnButton.addActionListener(event -> action.run());
    }
}
