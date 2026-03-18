package it.unibo.oop.hearthcode.model.ai.simulation;

import java.util.List;

import it.unibo.oop.hearthcode.model.hand.api.Hand;

/**
 * Snapshot representation of the {@link Hand} state at a specific moment used in the AI decision simulation layer.
 * Implementations should be immutable.
 */
public interface HandState {

    /**
     * @return an immutable list of card states in the hand
     */
    List<CardState> getCards();

}
