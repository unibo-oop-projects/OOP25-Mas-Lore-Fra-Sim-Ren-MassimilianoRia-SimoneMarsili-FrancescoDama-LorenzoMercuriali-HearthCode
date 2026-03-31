package it.unibo.oop.hearthcode.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.model.boardgame.api.GameObserver;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.api.PlayerType;
import it.unibo.oop.hearthcode.view.api.CardComponent;
import it.unibo.oop.hearthcode.view.api.MatchCardSlot;
import it.unibo.oop.hearthcode.view.api.MatchView;
import it.unibo.oop.hearthcode.view.api.PlayerArea;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Implementation of {@link MatchView}.
 */
public final class MatchScene extends JPanel implements MatchView, GameObserver {

    private static final long serialVersionUID = 1L;

    private static final int MAX_ARMY_SIZE = 5;
    private static final PlayerId HUMAN_PLAYER = PlayerId.HUMAN;
    private static final PlayerId AI_PLAYER = PlayerId.AI;
    private static final Color PRIMARY_BUTTON = new Color(82, 113, 68);
    private static final Color PRIMARY_BUTTON_HOVER = new Color(103, 136, 83);
    private static final Color DANGER_BUTTON = new Color(136, 78, 52);
    private static final Color DANGER_BUTTON_HOVER = new Color(160, 97, 66);
    private final transient MatchSceneUiFactoryImpl uiFactory = new MatchSceneUiFactoryImpl();
    private final PlayerArea humanPlayerArea;
    private final PlayerArea aiPlayerArea;
    private final Map<CardId, MatchCardSlot> cardsById = new LinkedHashMap<>();
    private final JButton attackHeroButton;
    private final JButton attackCreatureButton;
    private final JButton endTurnButton;
    private final JButton placeCardButton;
    private final JButton exitButton;
    private PlayerId currentTurnPlayer = HUMAN_PLAYER;
    private final transient MatchSelectionStateImpl selection = new MatchSelectionStateImpl();
    private transient int humanCurrentMana;

    /**
     * Initializes the match scene.
     */
    public MatchScene() {
        super(new BorderLayout(ViewMetrics.horizontalGap(), ViewMetrics.verticalGap()));
        this.setBorder(BorderFactory.createEmptyBorder(
            ViewMetrics.outerPadding(),
            ViewMetrics.outerPadding(),
            ViewMetrics.outerPadding(),
            ViewMetrics.outerPadding()
        ));
        this.setOpaque(false);
        this.humanPlayerArea = new PlayerAreaImpl(HUMAN_PLAYER);
        this.aiPlayerArea = new PlayerAreaImpl(AI_PLAYER);
        this.attackHeroButton = this.uiFactory.createActionButton(
            "ATTACK HERO",
            PRIMARY_BUTTON,
            PRIMARY_BUTTON_HOVER
        );
        this.attackCreatureButton = this.uiFactory.createActionButton(
            "ATTACK CREATURE",
            PRIMARY_BUTTON,
            PRIMARY_BUTTON_HOVER
        );
        this.placeCardButton = this.uiFactory.createActionButton(
            "PLACE CARD",
            PRIMARY_BUTTON,
            PRIMARY_BUTTON_HOVER
        );
        this.endTurnButton = this.uiFactory.createActionButton(
            "END TURN",
            PRIMARY_BUTTON,
            PRIMARY_BUTTON_HOVER
        );
        this.exitButton = this.uiFactory.createActionButton("EXIT", DANGER_BUTTON, DANGER_BUTTON_HOVER);
        this.add(this.aiPlayerArea.getComponent(), BorderLayout.NORTH);
        this.add(this.createCenterPanel(), BorderLayout.CENTER);
        this.add(this.humanPlayerArea.getComponent(), BorderLayout.SOUTH);
        this.refreshInteractionState();
    }

    private JPanel createPanel() {
        return this.uiFactory.createPanel();
    }

    private PlayerArea getPlayerArea(final PlayerId playerId) {
        return this.isHumanPlayer(playerId) ? this.humanPlayerArea : this.aiPlayerArea;
    }

    private JComponent createCenterPanel() {
        final JPanel panel = this.createPanel();
        panel.setLayout(new BorderLayout(ViewMetrics.horizontalGap() * 2, ViewMetrics.verticalGap() * 2));
        panel.add(this.createActionPanel(), BorderLayout.WEST);
        final JPanel armiesPanel = this.createPanel();
        armiesPanel.setLayout(new GridLayout(2, 1, 0, ViewMetrics.verticalGap() * 2));
        armiesPanel.add(this.aiPlayerArea.getArmyAreaComponent());
        armiesPanel.add(this.humanPlayerArea.getArmyAreaComponent());
        panel.add(armiesPanel, BorderLayout.CENTER);
        return panel;
    }

    private JComponent createActionPanel() {
        return this.uiFactory.createActionPanel(
            this.attackHeroButton,
            this.attackCreatureButton,
            this.placeCardButton,
            this.endTurnButton,
            this.exitButton
        );
    }

