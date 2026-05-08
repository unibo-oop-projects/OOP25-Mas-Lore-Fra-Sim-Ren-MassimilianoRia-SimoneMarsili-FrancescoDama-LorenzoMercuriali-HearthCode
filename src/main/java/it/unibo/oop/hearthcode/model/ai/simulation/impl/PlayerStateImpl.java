package it.unibo.oop.hearthcode.model.ai.simulation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.oop.hearthcode.model.ai.simulation.api.PlayerState;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.creature.impl.CardStateImpl;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Implementation of {@link PlayerState}.
 */
public class PlayerStateImpl implements PlayerState {

    private static final int ARMY_MAX_SIZE = 5;

    private final PlayerId playerId;
    private int playerHealth;
    private int playerActualMana;
    private final Optional<List<CardStateImpl>> playerHand;
    private final List<CardStateImpl> playerArmy;

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
        this.playerHand = playerHand.map(hand -> hand.stream()
            .map(CardStateImpl::new)
            .toList())
            .map(ArrayList::new);
        this.playerArmy = new ArrayList<>(playerArmy.stream()
            .map(CardStateImpl::new)
            .toList());
    }

    /**
     * Copy constructor.
     *
     * @param other the source player state
     */
    public PlayerStateImpl(final PlayerStateImpl other) {
        this.playerId = other.playerId;
        this.playerHealth = other.playerHealth;
        this.playerActualMana = other.playerActualMana;
        this.playerHand = other.playerHand
            .map(hand -> hand.stream()
                .map(CardStateImpl::new)
                .toList())
            .map(ArrayList::new);
        this.playerArmy = new ArrayList<>(other.playerArmy.stream()
            .map(CardStateImpl::new)
            .toList());
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
        return this.playerHand.map(hand -> hand.stream()
            .map(CardState.class::cast)
            .toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CardState> getPlayerArmy() {
        return List.copyOf(this.playerArmy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CardState> getHandCard(final CardId cardId) {
        return this.playerHand.flatMap(hand -> hand.stream()
            .filter(card -> card.getCardId().equals(cardId))
            .findFirst());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CardState> getArmyCard(final CardId cardId) {
        return this.playerArmy.stream()
            .filter(card -> card.getCardId().equals(cardId))
            .map(CardState.class::cast)
            .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void damagePlayer(final int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative.");
        }
        this.playerHealth = Math.max(0, this.playerHealth - damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void consumeMana(final int mana) {
        if (mana < 0) {
            throw new IllegalArgumentException("Mana cannot be negative.");
        }
        if (mana > this.playerActualMana) {
            throw new IllegalStateException("Not enough mana.");
        }
        this.playerActualMana -= mana;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placeCard(final CardId cardId) {
        if (this.playerArmy.size() >= ARMY_MAX_SIZE) {
            throw new IllegalStateException("Army is full.");
        }

        final List<CardStateImpl> hand = this.playerHand.orElseThrow(
            () -> new IllegalStateException("This player has no visible hand.")
        );

        final CardStateImpl card = hand.stream()
            .filter(c -> c.getCardId().equals(cardId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Card not found in hand."));

        hand.remove(card);
        card.exhaust();
        this.playerArmy.add(card);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyArmyCard(final CardId cardId) {
        final boolean removed = this.playerArmy.removeIf(card -> card.getCardId().equals(cardId));
        if (!removed) {
            throw new IllegalArgumentException("Card not found in army.");
        }
    }

}
