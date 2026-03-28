package it.unibo.oop.hearthcode.model.ai.action.impl;

import it.unibo.oop.hearthcode.model.ai.action.api.AiAction;
import it.unibo.oop.hearthcode.model.creature.api.CardId;

/**
 * AI action representing an attack from a card to the enemy hero.
 *
 * @param attackerId the attacking card identifier
 */
public record AttackHeroAction(CardId attackerId) implements AiAction { }
