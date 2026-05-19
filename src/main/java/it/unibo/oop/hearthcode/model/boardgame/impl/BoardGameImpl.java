package it.unibo.oop.hearthcode.model.boardgame.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.army.api.Army;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;
import it.unibo.oop.hearthcode.model.boardgame.api.GameObserver;
import it.unibo.oop.hearthcode.model.boardgame.api.PlayerInitialState;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CardType;
import it.unibo.oop.hearthcode.model.creature.api.Creature;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.player.api.DrawCardResult;
import it.unibo.oop.hearthcode.model.player.api.DrawCardResultType;
import it.unibo.oop.hearthcode.model.player.api.Player;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * An implementation of the {@link BoardGame} interface.
 */
public final class BoardGameImpl implements BoardGame {

    private static final int STARTING_HAND_CARDS = 5;
    private final Map<Player, Army> armies;
    private final Map<PlayerId, Player> players;
    private final TurnManager turnManager;
    private final List<GameObserver> observers;

    /**
     * Builds a board game using the provided match setup.
     * 
     * @param setup the match setup containing the initial players, armies, and starting player
     */
    protected BoardGameImpl(final GameSetup setup) {
        this.armies = setup.armies();
        this.players = setup.players();
        this.turnManager = new TurnManager(setup.startingPlayerId());
        this.observers = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(final GameObserver obs) {
        this.observers.add(obs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(final GameObserver obs) {
        if (this.observers.contains(obs)) {
            this.observers.remove(obs);
        }
    }

    private void notifyObservers(final Consumer<GameObserver> action) {
        this.observers.forEach(action);
    }

    private PlayerId getDefendingPlayer() {
        return this.players.keySet().stream()
            .filter(i -> !i.equals(this.turnManager.getCurrentPlayer()))
            .toList()
            .get(0);
    }

    private void notifyPlayerMana(final PlayerId playerId) {
        notifyObservers(o -> o.onManaChanged(
                playerId,
                players.get(playerId).getActualMana(),
                players.get(playerId).getTurnManaLimit()
            )
        );
    }

    private Map<PlayerId, PlayerInitialState> createInitialStateMap() {
        return this.players.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> this.createInitialState(entry.getValue())
            ));
    }

    private PlayerInitialState createInitialState(final Player player) {
        return new PlayerInitialState(
            player.getHealth(),
            player.getHandCardsLimit(),
            this.armies.get(player).getMaximumSize(),
            player.getDeckCardsCount()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGame() {
        try {
            final Map<PlayerId, PlayerInitialState> initialStateMap = this.createInitialStateMap();
            notifyObservers(o -> o.onGameStarted(this.turnManager.getCurrentPlayer(), initialStateMap));
            this.players.values().forEach(
                p -> IntStream.range(0, STARTING_HAND_CARDS)
                    .forEach(n -> {
                        final var r = (Creature) p.drawCard().drawnCard().get();
                        notifyObservers(o -> o.onCreatureDrawn(p.getId(), r.getId(),
                            new CreatureDefinition(r.getName(), r.getHealth(), r.getAttackValue(), r.getManaCost()))
                        );
                    }
                )
            );
        } catch (final IllegalStateException e) {
            throw new IllegalStateException("Could not draw all the requested cards.", e);
        }
        this.players.get(this.turnManager.getCurrentPlayer()).incrementMana();
        notifyPlayerMana(this.turnManager.getCurrentPlayer());
        notifyPlayerMana(getDefendingPlayer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PlayerId> getWinner() {
        final List<Player> alivePlayers = this.players.values().stream()
            .filter(p -> p.getHealth() > 0)
            .toList();
        return alivePlayers.size() == 2 ? Optional.empty() : Optional.of(alivePlayers.get(0).getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attackCard(final CardId attackingIdCard, final CardId defendingIdCard) {
        final var currentArmy = this.armies.get(this.players.get(this.turnManager.getCurrentPlayer()));
        final var defendingArmy = this.armies.get(this.players.get(getDefendingPlayer()));
        try {
            final var defendingCard = defendingArmy.getPlacedCard(defendingIdCard);
            if (defendingCard.isPresent() && currentArmy.canAttack(attackingIdCard)) {
                final var attackingCard = currentArmy.getPlacedCard(attackingIdCard).get();
                final var currPlayer = this.turnManager.getCurrentPlayer();
                final var defPlayer = getDefendingPlayer();
                attackingCard.decreaseHealth(defendingCard.get().getAttackValue());
                defendingCard.get().decreaseHealth(attackingCard.getAttackValue());

                notifyObservers(o -> o.onCardHealthChanged(currPlayer, attackingIdCard, attackingCard.getHealth()));
                notifyObservers(o -> o.onCardHealthChanged(defPlayer, defendingIdCard, defendingCard.get().getHealth()));

                currentArmy.disableAttack(attackingIdCard);
                notifyObservers(o -> o.onCardExhausted(currPlayer, attackingIdCard));

                if (attackingCard.getHealth() == 0) {
                    currentArmy.deleteDeathCreature(attackingIdCard);
                    notifyObservers(o -> o.onCardDestroyed(currPlayer, attackingIdCard));
                }
                if (defendingCard.get().getHealth() == 0) {
                    defendingArmy.deleteDeathCreature(defendingIdCard);
                    notifyObservers(o -> o.onCardDestroyed(defPlayer, defendingIdCard));
                }
            } else {
                throw new IllegalStateException("Your card cannot attack during this turn!");
            }
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentException("Something went wrong with the fight.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attackHero(final CardId attackingIdCard) {
        try {
            final var currentArmy = this.armies.get(this.players.get(this.turnManager.getCurrentPlayer()));
            if (currentArmy.canAttack(attackingIdCard)) {
                final var attackingCard = currentArmy.getPlacedCard(attackingIdCard);
                final var defPlayer = this.players.get(getDefendingPlayer());
                defPlayer.decreaseHealth(attackingCard.get().getAttackValue());
                notifyObservers(o -> o.onPlayerHealthChanged(defPlayer.getId(), defPlayer.getHealth()));
                currentArmy.disableAttack(attackingIdCard);
                notifyObservers(o -> o.onCardExhausted(this.turnManager.getCurrentPlayer(), attackingIdCard));
            } else {
                throw new IllegalStateException("You cannot use this card right now.");
            }
        } catch (final IllegalArgumentException e) {
            throw new IllegalStateException("This card is not contained in your army!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void place(final CardId selectedIdCard) {
        if (selectedIdCard.type() != CardType.CREATURE) {
            throw new IllegalArgumentException("You cannot place a non-creature card.");
        }
        final var currentArmy = this.armies.get(this.players.get(this.turnManager.getCurrentPlayer()));
        if (currentArmy.isArmyFull()) {
            throw new IllegalStateException("Your army is full, you cannot place the card!");
        }
        try {
            final var currPlayer = this.turnManager.getCurrentPlayer();
            final var removed = this.players.get(currPlayer).playCard(selectedIdCard);
            notifyPlayerMana(currPlayer);
            this.armies.get(this.players.get(currPlayer)).placeCard((Creature) removed);
            notifyObservers(o -> o.onCardPlaced(currPlayer, removed.getId()));
        } catch (final IllegalArgumentException | IllegalStateException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchTurn() {
        turnManager.switchAndAwake();
        turnManager.drawNewCard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerHealth(final PlayerId playerId) {
        return this.players.get(playerId).getHealth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerActualMana(final PlayerId playerId) {
        return this.players.get(playerId).getActualMana();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CardState> getHandCardsCopies(final PlayerId playerId) {
        return this.players.get(playerId).getHandCardsCopies();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CardState> getArmyCardsCopies(final PlayerId playerId) {
        return this.armies.get(this.players.get(playerId)).getCardsCopies();
    }

    /**
     * A inner class that manages the change turn.
     */
    private class TurnManager {

        private PlayerId currentPlayer;
        private int decreasingHealthTax = 1;
        private int passedTurns;

        TurnManager(final PlayerId id) {
            this.currentPlayer = id;
        }

        private PlayerId getCurrentPlayer() {
            return this.currentPlayer;
        }

        private void switchAndAwake() {
            this.currentPlayer = getDefendingPlayer();
            notifyObservers(o -> o.onTurnSwitch(currentPlayer));
            players.get(currentPlayer).incrementMana();
            notifyPlayerMana(currentPlayer);
            armies.get(players.get(currentPlayer)).awakeCreatures();
        }

        private void drawNewCard() {
            final DrawCardResult drawResult = players.get(currentPlayer).drawCard();
            if (drawResult.result() == DrawCardResultType.DECK_EMPTY) {
                players.get(currentPlayer).decreaseHealth(this.decreasingHealthTax);
                notifyObservers(o -> o.onPlayerHealthChanged(currentPlayer, players.get(currentPlayer).getHealth()));
                this.passedTurns++;
                if (this.passedTurns % 2 == 0) {
                    this.decreasingHealthTax *= 2;
                }
            } else if (drawResult.result() == DrawCardResultType.CARD_ADDED) {
                final var card = (Creature) drawResult.drawnCard().get();
                notifyObservers(o -> o.onCreatureDrawn(this.currentPlayer, card.getId(),
                    new CreatureDefinition(card.getName(), card.getHealth(), card.getAttackValue(), card.getManaCost()))
                );
            } else {
                notifyObservers(o -> o.onCardBurned(this.currentPlayer));
            }
        }
    }

}
