package it.unibo.oop.hearthcode.model.boardgame.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import it.unibo.oop.hearthcode.model.army.api.Army;
import it.unibo.oop.hearthcode.model.army.impl.ArmyImpl;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CardType;
import it.unibo.oop.hearthcode.model.creature.api.Creature;
import it.unibo.oop.hearthcode.model.creature.impl.CreatureImplFactory;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabaseFactory;
import it.unibo.oop.hearthcode.model.deck.impl.DeckFactory;
import it.unibo.oop.hearthcode.model.player.api.Player;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.api.PlayerType;
import it.unibo.oop.hearthcode.model.player.impl.PlayerFactory;

/**
 * An implementation of {@link BoardGame} interface.
 */
public final class BoardGameImpl implements BoardGame {

    private static final String DEFAULT_CREATURES_FILE = "creatures.txt";
    private static final int STARTING_HAND_CARDS = 5;
    private static final int DECK_SIZE = 20;
    private static final int DEFAULT_HEALTH = 30;
    private final Map<Player, Army> armies;
    private final Map<PlayerId, Player> players;
    private PlayerId currentPlayer;

    /**
     * It constructs a new BoardGameImpl.
     */
    public BoardGameImpl() {
        this.armies = new HashMap<>();
        this.players = new HashMap<>();
        initGame();
    }

    private void initGame() {
        final var generator = new IdGenerator();
        final var database = CreatureDatabaseFactory.createFromFile(DEFAULT_CREATURES_FILE);
        final var creatureFactory = new CreatureImplFactory(generator);
        final var deckFactory = new DeckFactory(database, creatureFactory);

        final Player humanPlayer = PlayerFactory.createPlayer(
            deckFactory.createWeighted(DECK_SIZE, def -> Math.max(1, def.manaCost())),
            DEFAULT_HEALTH,
            new PlayerId(PlayerType.HUMAN_PLAYER)
        );

        this.currentPlayer = humanPlayer.getId();

        final Player iaPlayer = PlayerFactory.createPlayer(
            deckFactory.createWeighted(DECK_SIZE, def -> Math.max(1, def.manaCost())),
            DEFAULT_HEALTH,
            new PlayerId(PlayerType.IA_PLAYER)
        );

        this.players.put(humanPlayer.getId(), humanPlayer);
        this.players.put(iaPlayer.getId(), iaPlayer);
        this.armies.put(humanPlayer, new ArmyImpl());
        this.armies.put(iaPlayer, new ArmyImpl());
    }

    private PlayerId getDefendingPlayer() {
        return this.players.keySet().stream()
            .filter(i -> !i.equals(this.currentPlayer))
            .toList()
            .get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGame() {
        try {
            this.players.values().forEach(
                p -> IntStream.range(0, STARTING_HAND_CARDS)
                    .forEach(n -> p.drawCard())
            );
        } catch (final IllegalStateException e) {
            throw new IllegalStateException("Could not draw all the requested cards", e);
        }
        this.players.get(this.currentPlayer).incrementMana();
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
        final var currentArmy = this.armies.get(this.players.get(this.currentPlayer));
        final var defendingArmy = this.armies.get(this.players.get(getDefendingPlayer()));
        try {
            final var defendingCard = defendingArmy.getPlacedCard(defendingIdCard);
            if (defendingCard.isPresent() || currentArmy.canAttack(attackingIdCard)) {
                final var attackingCard = currentArmy.getPlacedCard(attackingIdCard).get();
                attackingCard.decreaseHealth(defendingCard.get().getAttackValue());
                defendingCard.get().decreaseHealth(attackingCard.getAttackValue());
                if (attackingCard.getHealth() == 0) {
                    currentArmy.deleteDeathCreature(attackingIdCard);
                }
                if (defendingCard.get().getHealth() == 0) {
                    defendingArmy.deleteDeathCreature(defendingIdCard);
                }
            }
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentException("Something went wrong with the fight", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attackHero(final CardId attackingIdCard) {
        try {
            final var currentArmy = this.armies.get(this.players.get(this.currentPlayer));
            if (currentArmy.canAttack(attackingIdCard)) {
                final var attackingCard = currentArmy.getPlacedCard(attackingIdCard);
                this.players.get(getDefendingPlayer()).decreaseHealth(attackingCard.get().getAttackValue());
                currentArmy.disableAttack(attackingIdCard);
            } else {
                throw new IllegalStateException("You cannot use this card right now");
            }
        } catch (final IllegalArgumentException e) {
            throw new IllegalStateException("This is not a valid card", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void place(final CardId selectedIdCard) {
        if (selectedIdCard.type() != CardType.CREATURE) {
            throw new IllegalArgumentException("You cannot place a non-creature card");
        }
        final var currentArmy = this.armies.get(this.players.get(currentPlayer));
        if (currentArmy.isArmyFull()) {
            throw new IllegalStateException("your army is full and cannot place the card");
        }
        try {
            final var removed = this.players.get(this.currentPlayer).playCard(selectedIdCard);
            this.armies.get(this.players.get(currentPlayer)).placeCard((Creature) removed);
        } catch (final IllegalArgumentException | IllegalStateException e) {
            throw new IllegalStateException("The card cannot be placed on the board", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchTurn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'switchTurn'");
    }
}
