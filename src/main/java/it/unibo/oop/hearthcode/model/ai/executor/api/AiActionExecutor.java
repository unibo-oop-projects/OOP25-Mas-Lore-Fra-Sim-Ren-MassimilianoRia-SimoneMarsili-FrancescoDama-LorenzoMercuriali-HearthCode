package it.unibo.oop.hearthcode.model.ai.executor.api;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;

/**
 * Executes a given {@link AiAction} on the real {@link BoardGame} model.
 */
@FunctionalInterface
public interface AiActionExecutor {

    /**
     * Executes the given AI action on the provided game instance.
     *
     * @param game the game model on which the action must be executed
     * @param action the AI action to execute
     */
    void execute(BoardGame game, AiAction action);

}
