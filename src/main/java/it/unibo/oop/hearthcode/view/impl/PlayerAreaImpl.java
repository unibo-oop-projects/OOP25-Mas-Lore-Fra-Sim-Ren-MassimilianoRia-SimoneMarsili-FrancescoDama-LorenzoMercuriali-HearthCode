package it.unibo.oop.hearthcode.view.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.view.api.CardArea;
import it.unibo.oop.hearthcode.view.api.CardComponent;
import it.unibo.oop.hearthcode.view.api.PlayerArea;

/**
 * Implementation of {@link PlayerArea}.
 */
public final class PlayerAreaImpl extends JPanel implements PlayerArea {

    private static final long serialVersionUID = 1L;

    private static final int SIDE_PANEL_WIDTH = 200;
    private static final int PLAYER_PANEL_HEIGHT = 220;

    private final transient PlayerId playerId;

    private int currentHealth;
    private int maxHealth;
    private int currentMana;
    private int maxMana;

    private final CardArea handArea;
    private final CardArea armyArea;
    private final JLabel healthLabel;
    private final JLabel manaLabel;

    /**
     * Builds the UI area representing a player.
     *
     * @param playerId the identifier of the represented player
     */
    public PlayerAreaImpl(final PlayerId playerId) {
        super(new BorderLayout(8, 8));
        this.playerId = playerId;

        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        this.setPreferredSize(new Dimension(0, PLAYER_PANEL_HEIGHT));

        this.healthLabel = new JLabel("HP: 0 / 0");
        this.manaLabel = new JLabel("Mana: 0 / 0");

        this.handArea = new CardAreaImpl(playerId + " Hand");
        this.armyArea = new CardAreaImpl(playerId + " Army");

        this.add(this.createStatsPanel(), BorderLayout.WEST);
        this.add(this.createHandPanel(), BorderLayout.CENTER);
    }

    private JPanel createStatsPanel() {
        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(this.playerId + " Stats"));
        panel.setPreferredSize(new Dimension(SIDE_PANEL_WIDTH, 0));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(this.healthLabel);
        panel.add(this.manaLabel);

        return panel;
    }

    private JPanel createHandPanel() {
        final JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.setOpaque(false);
        panel.add(this.handArea.getComponent());
        return panel;
    }

    private void refreshHealth() {
        this.healthLabel.setText("HP: " + this.currentHealth + " / " + this.maxHealth);
    }

    private void refreshMana() {
        this.manaLabel.setText("Mana: " + this.currentMana + " / " + this.maxMana);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public PlayerId getPlayerId() {
        return this.playerId;
    }

    @Override
    public void initHealth(final int health) {
        this.maxHealth = health;
        this.currentHealth = health;
        this.refreshHealth();
    }

    @Override
    public void setCurrentHealth(final int currentHealth) {
        this.currentHealth = currentHealth;
        this.refreshHealth();
    }

    @Override
    public void setMana(final int newCurrentMana, final int newMaxMana) {
        this.currentMana = newCurrentMana;
        this.maxMana = newMaxMana;
        this.refreshMana();
    }

    @Override
    public void addHandCard(final CardComponent card) {
        this.handArea.addCard(card);
    }

    @Override
    public void placeCard(final CardId cardId) {
        final CardComponent card = this.handArea.getCard(cardId);
        this.handArea.removeCard(cardId);
        this.armyArea.addCard(card);
    }

    @Override
    public CardComponent getArmyCard(final CardId cardId) {
        return this.armyArea.getCard(cardId);
    }

    @Override
    public List<CardComponent> getArmyCards() {
        return this.armyArea.getCards();
    }

    @Override
    public List<CardComponent> getHandCards() {
        return this.handArea.getCards();
    }

    @Override
    public JComponent getArmyAreaComponent() {
        return this.armyArea.getComponent();
    }

}
