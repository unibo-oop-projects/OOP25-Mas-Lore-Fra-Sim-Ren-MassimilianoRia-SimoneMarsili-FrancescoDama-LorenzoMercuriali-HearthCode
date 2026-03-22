package it.unibo.oop.hearthcode.view.impl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.model.boardgame.api.GameObserver;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.api.PlayerType;
import it.unibo.oop.hearthcode.view.api.MatchView;

/**
 * Implementation of {@link MatchScene}.
 */
public final class MatchScene extends JPanel implements MatchView, GameObserver {

    private static final long serialVersionUID = 1L;
    private static final int SIDE_PANEL_WIDTH = 160;
    private static final int PLAYER_PANEL_HEIGHT = 200;
    private static final String SLASH = " / ";
    private static final String PLAYER = "Player";
    private static final String OPPONENT = "Opponent";

    private int maxHealth;

    private final JButton attackButton;
    private final JButton endTurnButton;

    private JPanel playerHandPanel;
    private JPanel opponentHandPanel;
    private JPanel playerArmyPanel;
    private JPanel opponentArmyPanel;

    private JLabel playerHealthLabel;
    private JLabel playerManaLabel;
    private JLabel opponentHealthLabel;
    private JLabel opponentManaLabel;

    /**
     * Initializes the Scene.
     */
    public MatchScene() {
        super(new BorderLayout());

        this.attackButton = new JButton("ATTACK");
        this.endTurnButton = new JButton("END TURN");

        this.add(this.createPlayerPanel(OPPONENT), BorderLayout.NORTH);
        this.add(this.createCenterPanel(), BorderLayout.CENTER);
        this.add(this.createPlayerPanel(PLAYER), BorderLayout.SOUTH);
    }

    private Component createCenterPanel() {
        final JPanel panel = this.createSimplePanel();
        panel.setLayout(new BorderLayout());

        final JPanel actionPanel = this.createTitledPanel("Actions");
        actionPanel.setPreferredSize(new Dimension(SIDE_PANEL_WIDTH, 0));
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));

        this.attackButton.setAlignmentX(CENTER_ALIGNMENT);
        this.endTurnButton.setAlignmentX(CENTER_ALIGNMENT);

        actionPanel.add(this.attackButton);
        actionPanel.add(this.endTurnButton);

        final JPanel armiesPanel = this.createSimplePanel();
        armiesPanel.setLayout(new GridLayout(2, 1, 0, 8));

        this.opponentArmyPanel = this.createTitledPanel("Opponent Army");
        this.playerArmyPanel = this.createTitledPanel("Player Army");

        armiesPanel.add(this.opponentArmyPanel);
        armiesPanel.add(this.playerArmyPanel);

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

    private JPanel createPlayerPanel(final String title) {
        final JPanel playerPanel = createSimplePanel();
        playerPanel.setLayout(new BorderLayout());
        playerPanel.setPreferredSize(new Dimension(0, PLAYER_PANEL_HEIGHT));
        final JPanel statsPanel = createPlayerStatsPanel(title);
        statsPanel.setPreferredSize(new Dimension(SIDE_PANEL_WIDTH, 0));
        playerPanel.add(statsPanel, BorderLayout.WEST);
        if (PLAYER.equals(title)) {
            this.playerHandPanel = createTitledPanel(title + " Hand");
            playerPanel.add(this.playerHandPanel, BorderLayout.CENTER);
        } else {
            this.opponentHandPanel = createTitledPanel(title + " Hand");
            playerPanel.add(this.opponentHandPanel, BorderLayout.CENTER);
        }
        return playerPanel;
    }

    private JPanel createPlayerStatsPanel(final String title) {
        final JPanel panel = createTitledPanel(title + " Stats");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        final JLabel health = new JLabel("HP: 0 / 0");
        final JLabel mana = new JLabel("Mana: 0 / 0");

        panel.add(health);
        panel.add(mana);

        if (PLAYER.equals(title)) {
            this.playerHealthLabel = health;
            this.playerManaLabel = mana;
        } else {
            this.opponentHealthLabel = health;
            this.opponentManaLabel = mana;
        }

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

    @Override
    public void onGameStarted(final Map<PlayerId, Integer> playersHealth) {
        this.maxHealth = playersHealth.get(new PlayerId(PlayerType.HUMAN_PLAYER));
        playersHealth.forEach((playerId, health) -> {
            this.updateHealth(playerId, health);
            this.updateMana(playerId, 0, 0);
        });
        this.revalidate();
        this.repaint();
    }

    private void updateHealth(final PlayerId playerId, final int newHealth) {
        if (this.isHumanPlayer(playerId)) {
            this.playerHealthLabel.setText("HP: " + newHealth + SLASH + maxHealth);
        } else {
            this.opponentHealthLabel.setText("HP: " + newHealth + SLASH + maxHealth);
        }
    }

    private void updateMana(final PlayerId playerId, final int currentMana, final int maxMana) {
        if (this.isHumanPlayer(playerId)) {
            this.playerManaLabel.setText("Mana: " + currentMana + SLASH + maxMana);
        } else {
            this.opponentManaLabel.setText("Mana: " + currentMana + SLASH + maxMana);
        }
    }

    private boolean isHumanPlayer(final PlayerId playerId) {
        return playerId.type() == PlayerType.HUMAN_PLAYER;
    }

    @Override
    public void onTurnSwitch(final PlayerId nextPlayer) {
        final boolean isHumanTurn = this.isHumanPlayer(nextPlayer);
        this.attackButton.setEnabled(isHumanTurn);
        this.endTurnButton.setEnabled(isHumanTurn);
    }

    /**
     * Temp method.
     * 
     * @param playerId the id of the player
     * @return the hand of the player
     */
    public JPanel getHandPanel(final PlayerId playerId) {
        return this.isHumanPlayer(playerId) ? this.playerHandPanel : this.opponentHandPanel;
    }

    /**
     * Temp method.
     * 
     * @param playerId the id of the player
     * @return the army of the player
     */
    public JPanel getArmyPanel(final PlayerId playerId) {
        return this.isHumanPlayer(playerId) ? this.playerArmyPanel : this.opponentArmyPanel;
    }

    @Override
    public void onCreatureDrawn(final PlayerId playerId, final CardId drawnCard, final CreatureDefinition def) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCreatureDrawn'");
    }

    @Override
    public void onCardPlaced(final PlayerId playerId, final CardId placedCard) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCardPlaced'");
    }

    @Override
    public void onCardHealthChanged(final CardId cardId, final int newHealth) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCardHealthChanged'");
    }

    @Override
    public void onCardDestroyed(final CardId cardId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCardDestroyed'");
    }

    @Override
    public void onPlayerHealthChanged(final PlayerId playerId, final int newHealth) {
        this.updateHealth(playerId, newHealth);
    }

    @Override
    public void onManaChanged(final PlayerId playerId, final int actualMana, final int manaLimit) {
        this.updateMana(playerId, actualMana, manaLimit);
    }

    @Override
    public void onCardExhausted(final PlayerId player, final CardId exhaustedCard) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCardExhausted'");
    }
}
