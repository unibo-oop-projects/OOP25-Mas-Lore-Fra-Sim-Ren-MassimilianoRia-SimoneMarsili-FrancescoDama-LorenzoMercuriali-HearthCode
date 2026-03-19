package it.unibo.oop.hearthcode.model.player.api;

import java.util.Optional;

import it.unibo.oop.hearthcode.model.creature.api.Card;

/**
 * A simple record that represents draw action result.
 * 
 * @param drawnCard the possible card drawn
 * @param result the type of the draw result
 */
public record DrawCardResult(Optional<Card> drawnCard, DrawCardResultType result) { }
