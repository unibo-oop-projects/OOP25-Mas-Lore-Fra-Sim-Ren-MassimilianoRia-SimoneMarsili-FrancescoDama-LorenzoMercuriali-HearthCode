package it.unibo.oop.hearthcode.model.ai.actions.impl;

import it.unibo.oop.hearthcode.model.ai.actions.api.AiAction;
import it.unibo.oop.hearthcode.model.creature.api.CardId;

/**
 * AI action representing the placement of a card from hand to army.
 *
 * @param cardId the identifier of the card to play
 */
public record PlayCardAction(CardId cardId) implements AiAction { }
