package it.unibo.oop.hearthcode.model.ai.impl;

import java.util.List;
import java.util.Optional;

import it.unibo.oop.hearthcode.model.ai.api.CardState;
import it.unibo.oop.hearthcode.model.ai.api.PlayerState;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Implementation of {@link PlayerState}.
 */
public class PlayerStateImpl implements PlayerState {

    private final PlayerId playerId;
    private final int playerHealth;
    private final int playerActualMana;
    private final Optional<List<CardState>> playerHand;
    private final List<CardState> playerArmy;

    /**
     * Creates a player state.
     *
     * @param playerId the player identifier
     * @param playerHealth the current health of the player
     * @param playerActualMana the current available mana of the player
     * @param playerHand the optional hand of the player
     * @param playerArmy the current army of the player
     */
    public PlayerStateImpl(
        final PlayerId playerId,
        final int playerHealth,
        final int playerActualMana,
        final Optional<List<CardState>> playerHand,
        final List<CardState> playerArmy
    ) {
        this.playerId = playerId;
        this.playerHealth = playerHealth;
        this.playerActualMana = playerActualMana;
        this.playerHand = playerHand.map(List::copyOf);
        this.playerArmy = List.copyOf(playerArmy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerId getPlayerId() {
        return this.playerId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerHealth() {
        return this.playerHealth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerActualMana() {
        return this.playerActualMana;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<CardState>> getPlayerHand() {
        return this.playerHand.map(List::copyOf);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CardState> getPlayerArmy() {
        return List.copyOf(this.playerArmy);
    }

}
