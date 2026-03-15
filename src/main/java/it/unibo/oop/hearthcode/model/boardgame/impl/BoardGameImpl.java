package it.unibo.oop.hearthcode.model.boardgame.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import it.unibo.oop.hearthcode.model.army.api.Army;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CardType;
import it.unibo.oop.hearthcode.model.creature.impl.CreatureImplFactory;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabaseFactory;
import it.unibo.oop.hearthcode.model.deck.impl.DeckFactory;
import it.unibo.oop.hearthcode.model.player.api.Player;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.api.PlayerType;
import it.unibo.oop.hearthcode.model.player.impl.PlayerFactory;

public class BoardGameImpl implements BoardGame{

    private static final String DEFAULT_CREATURES_FILE = "creatures.txt";
    private static final int STARTING_HAND_CARDS = 5;
    private static final int MAX_ARMY_SIZE = 5;
    private static final int DECK_SIZE = 20;
    private static final int DEFAULT_HEALTH = 30;
    private final Map<Player, Army> gameState;
    private final Map<PlayerId, Player> players;

    public BoardGameImpl() {
        this.gameState = new HashMap<>();
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

        final Player iaPlayer = PlayerFactory.createPlayer(
            deckFactory.createWeighted(DECK_SIZE, def -> Math.max(1, def.manaCost())),
            DEFAULT_HEALTH,
            new PlayerId(PlayerType.IA_PLAYER)
        );
        
        this.players.put(humanPlayer.getId(), humanPlayer);
        this.players.put(iaPlayer.getId(), iaPlayer);

        // manca inizializzazione turni ed army
    }

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

        // TO DO
    }

    @Override
    public boolean isOver() {
        return this.players.values().stream().anyMatch(p -> p.getHealth() <= 0);
    }

    @Override
    public Optional<PlayerId> getWinner() {
        if (!isOver()) {
            return Optional.empty();
        }
        return this.players.values().stream()
            .filter(p -> p.getHealth() > 0)
            .map(p -> p.getId())
            .findFirst();
    }

    @Override
    public void attackCard(Optional<CardId> attackingIdCard, Optional<CardId> defendingIdCard) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attackCard'");
    }

    @Override
    public void attackHero(Optional<CardId> attackingIdCard, Optional<PlayerId> defendingPlayer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attackHero'");
    }

    @Override
    public void place(CardId selectedIdCard, PlayerId placingPlayer) {

        // Check if placingPlayer is the actualPlayer

        if (!this.players.containsKey(placingPlayer)) {
            throw new IllegalArgumentException("Specified player is not in the game");
        }
        if (!selectedIdCard.type().equals(CardType.CREATURE)) {
            throw new IllegalArgumentException("You cannot place a non-creature card");
        }
        if (this.gameState.get(this.players.get(placingPlayer)).getSize() >= MAX_ARMY_SIZE) {
            throw new IllegalStateException("The creature cannot be placed in the army: already reached maximum capacity");
        }
        try {
            final var removed = this.players.get(placingPlayer).playCard(selectedIdCard);

            // bisogna inserire la carta nell'army

        } catch (final IllegalArgumentException | IllegalStateException e) {
            throw new IllegalStateException("The card cannot be placed on the board", e);
        }
        
    }

    @Override
    public void switchTurn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'switchTurn'");
    }
    
}
