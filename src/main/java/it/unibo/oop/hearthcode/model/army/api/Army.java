package it.unibo.oop.hearthcode.model.army.api;

import java.util.Optional;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.Creature;

/**
 * It modelizes the set of the placed creatures by his own player.
 */
public interface Army {

    /**
     * @return the actual number of creatures placed in this army
     */
    int getSize();

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
     * @param creature the creature to be added in the army.
     */
    void placeCard(Creature creature);

    /**
     * It awakes all the creatures in the army.
     */
    void awakeCreatures();
}
