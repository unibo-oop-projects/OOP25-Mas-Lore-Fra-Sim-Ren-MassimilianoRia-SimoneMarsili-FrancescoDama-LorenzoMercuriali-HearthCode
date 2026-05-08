package it.unibo.oop.hearthcode.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.api.PlayerType;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * UI area representing a player and the related match information.
 */
final class PlayerArea extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final float LABEL_FONT_SIZE = 15f;
    private static final float PANEL_TITLE_FONT_SIZE = 14f;
    private static final int MIN_PROGRESS_BAR_HEIGHT = 18;
    private static final String COUNTER_SEPARATOR = "/";
    private static final Color HEALTH_COLOR = new Color(184, 74, 49);
    private static final Color MANA_COLOR = new Color(72, 135, 122);
    private static final Color PANEL_BACKGROUND = new Color(45, 60, 39, 210);
    private static final Color PANEL_BORDER = new Color(176, 145, 79);
    private static final Color TEXT_COLOR = new Color(244, 232, 194);
    private static final Color BAR_BACKGROUND = new Color(82, 73, 50, 220);
    private final String displayName;
    private final CardArea handArea;
    private final CardArea armyArea;
    private final JLabel healthLabel;
    private final JLabel manaLabel;
    private final JLabel handCounterLabel;
    private final JLabel armyCounterLabel;
    private final JLabel deckCounterLabel;
    private final JProgressBar healthBar;
    private final JProgressBar manaBar;
    private int currentHealth;
    private int maxHealth;
    private int currentMana;
    private int maxMana;
    private int currentHandCards;
    private int currentArmyCards;
    private int currentDeckCards;
    private int maxHandCards;
    private int maxArmyCards;
    private int maxDeckCards;

    /**
     * Builds the UI area representing a player.
     *
     * @param playerId the identifier of the represented player
     */
    PlayerArea(final PlayerId playerId) {
        super(new BorderLayout(ViewMetrics.horizontalGap(), ViewMetrics.verticalGap()));
        this.displayName = playerId.type() == PlayerType.HUMAN_PLAYER ? "Player" : "Enemy";
        this.handArea = new CardArea(this.displayName + " Hand");
        this.armyArea = new CardArea(this.displayName + " Army");
        this.healthLabel = this.createCenteredLabel();
        this.manaLabel = this.createCenteredLabel();
        this.handCounterLabel = this.createCenteredLabel();
        this.armyCounterLabel = this.createCenteredLabel();
        this.deckCounterLabel = this.createCenteredLabel();
        this.healthBar = this.createProgressBar(HEALTH_COLOR);
        this.manaBar = this.createProgressBar(MANA_COLOR);
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(
            ViewMetrics.outerPadding() * 2,
            ViewMetrics.outerPadding() * 2,
            ViewMetrics.outerPadding() * 2,
            ViewMetrics.outerPadding() * 2
        ));
        this.add(this.createStatsPanel(), BorderLayout.WEST);
        this.add(this.handArea, BorderLayout.CENTER);
        this.refreshHealth();
        this.refreshMana();
        this.refreshCardCounters();
    }

    private JLabel createCenteredLabel() {
        final JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setForeground(TEXT_COLOR);
        label.setFont(label.getFont().deriveFont(Font.BOLD, LABEL_FONT_SIZE));
        return label;
    }

    private JProgressBar createProgressBar(final Color color) {
        final JProgressBar bar = new JProgressBar();
        bar.setMinimum(0);
        bar.setMaximum(1);
        bar.setValue(0);
        bar.setForeground(color);
        bar.setBackground(BAR_BACKGROUND);
        bar.setStringPainted(false);
        bar.setBorder(new CompoundBorder(
            new LineBorder(PANEL_BORDER, 1, true),
            new EmptyBorder(1, 1, 1, 1)
        ));
        bar.setAlignmentX(CENTER_ALIGNMENT);
        final Dimension size = new Dimension(
            (int) (ViewMetrics.sidePanelWidth() * 0.82),
            Math.max(MIN_PROGRESS_BAR_HEIGHT, (int) (ViewMetrics.actionButtonHeight() * 0.35))
        );
        bar.setPreferredSize(size);
        bar.setMinimumSize(size);
        bar.setMaximumSize(size);
        return bar;
    }

    private JPanel createStatsPanel() {
        final JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(PANEL_BACKGROUND);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        final TitledBorder titledBorder = BorderFactory.createTitledBorder(
            new CompoundBorder(
                new LineBorder(PANEL_BORDER, 1, true),
                new EmptyBorder(10, 10, 10, 10)
            ),
            this.displayName
        );
        titledBorder.setTitleColor(TEXT_COLOR);
        titledBorder.setTitleFont(titledBorder.getTitleFont().deriveFont(Font.BOLD, PANEL_TITLE_FONT_SIZE));
        panel.setBorder(titledBorder);
        panel.setPreferredSize(new Dimension(ViewMetrics.sidePanelWidth(), 0));
        panel.add(Box.createVerticalGlue());
        panel.add(this.healthLabel);
        panel.add(Box.createVerticalStrut(ViewMetrics.verticalGap()));
        panel.add(this.healthBar);
        panel.add(Box.createVerticalStrut(ViewMetrics.verticalGap() * 3));
        panel.add(this.manaLabel);
        panel.add(Box.createVerticalStrut(ViewMetrics.verticalGap()));
        panel.add(this.manaBar);
        panel.add(Box.createVerticalStrut(ViewMetrics.playerStatsSectionGap()));
        panel.add(this.handCounterLabel);
        panel.add(Box.createVerticalStrut(ViewMetrics.verticalGap()));
        panel.add(this.armyCounterLabel);
        panel.add(Box.createVerticalStrut(ViewMetrics.verticalGap()));
        panel.add(this.deckCounterLabel);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private void refreshHealth() {
        this.healthLabel.setText("HP: " + this.currentHealth + " / " + this.maxHealth);
        this.healthBar.setMaximum(Math.max(1, this.maxHealth));
        this.healthBar.setValue(Math.max(0, Math.min(this.currentHealth, this.healthBar.getMaximum())));
    }

    private void refreshMana() {
        this.manaLabel.setText("Mana: " + this.currentMana + " / " + this.maxMana);
        this.manaBar.setMaximum(Math.max(1, this.maxMana));
        this.manaBar.setValue(Math.max(0, Math.min(this.currentMana, this.manaBar.getMaximum())));
    }

    private void refreshCardCounters() {
        this.handCounterLabel.setText("Hand: " + this.currentHandCards + COUNTER_SEPARATOR + this.maxHandCards);
        this.armyCounterLabel.setText("Army: " + this.currentArmyCards + COUNTER_SEPARATOR + this.maxArmyCards);
        this.deckCounterLabel.setText("Deck: " + this.currentDeckCards + COUNTER_SEPARATOR + this.maxDeckCards);
    }

    public CardComponent getArmyCard(final CardId cardId) {
        return this.armyArea.getCard(cardId);
    }

    public List<CardComponent> getArmyCards() {
        return this.armyArea.getCards();
    }

    public JComponent getArmyAreaComponent() {
        return this.armyArea;
    }

    public void removeArmyCard(final CardId cardId) {
        this.armyArea.removeCard(cardId);
    }

    public void initHealth(final int health) {
        this.maxHealth = health;
        this.currentHealth = health;
        this.refreshHealth();
    }

    public void setCurrentHealth(final int currentHealth) {
        this.currentHealth = currentHealth;
        this.refreshHealth();
    }

    public void setMana(final int newCurrentMana, final int newMaxMana) {
        this.currentMana = newCurrentMana;
        this.maxMana = newMaxMana;
        this.refreshMana();
    }

    public void resetCardCounters(
        final int handCardsLimit,
        final int armyCardsLimit,
        final int deckCardsLimit
    ) {
        this.currentHandCards = 0;
        this.currentArmyCards = 0;
        this.currentDeckCards = deckCardsLimit;
        this.maxHandCards = handCardsLimit;
        this.maxArmyCards = armyCardsLimit;
        this.maxDeckCards = deckCardsLimit;
        this.refreshCardCounters();
    }

    public void registerCardDrawn() {
        this.currentHandCards = Math.min(this.maxHandCards, this.currentHandCards + 1);
        this.currentDeckCards = Math.max(0, this.currentDeckCards - 1);
        this.refreshCardCounters();
    }

    public void registerCardBurned() {
        this.currentDeckCards = Math.max(0, this.currentDeckCards - 1);
        this.refreshCardCounters();
    }

    public void registerCardPlaced() {
        this.currentHandCards = Math.max(0, this.currentHandCards - 1);
        this.currentArmyCards = Math.min(this.maxArmyCards, this.currentArmyCards + 1);
        this.refreshCardCounters();
    }

    public void registerCardDestroyed() {
        this.currentArmyCards = Math.max(0, this.currentArmyCards - 1);
        this.refreshCardCounters();
    }

    public void addHandCard(final CardComponent card) {
        this.handArea.addCard(card);
    }

    public void placeCard(final CardId cardId) {
        final CardComponent card = this.handArea.getCard(cardId);
        this.handArea.removeCard(cardId);
        this.armyArea.addCard(card);
    }

}
