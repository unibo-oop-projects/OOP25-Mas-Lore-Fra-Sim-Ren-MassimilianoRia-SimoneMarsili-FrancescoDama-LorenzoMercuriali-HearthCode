package it.unibo.oop.hearthcode.model.ai.service.impl;

import java.util.List;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.ai.algorithm.api.AiDecisionAlgorithm;
import it.unibo.oop.hearthcode.model.ai.service.api.AiTurnService;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;
import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameStateFactory;
import it.unibo.oop.hearthcode.model.boardgame.api.BoardGame;

/**
 * Implementation of {@link AiTurnService}.
 */
public class AiTurnServiceImpl implements AiTurnService {

    private final AiGameStateFactory gameStateFactory;
    private final AiDecisionAlgorithm decisionAlgorithm;

    /**
     * Creates the service with the components required to produce an AI action plan.
     *
     * @param gameStateFactory the factory used to generate AI snapshots
     * @param decisionAlgorithm the algorithm used to decide the AI actions
     */
    public AiTurnServiceImpl(
        final AiGameStateFactory gameStateFactory,
        final AiDecisionAlgorithm decisionAlgorithm
    ) {
        this.gameStateFactory = gameStateFactory;
        this.decisionAlgorithm = decisionAlgorithm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AiAction> decideTurn(final BoardGame game) {
        final AiGameState state = this.gameStateFactory.create(game);
        return this.decisionAlgorithm.decide(state);
    }

}
