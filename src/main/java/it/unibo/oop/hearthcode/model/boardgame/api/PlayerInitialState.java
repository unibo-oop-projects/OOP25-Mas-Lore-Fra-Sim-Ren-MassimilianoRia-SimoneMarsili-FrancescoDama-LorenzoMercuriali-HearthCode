package it.unibo.oop.hearthcode.model.boardgame.api;

/**
 * Initial values needed by observers to render a player's match state.
 *
 * @param health the starting health
 * @param handCardsLimit the maximum number of cards in hand
 * @param armyCardsLimit the maximum number of cards in army
 * @param deckCardsLimit the starting number of cards in deck
 */
public record PlayerInitialState(
    int health,
    int handCardsLimit,
    int armyCardsLimit,
    int deckCardsLimit
) {
}
