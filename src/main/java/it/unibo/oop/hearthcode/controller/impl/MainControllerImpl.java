package it.unibo.oop.hearthcode.controller.impl;

import it.unibo.oop.hearthcode.controller.api.MainController;
import it.unibo.oop.hearthcode.view.api.MainView;
import it.unibo.oop.hearthcode.view.api.SceneId;
import it.unibo.oop.hearthcode.view.impl.MainViewImpl;
import it.unibo.oop.hearthcode.view.impl.MatchScene;
import it.unibo.oop.hearthcode.view.impl.MenuScene;
import it.unibo.oop.hearthcode.view.impl.SettingsScene;

/**
 * Default implementation of {@link MainController}.
 */
public final class MainControllerImpl implements MainController {

    private final MainView mainView;

    /**
     * Builds the application controller.
     */
    public MainControllerImpl() {
        this.mainView = new MainViewImpl();
    }

    @Override
    public void start() {
        final MenuScene mainScene = new MenuScene();
        final SettingsScene settingsScene = new SettingsScene();
        final MatchScene matchScene = new MatchScene();

        new MenuController(mainScene, this.mainView);
        new SettingsController(settingsScene, this.mainView);
        new MatchController(matchScene, this.mainView);

        this.mainView.addScene(SceneId.MAIN_MENU, mainScene);
        this.mainView.addScene(SceneId.SETTINGS, settingsScene);
        this.mainView.addScene(SceneId.MATCH, matchScene);

        this.mainView.show();
        this.mainView.showScene(SceneId.MAIN_MENU);
    }
}
