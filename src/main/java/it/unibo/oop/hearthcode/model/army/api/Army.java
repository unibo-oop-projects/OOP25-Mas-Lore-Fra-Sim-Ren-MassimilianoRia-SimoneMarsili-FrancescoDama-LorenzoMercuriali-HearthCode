package it.unibo.oop.hearthcode.model.army.api;

import java.util.List;
import java.util.Optional;

import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.Creature;

/**
 * Represents the set of creatures placed by a player.
 */
public interface Army {

    /**
     * @return whether the army is full
     */
    boolean isArmyFull();

    /**
     * @return the maximum number of creatures that can be placed in the army
     */
    int getMaximumSize();

    /**
     * @param cardId the identifier of the specific card
     * @return an Optional containing the requested creature if present, empty otherwise
     */
    Optional<Creature> getPlacedCard(CardId cardId);

    /**
     * @return a copy of the cards contained
     */
    List<CardState> getCardsCopies();

    /**
     * Removes a specific card from the army.
     * 
     * @param cardId the id of the creature to be removed
     */
    void deleteDeathCreature(CardId cardId);

    /**
     * @param cardId the identifier of the specific card
     * @return true if the creature is awake and is able to attack, false otherwise
     */
    boolean canAttack(CardId cardId);

    /**
     * It disables the attack of a specified card.
     * 
     * @param cardId the id of the specified card
     */
    void disableAttack(CardId cardId);

    /**
     * Adds a specific card to the army.
     * 
     * @param creature the creature to be added to the army
     */
    void placeCard(Creature creature);

    /**
     * Wakes up all the creatures in the army.
     */
    void awakeCreatures();

}
