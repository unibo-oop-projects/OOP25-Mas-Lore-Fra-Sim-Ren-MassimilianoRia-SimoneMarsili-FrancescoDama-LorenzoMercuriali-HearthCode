package it.unibo.oop.hearthcode.model.creature.api;

/**
 * Represents a creature definition.
 * It is basically a static creature card used in the database, without an associated id.
 * 
 * @param name the name of the creature
 * @param health the creature's health
 * @param attackValue the amount of damage the creature can deal
 * @param manaCost the amount of mana required to play the card
 */
public record CreatureDefinition(String name, Integer health, Integer attackValue, Integer manaCost) { }
