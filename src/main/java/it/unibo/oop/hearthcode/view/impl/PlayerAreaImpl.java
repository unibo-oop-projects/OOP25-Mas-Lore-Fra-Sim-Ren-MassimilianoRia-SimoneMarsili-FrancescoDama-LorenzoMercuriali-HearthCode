package it.unibo.oop.hearthcode.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.api.PlayerType;
import it.unibo.oop.hearthcode.view.api.CardArea;
import it.unibo.oop.hearthcode.view.api.CardComponent;
import it.unibo.oop.hearthcode.view.api.PlayerArea;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Implementation of {@link PlayerArea}.
 */
public final class PlayerAreaImpl extends JPanel implements PlayerArea {

    private static final long serialVersionUID = 1L;

    private static final Color HEALTH_COLOR = new Color(185, 45, 45);
    private static final Color MANA_COLOR = new Color(70, 150, 255);

    private final transient PlayerId playerId;
    private final CardArea handArea;
    private final CardArea armyArea;
    private final JLabel healthLabel;
    private final JLabel manaLabel;
    private final JProgressBar healthBar;
    private final JProgressBar manaBar;

    private int currentHealth;
    private int maxHealth;
    private int currentMana;
    private int maxMana;

    /**
     * Builds the UI area representing a player.
     *
     * @param playerId the identifier of the represented player
     */
    public PlayerAreaImpl(final PlayerId playerId) {
        super(new BorderLayout(ViewMetrics.horizontalGap(), ViewMetrics.verticalGap()));
        this.playerId = playerId;
        this.handArea = new CardAreaImpl(this.displayName() + " Hand");
        this.armyArea = new CardAreaImpl(this.displayName() + " Army");

        this.healthLabel = this.createCenteredLabel();
        this.manaLabel = this.createCenteredLabel();

        this.healthBar = this.createProgressBar(HEALTH_COLOR);
        this.manaBar = this.createProgressBar(MANA_COLOR);

        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(
            ViewMetrics.outerPadding(),
            ViewMetrics.outerPadding(),
            ViewMetrics.outerPadding(),
            ViewMetrics.outerPadding()
        ));

        this.add(this.createStatsPanel(), BorderLayout.WEST);
        this.add(this.handArea.getComponent(), BorderLayout.CENTER);

        this.refreshHealth();
        this.refreshMana();
    }

    private String displayName() {
        return this.playerId.type() == PlayerType.HUMAN_PLAYER ? "Player" : "Enemy";
    }

    private JLabel createCenteredLabel() {
        final JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setAlignmentX(CENTER_ALIGNMENT);
        return label;
    }

    private JProgressBar createProgressBar(final Color color) {
        final JProgressBar bar = new JProgressBar();
        bar.setMinimum(0);
        bar.setMaximum(1);
        bar.setValue(0);
        bar.setForeground(color);
        bar.setStringPainted(false);
        bar.setBorder(BorderFactory.createEmptyBorder());
        bar.setAlignmentX(CENTER_ALIGNMENT);

        final Dimension size = new Dimension(
            (int) (ViewMetrics.sidePanelWidth() * 0.82),
            Math.max(18, (int) (ViewMetrics.actionButtonHeight() * 0.35))
        );
        bar.setPreferredSize(size);
        bar.setMinimumSize(size);
        bar.setMaximumSize(size);

        return bar;
    }

    private JPanel createStatsPanel() {
        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(this.displayName()));
        panel.setPreferredSize(new Dimension(ViewMetrics.sidePanelWidth(), 0));

        panel.add(Box.createVerticalGlue());

        panel.add(this.healthLabel);
        panel.add(Box.createVerticalStrut(ViewMetrics.verticalGap()));
        panel.add(this.healthBar);

        panel.add(Box.createVerticalStrut(ViewMetrics.verticalGap() * 3));

        panel.add(this.manaLabel);
        panel.add(Box.createVerticalStrut(ViewMetrics.verticalGap()));
        panel.add(this.manaBar);

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

    /**
     * {@inheritDoc}
     */
    @Override
    public JComponent getComponent() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerId getPlayerId() {
        return this.playerId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardComponent getArmyCard(final CardId cardId) {
        return this.armyArea.getCard(cardId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CardComponent> getArmyCards() {
        return this.armyArea.getCards();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CardComponent> getHandCards() {
        return this.handArea.getCards();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JComponent getArmyAreaComponent() {
        return this.armyArea.getComponent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeArmyCard(final CardId cardId) {
        this.armyArea.removeCard(cardId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initHealth(final int health) {
        this.maxHealth = health;
        this.currentHealth = health;
        this.refreshHealth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCurrentHealth(final int currentHealth) {
        this.currentHealth = currentHealth;
        this.refreshHealth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMana(final int newCurrentMana, final int newMaxMana) {
        this.currentMana = newCurrentMana;
        this.maxMana = newMaxMana;
        this.refreshMana();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addHandCard(final CardComponent card) {
        this.handArea.addCard(card);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placeCard(final CardId cardId) {
        final CardComponent card = this.handArea.getCard(cardId);
        this.handArea.removeCard(cardId);
        this.armyArea.addCard(card);
    }

}
