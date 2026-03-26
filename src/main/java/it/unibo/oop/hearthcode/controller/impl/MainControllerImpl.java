package it.unibo.oop.hearthcode.controller.impl;

import it.unibo.oop.hearthcode.audio.api.AudioService;
import it.unibo.oop.hearthcode.audio.impl.AudioServiceImpl;
import it.unibo.oop.hearthcode.audio.model.SoundTrack;
import it.unibo.oop.hearthcode.controller.api.MainController;
import it.unibo.oop.hearthcode.controller.api.SceneCoordinator;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;
import it.unibo.oop.hearthcode.model.boardgame.impl.BoardGameImpl;
import it.unibo.oop.hearthcode.view.api.MainView;
import it.unibo.oop.hearthcode.view.api.SceneId;
import it.unibo.oop.hearthcode.view.impl.EndMatchScene;
import it.unibo.oop.hearthcode.view.impl.MainViewImpl;
import it.unibo.oop.hearthcode.view.impl.MatchScene;
import it.unibo.oop.hearthcode.view.impl.MenuScene;
import it.unibo.oop.hearthcode.view.impl.SettingsScene;

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

    @Override
    public void start() {
        final MenuScene menuScene = new MenuScene();
        final SettingsScene settingsScene = new SettingsScene();
        final EndMatchScene endMatchScene = new EndMatchScene();

        new MenuController(menuScene, this, this.audioService);
        new SettingsController(settingsScene, this, this.audioService);
        new EndMatchController(endMatchScene, this, this.audioService);

        this.mainView.addScene(SceneId.MAIN_MENU, menuScene);
        this.mainView.addScene(SceneId.SETTINGS, settingsScene);
        this.mainView.addScene(SceneId.END_MATCH, endMatchScene);

        this.mainView.show();
        this.showMainMenu();
    }

    @Override
    public void showMainMenu() {
        this.mainView.showScene(SceneId.MAIN_MENU);
        this.audioService.playMusic(SoundTrack.MENU);
    }

    @Override
    public void showSettings() {
        this.mainView.showScene(SceneId.SETTINGS);
        this.audioService.playMusic(SoundTrack.MENU);
    }

    @Override
    public void showEndMatch() {
        this.mainView.showScene(SceneId.END_MATCH);
        this.audioService.playMusic(SoundTrack.MENU);
    }

    @Override
    public void startMatch() {
        final MatchScene matchScene = new MatchScene();
        final BoardGame boardGame = new BoardGameImpl();
        new MatchController(matchScene, boardGame, this, this.audioService);
        this.mainView.addScene(SceneId.MATCH, matchScene);
        this.mainView.showScene(SceneId.MATCH);
        this.audioService.playMusic(SoundTrack.MATCH);
    }

    @Override
    public void requestExit() {
        if (this.mainView.confirmExit()) {
            this.audioService.shutdown();
            this.mainView.close();
        }
    }
}