    private void updateHealth(final PlayerId playerId, final int newHealth) {
        this.getPlayerArea(playerId).setCurrentHealth(newHealth);
    }

    private void updateMana(final PlayerId playerId, final int currentMana, final int maxMana) {
        this.getPlayerArea(playerId).setMana(currentMana, maxMana);
        if (this.isHumanPlayer(playerId)) {
            this.humanCurrentMana = currentMana;
        }
    }

    private boolean isHumanPlayer(final PlayerId playerId) {
        return playerId.type() == PlayerType.HUMAN_PLAYER;
    }

    private boolean isHumanTurn() {
        return this.currentTurnPlayer.equals(HUMAN_PLAYER);
    }

    private boolean isSelected(final CardId cardId) {
        return this.selection.contains(cardId);
    }

    private MatchCardSlot getCardSlot(final CardId cardId) {
        final MatchCardSlot cardSlot = this.cardsById.get(cardId);
        if (cardSlot == null) {
            throw new IllegalArgumentException("Card not tracked in match scene: " + cardId);
        }
        return cardSlot;
    }

    private void registerCard(
        final PlayerId owner,
        final CardComponent card,
        final int manaCost,
        final MatchCardZone zone
    ) {
        this.cardsById.put(card.getCardId(), new MatchCardSlotImpl(card, owner, manaCost, zone));
    }

    private void moveCardToArmy(final PlayerId playerId, final CardId cardId) {
        final MatchCardSlot slot = this.getCardSlot(cardId);
        slot.moveToArmy();
        if (this.isHumanPlayer(playerId)) {
            this.selection.clearHandCard();
        }
    }

    private void clearSelection() {
        this.selection.clear();
    }

    private void removeSelectionIfPresent(final CardId cardId) {
        this.selection.remove(cardId);
    }

    private void handleCardSelection(final CardId cardId) {
        final MatchCardSlot slot = this.getCardSlot(cardId);
        if (!slot.getCard().getComponent().isEnabled()) {
            return;
        }
        if (slot.getZone() == MatchCardZone.HAND) {
            this.toggleHandSelection(cardId);
        } else if (this.isHumanPlayer(slot.getOwner())) {
            this.toggleHumanArmySelection(cardId);
        } else {
            this.toggleEnemyArmySelection(cardId);
        }
        this.refreshInteractionState();
    }

    private void toggleHandSelection(final CardId cardId) {
        this.selection.toggleHandCard(cardId);
    }

    private void toggleHumanArmySelection(final CardId cardId) {
        this.selection.toggleAttacker(cardId);
    }

    private void toggleEnemyArmySelection(final CardId cardId) {
        if (this.selection.getAttacker() == null) {
            return;
        }
        this.selection.toggleTarget(cardId);
    }

    private boolean canPlayCard(final MatchCardSlot slot) {
        return this.isHumanTurn()
            && slot.getZone() == MatchCardZone.HAND
            && this.isHumanPlayer(slot.getOwner())
            && slot.getManaCost() <= this.humanCurrentMana
            && this.humanPlayerArea.getArmyCards().size() < MAX_ARMY_SIZE;
    }

    private boolean canSelectHumanArmyCard(final MatchCardSlot slot) {
        return this.isHumanTurn()
            && slot.getZone() == MatchCardZone.ARMY
            && this.isHumanPlayer(slot.getOwner())
            && !slot.isDormantForInteraction();
    }

    private boolean canSelectEnemyArmyCard(final MatchCardSlot slot) {
        return this.isHumanTurn()
            && slot.getZone() == MatchCardZone.ARMY
            && !this.isHumanPlayer(slot.getOwner())
            && this.selection.getAttacker() != null;
    }

    private boolean canAttackHero() {
        return this.isHumanTurn()
            && this.selection.getAttacker() != null
            && this.selection.getTarget() == null;
    }

    private boolean canAttackCreature() {
        return this.isHumanTurn()
            && this.selection.getAttacker() != null
            && this.selection.getTarget() != null;
    }

    private boolean canPlaceSelectedCard() {
        return this.selection.getHandCard() != null
            && this.canPlayCard(this.getCardSlot(this.selection.getHandCard()));
    }

    private boolean isTracked(final CardId cardId) {
        return cardId != null && this.cardsById.containsKey(cardId);
    }

    private void sanitizeSelection() {
        if (!this.isTracked(this.selection.getHandCard())) {
            this.selection.clearHandCard();
        }
        if (!this.isTracked(this.selection.getAttacker())) {
            this.selection.clearAttacker();
        }
        if (!this.isTracked(this.selection.getTarget())) {
            this.selection.clearTarget();
        }
        if (this.selection.getAttacker() != null
            && this.getCardSlot(this.selection.getAttacker()).isDormantForInteraction()) {
            this.selection.clearCombatSelection();
        }
        if (this.selection.getHandCard() != null
            && !this.canPlayCard(this.getCardSlot(this.selection.getHandCard()))) {
            this.selection.clearHandCard();
        }
        if (this.selection.getTarget() != null && this.selection.getAttacker() == null) {
            this.selection.clearTarget();
        }
    }

