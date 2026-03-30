package it.unibo.oop.hearthcode.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.oop.hearthcode.model.boardgame.api.GameObserver;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.api.PlayerType;
import it.unibo.oop.hearthcode.view.api.CardComponent;
import it.unibo.oop.hearthcode.view.api.MatchView;
import it.unibo.oop.hearthcode.view.api.PlayerArea;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Implementation of {@link MatchView}.
 */
public final class MatchScene extends JPanel implements MatchView, GameObserver {

    private static final long serialVersionUID = 1L;

    private static final int BUTTONS_NUMBER = 5;
    private static final int MAX_ARMY_SIZE = 5;
    private static final float ACTION_BUTTON_FONT_SIZE = 14f;
    private static final float PANEL_TITLE_FONT_SIZE = 14f;
    private static final int BUTTON_VERTICAL_PADDING = 8;
    private static final int BUTTON_HORIZONTAL_PADDING = 12;
    private static final int PANEL_INNER_PADDING = 10;
    private static final int PANEL_OUTER_PADDING = 4;

    private static final PlayerId HUMAN_PLAYER = PlayerId.HUMAN;
    private static final PlayerId AI_PLAYER = PlayerId.AI;

    private static final Color ACTION_PANEL_BACKGROUND = new Color(41, 57, 39, 220);
    private static final Color ACTION_PANEL_BORDER = new Color(172, 141, 74);
    private static final Color ACTION_PANEL_TITLE = new Color(241, 225, 178);

    private static final Color PRIMARY_BUTTON = new Color(82, 113, 68);
    private static final Color PRIMARY_BUTTON_HOVER = new Color(103, 136, 83);
    private static final Color PRIMARY_BUTTON_DISABLED = new Color(73, 78, 61);
    private static final Color DANGER_BUTTON = new Color(136, 78, 52);
    private static final Color DANGER_BUTTON_HOVER = new Color(160, 97, 66);
    private static final Color BUTTON_TEXT = new Color(247, 239, 214);

    @SuppressFBWarnings(
        value = "SE_TRANSIENT_FIELD_NOT_RESTORED",
        justification = "This Swing UI component is not meant to support meaningful deserialization."
    )
    private final transient PlayerArea humanPlayerArea;

    @SuppressFBWarnings(
        value = "SE_TRANSIENT_FIELD_NOT_RESTORED",
        justification = "This Swing UI component is not meant to support meaningful deserialization."
    )
    private final transient PlayerArea aiPlayerArea;

    @SuppressFBWarnings(
        value = "SE_TRANSIENT_FIELD_NOT_RESTORED",
        justification = "This Swing UI component is not meant to support meaningful deserialization."
    )
    private final transient Map<CardId, CardSlot> cardsById = new LinkedHashMap<>();

    private final JButton attackHeroButton;
    private final JButton attackCreatureButton;
    private final JButton endTurnButton;
    private final JButton placeCardButton;
    private final JButton exitButton;

    private transient PlayerId currentTurnPlayer = HUMAN_PLAYER;
    private final transient SelectionState selection = new SelectionState();
    private transient int humanCurrentMana;

    /**
     * Initializes the match scene.
     */
    public MatchScene() {
        super(new BorderLayout(ViewMetrics.horizontalGap() * 2, ViewMetrics.verticalGap() * 2));

        this.setBorder(BorderFactory.createEmptyBorder(
            ViewMetrics.outerPadding() * 4,
            ViewMetrics.outerPadding() * 4,
            ViewMetrics.outerPadding() * 4,
            ViewMetrics.outerPadding() * 4
        ));
        this.setOpaque(false);

        this.humanPlayerArea = new PlayerAreaImpl(HUMAN_PLAYER);
        this.aiPlayerArea = new PlayerAreaImpl(AI_PLAYER);

        this.attackHeroButton = this.createActionButton("ATTACK HERO", PRIMARY_BUTTON, PRIMARY_BUTTON_HOVER);
        this.attackCreatureButton = this.createActionButton("ATTACK CREATURE", PRIMARY_BUTTON, PRIMARY_BUTTON_HOVER);
        this.placeCardButton = this.createActionButton("PLACE CARD", PRIMARY_BUTTON, PRIMARY_BUTTON_HOVER);
        this.endTurnButton = this.createActionButton("END TURN", PRIMARY_BUTTON, PRIMARY_BUTTON_HOVER);
        this.exitButton = this.createActionButton("EXIT", DANGER_BUTTON, DANGER_BUTTON_HOVER);

        this.add(this.aiPlayerArea.getComponent(), BorderLayout.NORTH);
        this.add(this.createCenterPanel(), BorderLayout.CENTER);
        this.add(this.humanPlayerArea.getComponent(), BorderLayout.SOUTH);

        this.refreshInteractionState();
    }

