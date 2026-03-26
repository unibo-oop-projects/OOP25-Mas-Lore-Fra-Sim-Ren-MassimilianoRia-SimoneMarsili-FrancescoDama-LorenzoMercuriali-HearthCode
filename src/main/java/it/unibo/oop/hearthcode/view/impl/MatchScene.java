package it.unibo.oop.hearthcode.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.oop.hearthcode.model.boardgame.api.GameObserver;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.api.PlayerType;
import it.unibo.oop.hearthcode.view.api.CardComponent;
import it.unibo.oop.hearthcode.view.api.MatchView;
import it.unibo.oop.hearthcode.view.api.PlayerArea;
import it.unibo.oop.hearthcode.view.utility.ImageLoader;

/**
 * Implementation of {@link MatchView}.
 */
public final class MatchScene extends JPanel implements MatchView, GameObserver {

    private static final long serialVersionUID = 1L;

    private static final int SIDE_PANEL_WIDTH = 200;
    private static final PlayerId HUMAN_PLAYER = new PlayerId(PlayerType.HUMAN_PLAYER);
    private static final PlayerId IA_PLAYER = new PlayerId(PlayerType.IA_PLAYER);

    @SuppressFBWarnings(
        value = "SE_TRANSIENT_FIELD_NOT_RESTORED",
        justification = "This Swing UI component is not meant to support meaningful deserialization."
    )
    private final transient PlayerArea humanPlayerArea;

    @SuppressFBWarnings(
        value = "SE_TRANSIENT_FIELD_NOT_RESTORED",
        justification = "This Swing UI component is not meant to support meaningful deserialization."
    )
    private final transient PlayerArea iaPlayerArea;

    @SuppressFBWarnings(
        value = "SE_TRANSIENT_FIELD_NOT_RESTORED",
        justification = "This Swing UI component is not meant to support meaningful deserialization."
    )
    private final transient List<CardId> selectedCards = new ArrayList<>();

    private final JButton attackHeroButton;
    private final JButton attackCreatureButton;
    private final JButton endTurnButton;
    private final JButton placeCardButton;
    private final JButton exitButton;

    /**
     * Initializes the match scene.
     */
    public MatchScene() {
        super(new BorderLayout());

        this.humanPlayerArea = new PlayerAreaImpl(HUMAN_PLAYER);
        this.iaPlayerArea = new PlayerAreaImpl(IA_PLAYER);

        this.attackHeroButton = new JButton("ATTACK HERO");
        this.attackCreatureButton = new JButton("ATTACK CREATURE");
        this.placeCardButton = new JButton("PLACE CARD");
        this.endTurnButton = new JButton("END TURN");
        this.exitButton = new JButton("EXIT");

        this.add(this.iaPlayerArea.getComponent(), BorderLayout.NORTH);
        this.add(this.createCenterPanel(), BorderLayout.CENTER);
        this.add(this.humanPlayerArea.getComponent(), BorderLayout.SOUTH);
    }

    private JPanel createSimplePanel() {
        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createTitledPanel(final String title) {
        final JPanel panel = this.createSimplePanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    private PlayerArea getPlayerArea(final PlayerId playerId) {
        return this.isHumanPlayer(playerId) ? this.humanPlayerArea : this.iaPlayerArea;
    }

    private JComponent createCenterPanel() {
        final JPanel panel = this.createSimplePanel();
        panel.setLayout(new BorderLayout());
        panel.add(this.createActionPanel(), BorderLayout.WEST);

        final JPanel armiesPanel = this.createSimplePanel();
        armiesPanel.setLayout(new BorderLayout());
        armiesPanel.add(this.iaPlayerArea.getArmyAreaComponent(), BorderLayout.NORTH);
        armiesPanel.add(this.humanPlayerArea.getArmyAreaComponent(), BorderLayout.SOUTH);

        panel.add(armiesPanel, BorderLayout.CENTER);
        return panel;
    }

    private JComponent createActionPanel() {
        final JPanel actionPanel = this.createTitledPanel("Actions");
        actionPanel.setPreferredSize(new Dimension(SIDE_PANEL_WIDTH, 0));
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));

        this.attackHeroButton.setAlignmentX(CENTER_ALIGNMENT);
        this.attackCreatureButton.setAlignmentX(CENTER_ALIGNMENT);
        this.placeCardButton.setAlignmentX(CENTER_ALIGNMENT);
        this.endTurnButton.setAlignmentX(CENTER_ALIGNMENT);
        this.exitButton.setAlignmentX(CENTER_ALIGNMENT);

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
    }

