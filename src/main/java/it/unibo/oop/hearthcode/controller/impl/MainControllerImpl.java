package it.unibo.oop.hearthcode.controller.impl;

import it.unibo.oop.hearthcode.audio.api.AudioService;
import it.unibo.oop.hearthcode.audio.impl.AudioServiceImpl;
import it.unibo.oop.hearthcode.audio.model.SoundTrack;
import it.unibo.oop.hearthcode.controller.api.MainController;
import it.unibo.oop.hearthcode.controller.api.SceneCoordinator;
import it.unibo.oop.hearthcode.model.ai.action.impl.AiActionGeneratorImpl;
import it.unibo.oop.hearthcode.model.ai.algorithm.impl.AiStupidAlgorithm;
import it.unibo.oop.hearthcode.model.ai.executor.impl.AiActionExecutorImpl;
import it.unibo.oop.hearthcode.model.ai.service.impl.AiTurnServiceImpl;
import it.unibo.oop.hearthcode.model.ai.simulation.impl.AiGameStateFactoryImpl;
import it.unibo.oop.hearthcode.model.ai.transition.impl.AiStateTransitionImpl;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;
import it.unibo.oop.hearthcode.model.boardgame.impl.BoardGameImpl;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.view.api.MainView;
import it.unibo.oop.hearthcode.view.api.SceneId;
import it.unibo.oop.hearthcode.view.impl.DatabaseScene;
import it.unibo.oop.hearthcode.view.impl.EndMatchScene;
import it.unibo.oop.hearthcode.view.impl.MainViewImpl;
import it.unibo.oop.hearthcode.view.impl.MatchScene;
import it.unibo.oop.hearthcode.view.impl.MenuScene;
import it.unibo.oop.hearthcode.view.impl.SettingsScene;
import it.unibo.oop.hearthcode.view.utility.ImageLoader;

/**
 * Default implementation of {@link MainController}.
 */
public final class MainControllerImpl implements MainController, SceneCoordinator {

    private final MainView mainView;
    private final AudioService audioService;

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
        final SettingsScene settingsScene = new SettingsScene();

        new MenuController(menuScene, this, this.audioService);
        new SettingsController(settingsScene, this, this.audioService);

        this.mainView.addScene(SceneId.MAIN_MENU, menuScene);
        this.mainView.addScene(SceneId.SETTINGS, settingsScene);

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
    public void showSettings() {
        this.mainView.showScene(SceneId.SETTINGS);
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
    public void startMatch() {
        final MatchScene matchScene = new MatchScene();
        final BoardGame boardGame = new BoardGameImpl();
        new MatchController(
            matchScene,
            boardGame,
            this,
            this.audioService,
            new AiTurnServiceImpl(
                new AiGameStateFactoryImpl(),
                new AiStupidAlgorithm(new AiActionGeneratorImpl(), new AiStateTransitionImpl())
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
    public void requestExit() {
        if (this.mainView.confirmExit()) {
            this.audioService.shutdown();
            this.mainView.close();
        }
    }

    @Override
    public void showDatabase() {
        final DatabaseScene database = new DatabaseScene();
        new DatabaseController(database, this, this.audioService);
        this.mainView.addScene(SceneId.DATABASE, database);
        this.mainView.showScene(SceneId.DATABASE);
        this.audioService.playMusic(SoundTrack.MENU);
    }

}
