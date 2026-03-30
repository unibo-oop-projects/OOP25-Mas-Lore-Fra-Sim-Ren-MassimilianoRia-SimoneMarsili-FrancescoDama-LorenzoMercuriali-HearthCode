package it.unibo.oop.hearthcode.model.creature.api;

import java.io.Serializable;

/**
 * Identifies all the cards.
 * 
 * @param type the {@link CardType} of the card
 * @param id the identifier of the specific card
 */
public record CardId(CardType type, Integer id) implements Serializable {

    private static final long serialVersionUID = 1L;
}
