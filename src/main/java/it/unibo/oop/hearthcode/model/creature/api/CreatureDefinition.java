package it.unibo.oop.hearthcode.model.creature.api;

/**
 * It represents the concept of Creature Definition.
 * It's basicly a static creature card used in the database, without an associated id.
 * 
 * @param name the name of the creature
 * @param health the amount of health of the creature
 * @param attackValue the amount of damage the creature can deal
 * @param manaCost the amount of Mana required to play the card
 */
public record CreatureDefinition(String name, Integer health, Integer attackValue, Integer manaCost) { }
