package it.unibo.oop.hearthcode.controller.impl;

import java.util.Map;

import it.unibo.oop.hearthcode.audio.api.AudioService;
import it.unibo.oop.hearthcode.audio.impl.AudioServiceImpl;
import it.unibo.oop.hearthcode.audio.model.SoundTrack;
import it.unibo.oop.hearthcode.controller.api.MainController;
import it.unibo.oop.hearthcode.controller.api.SceneCoordinator;
import it.unibo.oop.hearthcode.model.ai.action.impl.AiActionGeneratorImpl;
import it.unibo.oop.hearthcode.model.ai.algorithm.api.AiDecisionAlgorithm;
import it.unibo.oop.hearthcode.model.ai.algorithm.impl.DepthLimitedLookaheadAiAlgorithm;
import it.unibo.oop.hearthcode.model.ai.algorithm.impl.GreedySequentialAiAlgorithm;
import it.unibo.oop.hearthcode.model.ai.evaluation.impl.HeuristicAiStateEvaluator;
import it.unibo.oop.hearthcode.model.ai.executor.impl.AiActionExecutorImpl;
import it.unibo.oop.hearthcode.model.ai.service.impl.AiTurnServiceImpl;
import it.unibo.oop.hearthcode.model.ai.simulation.impl.AiGameStateFactoryImpl;
import it.unibo.oop.hearthcode.model.ai.transition.impl.AiStateTransitionImpl;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;
import it.unibo.oop.hearthcode.model.boardgame.impl.BoardGameFactory;
import it.unibo.oop.hearthcode.model.boardgame.api.Difficulty;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabaseFactory;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.view.api.MainView;
import it.unibo.oop.hearthcode.view.api.SceneId;
import it.unibo.oop.hearthcode.view.impl.DatabaseScene;
import it.unibo.oop.hearthcode.view.impl.DifficultySelectionScene;
import it.unibo.oop.hearthcode.view.impl.EndMatchScene;
import it.unibo.oop.hearthcode.view.impl.MainViewImpl;
import it.unibo.oop.hearthcode.view.impl.MatchScene;
import it.unibo.oop.hearthcode.view.impl.MenuScene;
import it.unibo.oop.hearthcode.view.utility.ImageLoader;

/**
 * Default implementation of {@link MainController}.
 */
public final class MainControllerImpl implements MainController, SceneCoordinator {

    private static final int AI_LOOKAHEAD_DEPTH = 3;
    private static final String CREATURES_FILE = "creatures.txt";

    private final MainView mainView;
    private final AudioService audioService;
    private final Map<Difficulty, AiDecisionAlgorithm> aiAlgorithms = Map.of(
        Difficulty.NORMAL, new GreedySequentialAiAlgorithm(
            new AiActionGeneratorImpl(),
            new AiStateTransitionImpl(),
            new HeuristicAiStateEvaluator()
        ),
        Difficulty.HARD, new DepthLimitedLookaheadAiAlgorithm(
            new AiActionGeneratorImpl(),
            new AiStateTransitionImpl(),
            new HeuristicAiStateEvaluator(),
            AI_LOOKAHEAD_DEPTH
        )
    );

    /**
     * Builds the application controller.
     */
    public MainControllerImpl() {
        this.mainView = new MainViewImpl();
        this.audioService = new AudioServiceImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        ImageLoader.preloadMenuAssets();
        ImageLoader.preloadMatchAssets();
        ImageLoader.preloadDatabaseAssets();
        final MenuScene menuScene = new MenuScene();
        new MenuController(menuScene, this, this.audioService);
        this.mainView.addScene(SceneId.MAIN_MENU, menuScene);
        this.showMainMenu();
        this.mainView.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMainMenu() {
        this.mainView.showScene(SceneId.MAIN_MENU);
        this.audioService.playMusic(SoundTrack.MENU);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showEndMatch(final PlayerId playerId) {
        final EndMatchScene endMatchScene = new EndMatchScene(playerId);
        new EndMatchController(endMatchScene, this, this.audioService);
        this.mainView.addScene(SceneId.END_MATCH, endMatchScene);
        this.mainView.showScene(SceneId.END_MATCH);
        this.audioService.playMusic(SoundTrack.MENU);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startMatch(final Difficulty difficulty) {
        final MatchScene matchScene = new MatchScene();
        final BoardGame boardGame = BoardGameFactory.createDefaultGame();
        new MatchController(
            matchScene,
            boardGame,
            this,
            this.audioService,
            new AiTurnServiceImpl(
                new AiGameStateFactoryImpl(),
                this.aiAlgorithms.get(difficulty)
            ),
            new AiActionExecutorImpl()
        );
        this.mainView.addScene(SceneId.MATCH, matchScene);
        this.mainView.showScene(SceneId.MATCH);
        this.audioService.playMusic(SoundTrack.MATCH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showDifficultySelection() {
        final DifficultySelectionScene difficultySelectionScene = new DifficultySelectionScene();
        new DifficultySelectionController(difficultySelectionScene, this, this.audioService);
        this.mainView.addScene(SceneId.DIFFICULTY_SELECTION, difficultySelectionScene);
        this.mainView.showScene(SceneId.DIFFICULTY_SELECTION);
        this.audioService.playMusic(SoundTrack.MENU);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void requestExit() {
        if (this.mainView.confirmExit()) {
            this.audioService.shutdown();
            this.mainView.close();
        }
    }

    @Override
    public void showDatabase() {
        final var definitions = CreatureDatabaseFactory.createFromFile(CREATURES_FILE);
        final DatabaseScene database = new DatabaseScene(definitions);
        new DatabaseController(database, this, this.audioService);
        this.mainView.addScene(SceneId.DATABASE, database);
        this.mainView.showScene(SceneId.DATABASE);
        this.audioService.playMusic(SoundTrack.MENU);
    }

}