    private void refreshCardState(
        final CardId cardId,
        final MatchCardSlot slot
    ) {
        final boolean enabled;
        if (slot.getZone() == MatchCardZone.HAND) {
            enabled = this.canPlayCard(slot);
        } else if (this.isHumanPlayer(slot.getOwner())) {
            enabled = this.canSelectHumanArmyCard(slot);
        } else {
            enabled = this.canSelectEnemyArmyCard(slot);
        }
        slot.getCard().setSelectedVisual(this.isSelected(cardId));
        slot.getCard().setRestingVisual(slot.isDormantForVisuals());
        slot.getCard().getComponent().setEnabled(enabled);
    }

    private void refreshActionButtons() {
        this.attackHeroButton.setEnabled(this.canAttackHero());
        this.attackCreatureButton.setEnabled(this.canAttackCreature());
        this.placeCardButton.setEnabled(this.canPlaceSelectedCard());
        this.endTurnButton.setEnabled(this.isHumanTurn());
        this.exitButton.setEnabled(true);
    }

    private void refreshInteractionState() {
        this.sanitizeSelection();
        this.cardsById.forEach(this::refreshCardState);
        this.refreshActionButtons();
        this.revalidate();
        this.repaint();
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
    public List<CardId> getSelectedCards() {
        return this.selection.toSelectedCards();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttackHero(final Runnable action) {
        this.attackHeroButton.addActionListener(event -> action.run());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttackCreature(final Runnable action) {
        this.attackCreatureButton.addActionListener(event -> action.run());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPlaceCard(final Runnable action) {
        this.placeCardButton.addActionListener(event -> action.run());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEndTurn(final Runnable action) {
        this.endTurnButton.addActionListener(event -> action.run());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onExitGame(final Runnable action) {
        this.exitButton.addActionListener(event -> action.run());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean confirmExitGame() {
        final int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to quit the match?",
            "Quit Match",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showErrorPanel(final String s) {
        JOptionPane.showMessageDialog(this, s, "Error", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onGameStarted(final Map<PlayerId, Integer> playersHealth) {
        this.currentTurnPlayer = HUMAN_PLAYER;
        this.clearSelection();
        playersHealth.forEach((playerId, health) -> {
            this.getPlayerArea(playerId).initHealth(health);
            this.getPlayerArea(playerId).setMana(0, 0);
        });
        this.refreshInteractionState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTurnSwitch(final PlayerId nextPlayer) {
        this.currentTurnPlayer = nextPlayer;
        this.clearSelection();
        this.getPlayerArea(nextPlayer).getArmyCards().forEach(card -> {
            this.getCardSlot(card.getCardId()).wakeUp();
        });
        this.refreshInteractionState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreatureDrawn(final PlayerId playerId, final CardId drawnCard, final CreatureDefinition def) {
        final CardComponent card = new CardComponentImpl(drawnCard, def);
        card.getComponent().addActionListener(event -> this.handleCardSelection(card.getCardId()));
        if (this.isHumanPlayer(playerId)) {
            card.setFaceUp(true);
        }
        this.registerCard(playerId, card, def.manaCost(), MatchCardZone.HAND);
        this.getPlayerArea(playerId).addHandCard(card);
        this.refreshInteractionState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCardPlaced(final PlayerId playerId, final CardId placedCard) {
        this.getPlayerArea(playerId).placeCard(placedCard);
        this.moveCardToArmy(playerId, placedCard);
        this.refreshInteractionState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCardHealthChanged(final PlayerId playerId, final CardId cardId, final int newHealth) {
        this.getPlayerArea(playerId).getArmyCard(cardId).setHealth(newHealth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCardDestroyed(final PlayerId playerId, final CardId cardId) {
        this.removeSelectionIfPresent(cardId);
        this.cardsById.remove(cardId);
        this.getPlayerArea(playerId).removeArmyCard(cardId);
        this.refreshInteractionState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPlayerHealthChanged(final PlayerId playerId, final int newHealth) {
        this.updateHealth(playerId, newHealth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onManaChanged(final PlayerId playerId, final int actualMana, final int manaLimit) {
        this.updateMana(playerId, actualMana, manaLimit);
        this.refreshInteractionState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCardExhausted(final PlayerId playerId, final CardId exhaustedCard) {
        this.getCardSlot(exhaustedCard).exhaust();
        if (this.isHumanPlayer(playerId) && Objects.equals(this.selection.getAttacker(), exhaustedCard)) {
            this.selection.clearCombatSelection();
        }
        this.refreshInteractionState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCardBurned(final PlayerId playerId) {
        if (this.isHumanPlayer(playerId)) {
            this.showErrorPanel("Your hand is full. Drawn card is burned!");
        }
    }

}
