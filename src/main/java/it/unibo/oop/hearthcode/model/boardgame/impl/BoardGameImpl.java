package it.unibo.oop.hearthcode.model.boardgame.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.oop.hearthcode.model.army.api.Army;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
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
    private static final int DECK_SIZE = 20;
    private static final int DEFAULT_HEALTH = 30;
    private final Map<PlayerId, Army> gameState;
    private final List<Player> players;

    public BoardGameImpl() {
        this.gameState = new HashMap<>();
        this.players = new LinkedList<>();
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
        
        this.players.add(humanPlayer);
        this.players.add(iaPlayer);

        // manca inizializzazione turni ed army
    }

    @Override
    public void startGame() {
        try {
            for (int i = 0; i < STARTING_HAND_CARDS; i++) {
                this.players.get(0).drawCard();
                this.players.get(1).drawCard();
            }
        } catch (final IllegalStateException e) {
            throw new IllegalStateException("Could not draw all the requested cards", e);
        }

        // TO DO
    }

    @Override
    public boolean isOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOver'");
    }

    @Override
    public Optional<PlayerId> getWinner() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWinner'");
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
    public void place(Optional<CardId> selectedIdCard, Optional<PlayerId> placingPlayer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'place'");
    }

    @Override
    public void switchTurn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'switchTurn'");
    }
    
}
