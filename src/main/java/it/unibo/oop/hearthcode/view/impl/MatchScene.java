package it.unibo.oop.hearthcode.view.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.model.boardgame.api.GameObserver;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.api.PlayerType;
import it.unibo.oop.hearthcode.view.api.MatchView;

/**
 * Implementation of {@link MatchView}.
 */
public final class MatchScene extends JPanel implements MatchView, GameObserver {

    private static final long serialVersionUID = 1L;

    private static final int SIDE_PANEL_WIDTH = 200;
    private static final PlayerId HUMAN_PLAYER = new PlayerId(PlayerType.HUMAN_PLAYER);
    private static final PlayerId IA_PLAYER = new PlayerId(PlayerType.IA_PLAYER);

    private final Map<PlayerId, PlayerAreaImpl> players = new HashMap<>();

    private final JButton attackButton;
    private final JButton endTurnButton;

    /**
     * Initializes the scene.
     */
    public MatchScene() {
        super(new BorderLayout());

        this.players.put(HUMAN_PLAYER, new PlayerAreaImpl(HUMAN_PLAYER));
        this.players.put(IA_PLAYER, new PlayerAreaImpl(IA_PLAYER));

        this.attackButton = new JButton("ATTACK");
        this.endTurnButton = new JButton("END TURN");

        this.add(this.players.get(IA_PLAYER), BorderLayout.NORTH);
        this.add(this.createCenterPanel(), BorderLayout.CENTER);
        this.add(this.players.get(HUMAN_PLAYER), BorderLayout.SOUTH);
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

    private JComponent createCenterPanel() {
        final JPanel panel = this.createSimplePanel();
        panel.setLayout(new BorderLayout());
        panel.add(this.createActionPanel(), BorderLayout.WEST);
        final JPanel armiesPanel = this.createSimplePanel();
        armiesPanel.setLayout(new BorderLayout());
        armiesPanel.add(this.players.get(IA_PLAYER).getArmyArea().getComponent(), BorderLayout.NORTH);
        armiesPanel.add(this.players.get(HUMAN_PLAYER).getArmyArea().getComponent(), BorderLayout.SOUTH);
        panel.add(armiesPanel, BorderLayout.CENTER);
        return panel;
    }

    private JComponent createActionPanel() {
        final JPanel actionPanel = this.createTitledPanel("Actions");
        actionPanel.setPreferredSize(new Dimension(SIDE_PANEL_WIDTH, 0));
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));

        this.attackButton.setAlignmentX(CENTER_ALIGNMENT);
        this.endTurnButton.setAlignmentX(CENTER_ALIGNMENT);

        actionPanel.add(this.attackButton);
        actionPanel.add(this.endTurnButton);

        return actionPanel;
    }

    private void updateHealth(final PlayerId playerId, final int newHealth) {
        this.players.get(playerId).setCurrentHealth(newHealth);
    }

    private void updateMana(final PlayerId playerId, final int currentMana, final int maxMana) {
        this.players.get(playerId).setMana(currentMana, maxMana);
    }

    private boolean isHumanPlayer(final PlayerId playerId) {
        return playerId.type() == PlayerType.HUMAN_PLAYER;
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
        playersHealth.forEach((playerId, health) -> {
            this.players.get(playerId).initHealth(health);
            this.players.get(playerId).setMana(0, 0);
        });
        this.revalidate();
        this.repaint();
    }

    @Override
    public void onTurnSwitch(final PlayerId nextPlayer) {
        final boolean isHumanTurn = this.isHumanPlayer(nextPlayer);
        this.attackButton.setEnabled(isHumanTurn);
        this.endTurnButton.setEnabled(isHumanTurn);
        Stream.concat(
            this.players.get(IA_PLAYER).getArmyArea().getCards().stream(),
            Stream.concat(
                this.players.get(HUMAN_PLAYER).getArmyArea().getCards().stream(),
                this.players.get(HUMAN_PLAYER).getHandArea().getCards().stream()
            )
        ).forEach(card -> ((CardComponentImpl) card).setEnabled(false));
    }

    @Override
    public void onCreatureDrawn(final PlayerId playerId, final CardId drawnCard, final CreatureDefinition def) {
        final CardComponentImpl card = new CardComponentImpl(drawnCard, def, null);
        if (!isHumanPlayer(playerId)) {
            card.setEnabled(false);
        }
        this.players.get(playerId).getHandArea().addCard(card);
    }

    @Override
    public void onCardPlaced(final PlayerId playerId, final CardId placedCard) {
        final CardComponentImpl card = (CardComponentImpl) this.players.get(playerId).getHandArea().getCard(placedCard);
        this.players.get(playerId).getHandArea().removeCard(placedCard);
        this.players.get(playerId).getArmyArea().addCard(card);
    }

    @Override
    public void onCardHealthChanged(final CardId cardId, final int newHealth) {
        //MANCA PLAYERID
        //final CardComponentImpl card = (CardComponentImpl) this.players.get(playerId).getHandArea().getCard(cardId);
        //card.setHealth(newHealth);
    }

    @Override
    public void onCardDestroyed(final CardId cardId) {
        //MANCA PLAYERID
        //this.players.get(playerId).getHandArea().removeCard(cardId);
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
        final CardComponentImpl card = (CardComponentImpl) this.players.get(playerId).getArmyArea().getCard(exhaustedCard);
        card.setEnabled(false);
    }

}
