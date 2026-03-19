package it.unibo.oop.hearthcode.model.player.api;

import java.util.Optional;

import it.unibo.oop.hearthcode.model.creature.api.Card;

public record DrawCardResult(Optional<Card> drawnCard, DrawCardResultType result) { }
