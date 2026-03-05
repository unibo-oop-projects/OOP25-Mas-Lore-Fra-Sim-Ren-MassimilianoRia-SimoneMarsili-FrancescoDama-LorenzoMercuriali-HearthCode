package it.unibo.oop.hearthcode.model.creature.api;

/**
 * Identifies all the cards.
 * 
 * @param type the {@link CardType} of the card
 * @param id the identifier of the specific card
 */
public record CardId(CardType type, Integer id) { }
