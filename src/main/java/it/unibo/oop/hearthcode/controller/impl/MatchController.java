package it.unibo.oop.hearthcode.controller.impl;

import java.util.List;
import java.util.function.Consumer;

import it.unibo.oop.hearthcode.audio.api.AudioService;
import it.unibo.oop.hearthcode.audio.model.SoundEffect;
import it.unibo.oop.hearthcode.controller.api.SceneCoordinator;
import it.unibo.oop.hearthcode.model.ai.executor.api.AiActionExecutor;
import it.unibo.oop.hearthcode.model.ai.service.api.AiTurnService;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.view.api.MatchView;

/**
 * Controller of the match scene.
 */
public final class MatchController {

    private static final String MESSAGE = "Incorrect number of cards selected!";

    private final BoardGame boardGame;

    /**
     * Builds the controller and binds the scene actions.
     *
     * @param scene the controlled scene
     * @param boardGame the boardGame of the match
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
        this.boardGame = boardGame;
        this.boardGame.addObserver(scene);
        this.boardGame.startGame();

        scene.onAttackHero(() -> this.runSingleCardAction(
            scene,
            audioService,
            this.boardGame::attackHero,
            () -> this.evaluateEndMatch(coordinator)
        ));

        scene.onAttackCreature(() -> this.runTwoCardsAction(
            scene,
            audioService,
            selected -> this.boardGame.attackCard(selected.get(0), selected.get(1))
        ));

        scene.onPlaceCard(() -> this.runSingleCardAction(
            scene,
            audioService,
            this.boardGame::place
        ));

        scene.onEndTurn(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            this.boardGame.switchTurn();
            if (this.evaluateEndMatch(coordinator)) {
                return;
            }

            for (final var action : aiTurnService.decideTurn(this.boardGame)) {
                aiActionExecutor.execute(this.boardGame, action);
                if (this.evaluateEndMatch(coordinator)) {
                    return;
                }
            }

            this.boardGame.switchTurn();
            this.evaluateEndMatch(coordinator);
        });

        scene.onExitGame(() -> {
            audioService.playEffect(SoundEffect.BUTTON_CLICK);
            if (scene.confirmExitGame()) {
                coordinator.showMainMenu();
            }
        });
    }

    private boolean evaluateEndMatch(final SceneCoordinator coordinator) {
        final var winner = this.boardGame.getWinner();
        if (winner.isPresent()) {
            coordinator.showEndMatch(winner.get());
            return true;
        }
        return false;
    }

    private void runSingleCardAction(
        final MatchView scene,
        final AudioService audioService,
        final Consumer<CardId> action,
        final Runnable... trailingActions
    ) {
        this.runMatchAction(scene, audioService, 1, selected -> action.accept(selected.get(0)), trailingActions);
    }

    private void runTwoCardsAction(
        final MatchView scene,
        final AudioService audioService,
        final Consumer<List<CardId>> action,
        final Runnable... trailingActions
    ) {
        this.runMatchAction(scene, audioService, 2, action, trailingActions);
    }

    private void runMatchAction(
        final MatchView scene,
        final AudioService audioService,
        final int expectedSelectionSize,
        final Consumer<List<CardId>> action,
        final Runnable... trailingActions
    ) {
        audioService.playEffect(SoundEffect.BUTTON_CLICK);
        final List<CardId> selectedCards = scene.getSelectedCards();
        if (selectedCards.size() != expectedSelectionSize) {
            scene.showErrorPanel(MESSAGE);
            return;
        }
        try {
            action.accept(selectedCards);
            for (final Runnable trailingAction : trailingActions) {
                trailingAction.run();
            }
        } catch (final IllegalArgumentException | IllegalStateException e) {
            scene.showErrorPanel(e.getMessage());
        }
    }

}
