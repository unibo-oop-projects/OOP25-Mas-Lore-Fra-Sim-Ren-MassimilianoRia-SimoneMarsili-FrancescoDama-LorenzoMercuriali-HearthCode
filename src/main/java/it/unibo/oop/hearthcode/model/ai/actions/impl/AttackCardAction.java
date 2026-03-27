package it.unibo.oop.hearthcode.model.ai.actions.impl;

import it.unibo.oop.hearthcode.model.ai.actions.api.AiAction;
import it.unibo.oop.hearthcode.model.creature.api.CardId;

/**
 * AI action representing an attack from one card to another card.
 *
 * @param attackerId the attacking card identifier
 * @param defenderId the defending card identifier
 */
public record AttackCardAction(CardId attackerId, CardId defenderId) implements AiAction { }
