package it.unibo.oop.hearthcode.model.ai.simulation;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.Card;

/**
 * Snapshot representation of the {@link Card} state at a specific moment used in the AI decision simulation layer.
 * Implementations should be immutable.
 */
public interface CardState {

    /**
     * @return the specific CardId of the instance of the card
     */
    CardId getId();

    /**
     * @return the name of the card
     */
    String getName();

    /**
     * @return the cost of the card in terms of mana
     */
    int getManaCost();

}
