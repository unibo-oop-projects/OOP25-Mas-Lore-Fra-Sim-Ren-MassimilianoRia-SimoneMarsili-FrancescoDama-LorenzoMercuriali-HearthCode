package it.unibo.oop.hearthcode.model.army.api;

import java.util.Optional;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.Creature;

/**
 * It modelizes the set of the placed creatures by his own player.
 */
public interface Army {

    /**
     * @param cardId the identifier of the specific card
     * @return an Optional containing the requested creature if present, empty otherwise
     */
    Optional<Creature> getPlacedCard(CardId cardId);

    /**
     * Remove a specific card from the army.
     * 
     * @param cardId the id of the creature to be removed
     */
    void deleteDeathCreature(CardId cardId);

    /**
     * @param cardId the identifier of the specific card
     * @return true if the creature is awake, false otherwise
     */
    boolean isCreatureAwake(CardId cardId);

    /**
     * Adds a specific card to the army.
     * 
     * @param id the id of the card to be added in the army.
     */
    void placeCard(CardId cardId);

    /**
     * It awakes all the cratures in the army.
     */
    void awakeCreatures();
}
