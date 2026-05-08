package it.unibo.oop.hearthcode.view.impl;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.model.boardgame.api.PlayerInitialState;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.api.PlayerType;
import it.unibo.oop.hearthcode.view.api.MatchView;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Implementation of {@link MatchView}.
 */
public final class MatchScene extends JPanel implements MatchView {

    private static final long serialVersionUID = 1L;

    private static final PlayerId HUMAN_PLAYER = PlayerId.HUMAN;
    private static final PlayerId AI_PLAYER = PlayerId.AI;
    private final PlayerArea humanPlayerArea;
    private final PlayerArea aiPlayerArea;
    private final MatchActionPanel actionPanel;
    private transient Map<CardId, MatchCardSlot> cardsById = new LinkedHashMap<>();
    private transient Map<CardId, CardComponent> cardComponentsById = new LinkedHashMap<>();
    private PlayerId currentTurnPlayer;
    private transient MatchSelectionState selection = new MatchSelectionState();
    private transient int humanCurrentMana;
    private int humanArmySizeLimit;

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
        this.humanPlayerArea = new PlayerArea(HUMAN_PLAYER);
        this.aiPlayerArea = new PlayerArea(AI_PLAYER);
        this.actionPanel = new MatchActionPanel();
        this.add(this.aiPlayerArea, BorderLayout.NORTH);
        this.add(this.createCenterPanel(), BorderLayout.CENTER);
        this.add(this.humanPlayerArea, BorderLayout.SOUTH);
        this.refreshInteractionState();
    }

    private void readObject(final ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        this.cardsById = new LinkedHashMap<>();
        this.cardComponentsById = new LinkedHashMap<>();
        this.selection = new MatchSelectionState();
    }

    private PlayerArea getPlayerArea(final PlayerId playerId) {
        return this.isHumanPlayer(playerId) ? this.humanPlayerArea : this.aiPlayerArea;
    }

    private JComponent createCenterPanel() {
        final JPanel panel = createTransparentPanel();
        panel.setLayout(new BorderLayout(ViewMetrics.horizontalGap() * 2, ViewMetrics.verticalGap() * 2));
        panel.add(this.actionPanel, BorderLayout.WEST);
        final JPanel armiesPanel = createTransparentPanel();
        armiesPanel.setLayout(new GridLayout(2, 1, 0, ViewMetrics.verticalGap() * 2));
        armiesPanel.add(this.aiPlayerArea.getArmyAreaComponent());
        armiesPanel.add(this.humanPlayerArea.getArmyAreaComponent());
        panel.add(armiesPanel, BorderLayout.CENTER);
        return panel;
    }

    private static JPanel createTransparentPanel() {
        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
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
        return HUMAN_PLAYER.equals(this.currentTurnPlayer);
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

    private CardComponent getCardComponent(final CardId cardId) {
        final CardComponent cardComponent = this.cardComponentsById.get(cardId);
        if (cardComponent == null) {
            throw new IllegalArgumentException("Card component not tracked in match scene: " + cardId);
        }
        return cardComponent;
    }

    private void registerCard(
        final PlayerId owner,
        final CardComponent card,
        final int manaCost,
        final MatchCardZone zone
    ) {
        this.cardsById.put(card.getCardId(), new MatchCardSlot(owner, manaCost, zone));
        this.cardComponentsById.put(card.getCardId(), card);
    }

    private void moveCardToArmy(final PlayerId playerId, final CardId cardId) {
        final MatchCardSlot slot = this.getCardSlot(cardId);
        slot.moveToArmy();
        this.getCardComponent(cardId).setFaceUp(true);
        if (this.isHumanPlayer(playerId)) {
            this.selection.clearHandCard();
        }
    }

    private void clearSelection() {
        this.selection.clear();
    }

    private void handleCardSelection(final CardId cardId) {
        final MatchCardSlot slot = this.getCardSlot(cardId);
        if (!this.getCardComponent(cardId).isEnabled()) {
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
            && this.humanPlayerArea.getArmyCards().size() < this.humanArmySizeLimit;
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
        final CardComponent card = this.getCardComponent(cardId);
        final boolean enabled;
        if (slot.getZone() == MatchCardZone.HAND) {
            enabled = this.canPlayCard(slot);
        } else if (this.isHumanPlayer(slot.getOwner())) {
            enabled = this.canSelectHumanArmyCard(slot);
        } else {
            enabled = this.canSelectEnemyArmyCard(slot);
        }
        card.setSelectedVisual(this.isSelected(cardId));
        card.setRestingVisual(slot.isDormantForVisuals());
        card.setEnabled(enabled);
    }

    private void refreshActionButtons() {
        this.actionPanel.setActionsEnabled(
            this.canAttackHero(),
            this.canAttackCreature(),
            this.canPlaceSelectedCard(),
            this.isHumanTurn()
        );
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
        this.actionPanel.onAttackHero(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttackCreature(final Runnable action) {
        this.actionPanel.onAttackCreature(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPlaceCard(final Runnable action) {
        this.actionPanel.onPlaceCard(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEndTurn(final Runnable action) {
        this.actionPanel.onEndTurn(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onExitGame(final Runnable action) {
        this.actionPanel.onExitGame(action);
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
    public void onGameStarted(
        final PlayerId startingPlayer,
        final Map<PlayerId, PlayerInitialState> playersInitialState
    ) {
        this.currentTurnPlayer = startingPlayer;
        this.clearSelection();
        playersInitialState.forEach((playerId, initialState) -> {
            this.getPlayerArea(playerId).initHealth(initialState.health());
            this.getPlayerArea(playerId).setMana(0, 0);
            this.getPlayerArea(playerId).resetCardCounters(
                initialState.handCardsLimit(),
                initialState.armyCardsLimit(),
                initialState.deckCardsLimit()
            );
            if (this.isHumanPlayer(playerId)) {
                this.humanArmySizeLimit = initialState.armyCardsLimit();
            }
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
        final CardComponent card = new CardComponent(drawnCard, def);
        card.addActionListener(event -> this.handleCardSelection(card.getCardId()));
        if (this.isHumanPlayer(playerId)) {
            card.setFaceUp(true);
        }
        this.registerCard(playerId, card, def.manaCost(), MatchCardZone.HAND);
        this.getPlayerArea(playerId).addHandCard(card);
        this.getPlayerArea(playerId).registerCardDrawn();
        this.refreshInteractionState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCardPlaced(final PlayerId playerId, final CardId placedCard) {
        this.getPlayerArea(playerId).placeCard(placedCard);
        this.moveCardToArmy(playerId, placedCard);
        this.getPlayerArea(playerId).registerCardPlaced();
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
        this.selection.remove(cardId);
        this.cardsById.remove(cardId);
        this.cardComponentsById.remove(cardId);
        this.getPlayerArea(playerId).removeArmyCard(cardId);
        this.getPlayerArea(playerId).registerCardDestroyed();
        this.refreshInteractionState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPlayerHealthChanged(final PlayerId playerId, final int newHealth) {
        this.getPlayerArea(playerId).setCurrentHealth(newHealth);
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
        this.getPlayerArea(playerId).registerCardBurned();
        if (this.isHumanPlayer(playerId)) {
            this.showErrorPanel("Your hand is full. Drawn card is burned!");
        }
    }

}