    private JButton createActionButton(
        final String text,
        final Color background,
        final Color hoverBackground
    ) {
        final JButton button = new JButton(text);
        final Dimension size = new Dimension(ViewMetrics.actionButtonWidth(), ViewMetrics.actionButtonHeight());
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setForeground(BUTTON_TEXT);
        button.setBackground(background);
        button.setFont(button.getFont().deriveFont(Font.BOLD, ACTION_BUTTON_FONT_SIZE));
        button.setBorder(new CompoundBorder(
            new LineBorder(background.brighter(), 1, true),
            new EmptyBorder(
                BUTTON_VERTICAL_PADDING,
                BUTTON_HORIZONTAL_PADDING,
                BUTTON_VERTICAL_PADDING,
                BUTTON_HORIZONTAL_PADDING
            )
        ));
        button.setRolloverEnabled(true);

        button.addChangeListener(event -> {
            if (!button.isEnabled()) {
                button.setBackground(PRIMARY_BUTTON_DISABLED);
                return;
            }
            if (button.getModel().isPressed()) {
                button.setBackground(background.darker());
            } else if (button.getModel().isRollover()) {
                button.setBackground(hoverBackground);
            } else {
                button.setBackground(background);
            }
        });
        return button;
    }

    private JPanel createPanel() {
        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createTitledPanel(final String title) {
        final JPanel panel = this.createPanel();
        final TitledBorder titledBorder = BorderFactory.createTitledBorder(
            new CompoundBorder(
                new LineBorder(ACTION_PANEL_BORDER, 1, true),
                new EmptyBorder(
                    PANEL_INNER_PADDING,
                    PANEL_INNER_PADDING,
                    PANEL_INNER_PADDING,
                    PANEL_INNER_PADDING
                )
            ),
            title
        );
        titledBorder.setTitleColor(ACTION_PANEL_TITLE);
        titledBorder.setTitleFont(titledBorder.getTitleFont().deriveFont(Font.BOLD, PANEL_TITLE_FONT_SIZE));
        panel.setBorder(new CompoundBorder(
            titledBorder,
            BorderFactory.createEmptyBorder(
                PANEL_OUTER_PADDING,
                PANEL_OUTER_PADDING,
                PANEL_OUTER_PADDING,
                PANEL_OUTER_PADDING
            )
        ));
        panel.setBackground(ACTION_PANEL_BACKGROUND);
        panel.setOpaque(true);
        return panel;
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
        final JPanel actionPanel = this.createTitledPanel("Actions");
        actionPanel.setPreferredSize(new Dimension(ViewMetrics.sidePanelWidth(), 0));
        actionPanel.setLayout(new GridLayout(BUTTONS_NUMBER, 1, 0, ViewMetrics.verticalGap() * 2));

        actionPanel.add(this.attackHeroButton);
        actionPanel.add(this.attackCreatureButton);
        actionPanel.add(this.placeCardButton);
        actionPanel.add(this.endTurnButton);
        actionPanel.add(this.exitButton);

        return actionPanel;
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

    private CardSlot getCardSlot(final CardId cardId) {
        final CardSlot cardSlot = this.cardsById.get(cardId);
        if (cardSlot == null) {
            throw new IllegalArgumentException("Card not tracked in match scene: " + cardId);
        }
        return cardSlot;
    }

    private void registerCard(
        final PlayerId owner,
        final CardComponent card,
        final int manaCost,
        final CardZone zone
    ) {
        this.cardsById.put(card.getCardId(), new CardSlot(card, owner, manaCost, zone));
    }

    private void moveCardToArmy(final PlayerId playerId, final CardId cardId) {
        final CardSlot slot = this.getCardSlot(cardId);
        slot.zone = CardZone.ARMY;
        slot.sleeping = true;
        slot.exhausted = false;
        slot.card.setFaceUp(true);
        if (this.isHumanPlayer(playerId)) {
            this.selection.handCard = null;
        }
    }

    private boolean isDormantForInteraction(final CardSlot slot) {
        return slot.sleeping || slot.exhausted;
    }

    private boolean isDormantForVisuals(final CardSlot slot) {
        if (this.isHumanPlayer(slot.owner)) {
            return this.isDormantForInteraction(slot);
        }
        return slot.sleeping;
    }

    private void clearSelection() {
        this.selection.clear();
    }

    private void removeSelectionIfPresent(final CardId cardId) {
        this.selection.remove(cardId);
    }

    private void handleCardSelection(final CardId cardId) {
        final CardSlot slot = this.getCardSlot(cardId);
        if (!slot.card.getComponent().isEnabled()) {
            return;
        }

        if (slot.zone == CardZone.HAND) {
            this.toggleHandSelection(cardId);
        } else if (this.isHumanPlayer(slot.owner)) {
            this.toggleHumanArmySelection(cardId);
        } else {
            this.toggleEnemyArmySelection(cardId);
        }

        this.refreshInteractionState();
    }

    private void toggleHandSelection(final CardId cardId) {
        if (Objects.equals(this.selection.handCard, cardId)) {
            this.selection.handCard = null;
            return;
        }
        this.selection.handCard = cardId;
        this.selection.attacker = null;
        this.selection.target = null;
    }

    private void toggleHumanArmySelection(final CardId cardId) {
        if (Objects.equals(this.selection.attacker, cardId)) {
            this.selection.attacker = null;
            this.selection.target = null;
            return;
        }
        this.selection.handCard = null;
        this.selection.attacker = cardId;
    }

    private void toggleEnemyArmySelection(final CardId cardId) {
        if (this.selection.attacker == null) {
            return;
        }
        if (Objects.equals(this.selection.target, cardId)) {
            this.selection.target = null;
            return;
        }
        this.selection.target = cardId;
    }

    private boolean canPlayCard(final CardSlot slot) {
        return this.isHumanTurn()
            && slot.zone == CardZone.HAND
            && this.isHumanPlayer(slot.owner)
            && slot.manaCost <= this.humanCurrentMana
            && this.humanPlayerArea.getArmyCards().size() < MAX_ARMY_SIZE;
    }

    private boolean canSelectHumanArmyCard(final CardSlot slot) {
        return this.isHumanTurn()
            && slot.zone == CardZone.ARMY
            && this.isHumanPlayer(slot.owner)
            && !this.isDormantForInteraction(slot);
    }

    private boolean canSelectEnemyArmyCard(final CardSlot slot) {
        return this.isHumanTurn()
            && slot.zone == CardZone.ARMY
            && !this.isHumanPlayer(slot.owner)
            && this.selection.attacker != null;
    }

    private boolean canAttackHero() {
        return this.isHumanTurn()
            && this.selection.attacker != null
            && this.selection.target == null;
    }

    private boolean canAttackCreature() {
        return this.isHumanTurn()
            && this.selection.attacker != null
            && this.selection.target != null;
    }

    private boolean canPlaceSelectedCard() {
        return this.selection.handCard != null && this.canPlayCard(this.getCardSlot(this.selection.handCard));
    }

    private boolean isTracked(final CardId cardId) {
        return cardId != null && this.cardsById.containsKey(cardId);
    }

    private void sanitizeSelection() {
        if (!this.isTracked(this.selection.handCard)) {
            this.selection.handCard = null;
        }
        if (!this.isTracked(this.selection.attacker)) {
            this.selection.attacker = null;
        }
        if (!this.isTracked(this.selection.target)) {
            this.selection.target = null;
        }
        if (this.selection.attacker != null
            && this.isDormantForInteraction(this.getCardSlot(this.selection.attacker))) {
            this.selection.attacker = null;
            this.selection.target = null;
        }
        if (this.selection.handCard != null && !this.canPlayCard(this.getCardSlot(this.selection.handCard))) {
            this.selection.handCard = null;
        }
        if (this.selection.target != null && this.selection.attacker == null) {
            this.selection.target = null;
        }
    }

    private void refreshCardState(final CardId cardId, final CardSlot slot) {
        final boolean enabled;
        if (slot.zone == CardZone.HAND) {
            enabled = this.canPlayCard(slot);
        } else if (this.isHumanPlayer(slot.owner)) {
            enabled = this.canSelectHumanArmyCard(slot);
        } else {
            enabled = this.canSelectEnemyArmyCard(slot);
        }

        slot.card.setSelectedVisual(this.isSelected(cardId));
        slot.card.setRestingVisual(this.isDormantForVisuals(slot));
        slot.card.getComponent().setEnabled(enabled);
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
        if (this.selection.handCard != null) {
            return List.of(this.selection.handCard);
        }
        if (this.selection.attacker == null) {
            return List.of();
        }
        if (this.selection.target == null) {
            return List.of(this.selection.attacker);
        }
        return List.of(this.selection.attacker, this.selection.target);
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
            final CardSlot slot = this.getCardSlot(card.getCardId());
            slot.sleeping = false;
            slot.exhausted = false;
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

        this.registerCard(playerId, card, def.manaCost(), CardZone.HAND);
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
        this.getCardSlot(exhaustedCard).exhausted = true;
        if (this.isHumanPlayer(playerId) && Objects.equals(this.selection.attacker, exhaustedCard)) {
            this.selection.attacker = null;
            this.selection.target = null;
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

    private enum CardZone {
        HAND,
        ARMY
    }

    private static final class CardSlot {
        private final CardComponent card;
        private final PlayerId owner;
        private final int manaCost;
        private CardZone zone;
        private boolean sleeping;
        private boolean exhausted;

        CardSlot(
            final CardComponent card,
            final PlayerId owner,
            final int manaCost,
            final CardZone zone
        ) {
            this.card = card;
            this.owner = owner;
            this.manaCost = manaCost;
            this.zone = zone;
        }
    }

    private static final class SelectionState {
        private CardId handCard;
        private CardId attacker;
        private CardId target;

        private void clear() {
            this.handCard = null;
            this.attacker = null;
            this.target = null;
        }

        private boolean contains(final CardId cardId) {
            return Objects.equals(this.handCard, cardId)
                || Objects.equals(this.attacker, cardId)
                || Objects.equals(this.target, cardId);
        }

        private void remove(final CardId cardId) {
            if (Objects.equals(this.handCard, cardId)) {
                this.handCard = null;
            }
            if (Objects.equals(this.attacker, cardId)) {
                this.attacker = null;
            }
            if (Objects.equals(this.target, cardId)) {
                this.target = null;
            }
        }
    }

}
