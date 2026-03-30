package it.unibo.oop.hearthcode.model.ai.service.api;

import java.util.List;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;

/**
 * Service responsible for producing the action plan for an AI turn.
 */
@FunctionalInterface
public interface AiTurnService {

    /**
     * Produces the sequence of actions the AI intends to perform for the current turn.
     *
     * @param game the current game model
     * @return the ordered list of actions chosen by the AI
     */
    List<AiAction> decideTurn(BoardGame game);

}
