package it.unibo.oop.hearthcode.model.army.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import it.unibo.oop.hearthcode.model.ai.api.CardState;
import it.unibo.oop.hearthcode.model.ai.impl.CardStateImpl;
import it.unibo.oop.hearthcode.model.army.api.Army;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.Creature;
import it.unibo.oop.hearthcode.model.creature.impl.CreatureImpl;

/**
 * Implementation of {@link Army}.
 */
public class ArmyImpl implements Army {

    private static final int ARMY_MAX_SIZE = 5;
    private final Map<Creature, Boolean> awakenCreatures;
    private final List<Creature> sleepingCreatures;

    /**
     * Creates a new army.
     */
    public ArmyImpl() {
        this.awakenCreatures = new LinkedHashMap<>();
        this.sleepingCreatures = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isArmyFull() {
        return (this.awakenCreatures.size() + this.sleepingCreatures.size()) >= ARMY_MAX_SIZE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Creature> getPlacedCard(final CardId cardId) {
        final List<Creature> allCreatures = Stream.concat(this.awakenCreatures.keySet().stream(), this.sleepingCreatures.stream())
            .toList();
        return allCreatures.stream()
            .filter(c -> c.getId().equals(cardId))
            .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CardState> getCardsCopies() {
        return Stream.concat(
            this.awakenCreatures.entrySet().stream()
                .map(entry -> (CardState) new CardStateImpl(
                    entry.getKey().getId(),
                    entry.getKey().getManaCost(),
                    ((CreatureImpl) entry.getKey()).getHealth(),
                    ((CreatureImpl) entry.getKey()).getAttackValue(),
                    true
                )),
            this.sleepingCreatures.stream()
                .map(card -> (CardState) new CardStateImpl(
                    card.getId(),
                    card.getManaCost(),
                    ((CreatureImpl) card).getHealth(),
                    ((CreatureImpl) card).getAttackValue(),
                    false
                ))
        ).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDeathCreature(final CardId cardId) {
        final Optional<Creature> creature = this.getPlacedCard(cardId);
        if (creature.isEmpty()) {
            throw new IllegalArgumentException("this card isn't contained in your army");
        }
        if (this.sleepingCreatures.contains(creature.get())) {
            this.sleepingCreatures.remove(creature.get());
        } else {
            this.awakenCreatures.remove(creature.get());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canAttack(final CardId cardId) {
        final var creature = this.getPlacedCard(cardId);
        if (creature.isEmpty()) {
            throw new IllegalArgumentException("this card is not contained in your army");
        }
        return !this.sleepingCreatures.contains(creature.get())
            && this.awakenCreatures.get(creature.get()); 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placeCard(final Creature creature) {
        if (!this.isArmyFull()) {
            this.sleepingCreatures.add(creature);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void awakeCreatures() {
        this.sleepingCreatures.forEach(c -> this.awakenCreatures.put(c, false));
        this.awakenCreatures.replaceAll((k, v) -> true);
        this.sleepingCreatures.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableAttack(final CardId cardId) {
        final var creature = this.getPlacedCard(cardId);
        if (creature.isEmpty()) {
            throw new IllegalArgumentException("this card is not contained in your army");
        }
        this.awakenCreatures.put(creature.get(), false);
    }

}
