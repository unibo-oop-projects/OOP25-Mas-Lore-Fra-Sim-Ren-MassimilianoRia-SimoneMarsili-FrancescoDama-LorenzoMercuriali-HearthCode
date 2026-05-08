package it.unibo.oop.hearthcode.model.ai.simulation.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import it.unibo.oop.hearthcode.model.ai.simulation.api.AiGameState;
import it.unibo.oop.hearthcode.model.ai.simulation.api.PlayerState;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CardState;
import it.unibo.oop.hearthcode.model.creature.impl.CardStateImpl;
import it.unibo.oop.hearthcode.model.player.api.PlayerId;

/**
 * Implementation of {@link AiGameState}.
 */
public class AiGameStateImpl implements AiGameState {

    private final Map<PlayerId, PlayerStateImpl> players = new HashMap<>();

    /**
     * Creates an AI game state containing the two player states.
     *
     * @param humanPlayerState the state of the human player
     * @param aiPlayerState the state of the AI player
     */
    public AiGameStateImpl(final PlayerState humanPlayerState, final PlayerState aiPlayerState) {
        this.players.put(humanPlayerState.getPlayerId(), toImpl(humanPlayerState));
        this.players.put(aiPlayerState.getPlayerId(), toImpl(aiPlayerState));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerState getPlayerState(final PlayerId playerId) {
        return this.getRequiredPlayer(playerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AiGameState copy() {
        return new AiGameStateImpl(
            this.getRequiredPlayer(PlayerId.HUMAN),
            this.getRequiredPlayer(PlayerId.AI)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void damagePlayer(final PlayerId playerId, final int damage) {
        this.getRequiredPlayer(playerId).damagePlayer(damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void consumeMana(final PlayerId playerId, final int mana) {
        this.getRequiredPlayer(playerId).consumeMana(mana);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placeCard(final PlayerId playerId, final CardId cardId) {
        this.getRequiredPlayer(playerId).placeCard(cardId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void damageCard(final PlayerId playerId, final CardId cardId, final int damage) {
        final CardStateImpl card = (CardStateImpl) this.getRequiredPlayer(playerId)
            .getArmyCard(cardId)
            .orElseThrow(() -> new IllegalArgumentException("Card not found in army."));
        card.damage(damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exhaustCard(final PlayerId playerId, final CardId cardId) {
        final CardStateImpl card = (CardStateImpl) this.getRequiredPlayer(playerId)
            .getArmyCard(cardId)
            .orElseThrow(() -> new IllegalArgumentException("Card not found in army."));
        card.exhaust();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyCard(final PlayerId playerId, final CardId cardId) {
        this.getRequiredPlayer(playerId).destroyArmyCard(cardId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CardState> getArmyCard(final PlayerId playerId, final CardId cardId) {
        return this.getRequiredPlayer(playerId)
            .getArmyCard(cardId)
            .map(CardState.class::cast);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CardState> getHandCard(final PlayerId playerId, final CardId cardId) {
        return this.getRequiredPlayer(playerId)
            .getHandCard(cardId)
            .map(CardState.class::cast);
    }

    private PlayerStateImpl getRequiredPlayer(final PlayerId playerId) {
        final PlayerStateImpl player = this.players.get(playerId);
        if (player == null) {
            throw new IllegalArgumentException("Unknown player: " + playerId);
        }
        return player;
    }

    private PlayerStateImpl toImpl(final PlayerState state) {
        if (state instanceof PlayerStateImpl impl) {
            return new PlayerStateImpl(impl);
        }
        return new PlayerStateImpl(
            state.getPlayerId(),
            state.getPlayerHealth(),
            state.getPlayerActualMana(),
            state.getPlayerHand(),
            state.getPlayerArmy()
        );
    }

}
