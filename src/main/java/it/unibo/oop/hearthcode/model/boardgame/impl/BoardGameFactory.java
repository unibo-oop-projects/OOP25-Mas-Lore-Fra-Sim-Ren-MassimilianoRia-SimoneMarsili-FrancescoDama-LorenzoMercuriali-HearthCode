package it.unibo.oop.hearthcode.model.boardgame.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.oop.hearthcode.model.army.api.Army;
import it.unibo.oop.hearthcode.model.army.impl.ArmyImpl;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;
import it.unibo.oop.hearthcode.model.creature.impl.CreatureImplFactory;
import it.unibo.oop.hearthcode.model.creature.impl.IdGenerator;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabaseFactory;
import it.unibo.oop.hearthcode.model.deck.impl.DeckFactory;
import it.unibo.oop.hearthcode.model.player.api.Player;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.impl.PlayerFactory;

/**
 * Factory for creating default board game instances and match setups.
 */
public final class BoardGameFactory {

    private static final String DEFAULT_CREATURES_FILE = "creatures.txt";
    private static final int DECK_SIZE = 20;
    private static final int DEFAULT_HEALTH = 30;

    private BoardGameFactory() {
    }

    /**
     * Creates a board game configured with the default match setup.
     *
     * @return a new board game instance with default configuration
     */
    public static BoardGame createDefaultGame() {
        return new BoardGameImpl(createDefaultSetup());
    }

    /**
     * Creates the default setup used to initialize a new match.
     *
     * @return the default immutable match setup
     */
    public static GameSetup createDefaultSetup() {
        final var generator = new IdGenerator();
        final var database = CreatureDatabaseFactory.createFromFile(DEFAULT_CREATURES_FILE);
        final var creatureFactory = new CreatureImplFactory(generator);
        final var deckFactory = new DeckFactory(database, creatureFactory);

        final Player humanPlayer = PlayerFactory.createPlayer(
            deckFactory.createWeighted(DECK_SIZE, def -> Math.max(1, def.manaCost())),
            DEFAULT_HEALTH,
            PlayerId.HUMAN
        );
        final Player aiPlayer = PlayerFactory.createPlayer(
            deckFactory.createWeighted(DECK_SIZE, def -> Math.max(1, def.manaCost())),
            DEFAULT_HEALTH,
            PlayerId.AI
        );

        final Map<PlayerId, Player> players = new HashMap<>();
        players.put(humanPlayer.getId(), humanPlayer);
        players.put(aiPlayer.getId(), aiPlayer);

        final Map<Player, Army> armies = new HashMap<>();
        armies.put(humanPlayer, new ArmyImpl());
        armies.put(aiPlayer, new ArmyImpl());

        return new GameSetup(players, armies, humanPlayer.getId());
    }
}
