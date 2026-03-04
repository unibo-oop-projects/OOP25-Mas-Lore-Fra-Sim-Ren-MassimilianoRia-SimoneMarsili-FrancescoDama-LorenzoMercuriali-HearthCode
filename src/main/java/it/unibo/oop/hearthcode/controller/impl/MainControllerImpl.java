package it.unibo.oop.hearthcode.controller.impl;

import it.unibo.oop.hearthcode.controller.api.MainController;
import it.unibo.oop.hearthcode.view.api.MainView;
import it.unibo.oop.hearthcode.view.impl.MainViewImpl;

/**
 * Default implementation of {@link MainController}.
 */
public final class MainControllerImpl implements MainController {

    private final MainView view;

    /**
     * Builds the application controller.
     */
    public MainControllerImpl() {
        this.view = new MainViewImpl();
    }

    @Override
    public void start() {
        this.view.show();
    }
}
