package it.unibo.oop.hearthcode.controller.impl;

import java.util.List;
import java.util.function.Consumer;

import it.unibo.oop.hearthcode.audio.api.AudioService;
import it.unibo.oop.hearthcode.audio.model.SoundEffect;
import it.unibo.oop.hearthcode.controller.api.SceneCoordinator;
import it.unibo.oop.hearthcode.model.ai.executor.api.AiActionExecutor;
import it.unibo.oop.hearthcode.model.ai.service.api.AiTurnService;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;
import it.unibo.oop.hearthcode.model.boardgame.api.GameObserver;
import it.unibo.oop.hearthcode.model.boardgame.api.ObservableGame;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.view.api.MatchView;

/**
 * Controller of the match scene.
 */
public final class MatchController {

    private static final String INVALID_SELECTION_MESSAGE = "Incorrect number of cards selected!";

    private final BoardGame boardGame;
    private final SceneCoordinator coordinator;
    private final AudioService audioService;
    private final AiTurnService aiTurnService;
    private final AiActionExecutor aiActionExecutor;
    private final MatchView scene;

    /**
     * Builds the controller and binds the scene actions.
     *
     * @param scene the controlled scene
     * @param boardGame the board game of the match
     * @param coordinator the application scene coordinator
     * @param audioService the audio service
     * @param aiTurnService the turn service of the AI
     * @param aiActionExecutor the executor of the AI actions in the real match
     */
    public MatchController(
        final MatchView scene,
        final BoardGame boardGame,
        final SceneCoordinator coordinator,
        final AudioService audioService,
        final AiTurnService aiTurnService,
        final AiActionExecutor aiActionExecutor
    ) {
        this.scene = scene;
        this.boardGame = boardGame;
        this.coordinator = coordinator;
        this.audioService = audioService;
        this.aiTurnService = aiTurnService;
        this.aiActionExecutor = aiActionExecutor;
        this.bindObserver();
        this.bindActions();
        this.boardGame.startGame();
    }

    private void bindObserver() {
        if (this.boardGame instanceof ObservableGame observable && this.scene instanceof GameObserver observer) {
            observable.addObserver(observer);
        }
    }

    private void bindActions() {
        this.scene.onAttackHero(() -> this.runSingleCardAction(
            this.boardGame::attackHero,
            this::showEndMatchIfPresent
        ));
        this.scene.onAttackCreature(() -> this.runTwoCardsAction(
            selected -> this.boardGame.attackCard(selected.get(0), selected.get(1))
        ));
        this.scene.onPlaceCard(() -> this.runSingleCardAction(this.boardGame::place));
        this.scene.onEndTurn(this::handleEndTurn);
        this.scene.onExitGame(() -> {
            this.audioService.playEffect(SoundEffect.BUTTON_CLICK);
            if (this.scene.confirmExitGame()) {
                this.coordinator.showMainMenu();
            }
        });
    }

    private void handleEndTurn() {
        this.audioService.playEffect(SoundEffect.BUTTON_CLICK);
        this.boardGame.switchTurn();
        for (final var action : this.aiTurnService.decideTurn(this.boardGame)) {
            this.aiActionExecutor.execute(this.boardGame, action);
            if (this.showEndMatchIfPresent()) {
                return;
            }
        }
        this.boardGame.switchTurn();
    }

    private boolean showEndMatchIfPresent() {
        final var winner = this.boardGame.getWinner();
        if (winner.isPresent()) {
            this.coordinator.showEndMatch(winner.get());
            return true;
        }
        return false;
    }

    private void runSingleCardAction(
        final Consumer<CardId> action,
        final Runnable... trailingActions
    ) {
        this.runMatchAction(1, selected -> action.accept(selected.get(0)), trailingActions);
    }

    private void runTwoCardsAction(
        final Consumer<List<CardId>> action,
        final Runnable... trailingActions
    ) {
        this.runMatchAction(2, action, trailingActions);
    }

    private void runMatchAction(
        final int expectedSelectionSize,
        final Consumer<List<CardId>> action,
        final Runnable... trailingActions
    ) {
        this.audioService.playEffect(SoundEffect.BUTTON_CLICK);
        final List<CardId> selectedCards = this.scene.getSelectedCards();

        if (selectedCards.size() != expectedSelectionSize) {
            this.scene.showErrorPanel(INVALID_SELECTION_MESSAGE);
            return;
        }

        try {
            action.accept(selectedCards);
            for (final Runnable trailingAction : trailingActions) {
                trailingAction.run();
            }
        } catch (final IllegalArgumentException | IllegalStateException e) {
            this.scene.showErrorPanel(e.getMessage());
        }
    }

}