    private boolean isHumanPlayer(final PlayerId playerId) {
        return playerId.type() == PlayerType.HUMAN_PLAYER;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public List<CardId> getSelectedCards() {
        return List.copyOf(this.selectedCards);
    }

    @Override
    public void onAttackHero(final Runnable action) {
        this.attackHeroButton.addActionListener(event -> action.run());
    }

    @Override
    public void onAttackCreature(final Runnable action) {
       this.attackCreatureButton.addActionListener(event -> action.run());
    }

    @Override
    public void onPlaceCard(final Runnable action) {
        this.placeCardButton.addActionListener(event -> action.run());
    }

    @Override
    public void onEndTurn(final Runnable action) {
        this.endTurnButton.addActionListener(event -> action.run());
    }

    @Override
    public void onExitGame(final Runnable action) {
       this.exitButton.addActionListener(event -> action.run());
    }

    @Override
    public boolean confirmExitGame() {
        final int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to exit the game?",
            "Exit Application",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        return result == JOptionPane.YES_OPTION;
    }

    @Override
    public void onGameStarted(final Map<PlayerId, Integer> playersHealth) {
        playersHealth.forEach((playerId, health) -> {
            this.getPlayerArea(playerId).initHealth(health);
            this.getPlayerArea(playerId).setMana(0, 0);
        });
        this.revalidate();
        this.repaint();
    }

    @Override
    public void onTurnSwitch(final PlayerId nextPlayer) {
        final boolean isHumanTurn = this.isHumanPlayer(nextPlayer);
        this.attackHeroButton.setEnabled(isHumanTurn);
        this.attackCreatureButton.setEnabled(isHumanTurn);
        this.placeCardButton.setEnabled(isHumanTurn);
        this.endTurnButton.setEnabled(isHumanTurn);
        this.exitButton.setEnabled(isHumanTurn);

        Stream.concat(
            this.iaPlayerArea.getArmyCards().stream(),
            Stream.concat(
                this.humanPlayerArea.getArmyCards().stream(),
                this.humanPlayerArea.getHandCards().stream()
            )
        ).forEach(card -> card.getComponent().setEnabled(isHumanTurn));
    }

    @Override
    public void onCreatureDrawn(final PlayerId playerId, final CardId drawnCard, final CreatureDefinition def) {
        final ImageIcon front = ImageLoader.load("/images/cards/creatures/" + def.name() + ".png", 30, 70);
        final ImageIcon back = ImageLoader.load("/images/cards/utility/card_cover.png", 40, 80);
        final CardComponent card = new CardComponentImpl(drawnCard, def, front, back);
        card.getComponent().addActionListener(e -> this.toggleCardSelection(card));
        if (!this.isHumanPlayer(playerId)) {
            card.getComponent().setEnabled(false);
        } else {
            card.setFaceUp(true);
        }
        this.getPlayerArea(playerId).addHandCard(card);
    }

    private void toggleCardSelection(final CardComponent card) {
        if (this.selectedCards.contains(card.getCardId())) {
            this.selectedCards.remove(card.getCardId());
            card.getComponent().setBorder(null);
        } else {
            this.selectedCards.add(card.getCardId());
            card.getComponent().setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public void onCardPlaced(final PlayerId playerId, final CardId placedCard) {
        this.getPlayerArea(playerId).placeCard(placedCard);
    }

    @Override
    public void onCardHealthChanged(final PlayerId playerId, final CardId cardId, final int newHealth) {
        this.getPlayerArea(playerId).getArmyCard(cardId).setHealth(newHealth);
    }

    @Override
    public void onCardDestroyed(final PlayerId playerId, final CardId cardId) {
        this.getPlayerArea(playerId).removeArmyCard(cardId);
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
    public void onCardExhausted(final PlayerId playerId, final CardId exhaustedCard) {
        this.getPlayerArea(playerId).getArmyCard(exhaustedCard).getComponent().setEnabled(false);
    }

}
