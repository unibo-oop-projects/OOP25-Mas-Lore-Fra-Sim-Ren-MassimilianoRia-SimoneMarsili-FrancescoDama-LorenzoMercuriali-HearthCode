package it.unibo.oop.hearthcode.model.army.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import it.unibo.oop.hearthcode.model.army.api.Army;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.Creature;

/**
 * Implementation of {@link Army}.
 */
public class ArmyImpl implements Army {

    private static final int ARMY_MAX_SIZE = 5;
    private List<Creature> awakenCreatures;
    private List<Creature> sleepingCreatures;

    /**
     * Creates a new army.
     */
    public ArmyImpl() {
        this.awakenCreatures = new ArrayList<>();
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
        final List<Creature> allCreatures = Stream.concat(this.awakenCreatures.stream(), this.sleepingCreatures.stream())
            .toList();
        return allCreatures.stream()
            .filter(c -> c.getId().equals(cardId))
            .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDeathCreature(final CardId cardId) {
        this.awakenCreatures = this.awakenCreatures.stream()
            .filter(c -> !c.getId().equals(cardId))
            .toList();
        this.sleepingCreatures = this.sleepingCreatures.stream()
            .filter(c -> !c.getId().equals(cardId))
            .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCreatureAwake(final CardId cardId) {
        final Optional<Creature> creatureAwake = this.awakenCreatures.stream()
            .filter(c -> c.getId().equals(cardId))
            .findFirst();
        final Optional<Creature> creatureAsleep = this.sleepingCreatures.stream()
            .filter(c -> c.getId().equals(cardId))
            .findFirst();
        if (creatureAsleep.isEmpty() && creatureAwake.isEmpty()) {
            throw new IllegalArgumentException("this card isn't contained in your army");
        }
        return creatureAwake.isPresent();
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
        this.awakenCreatures.addAll(this.sleepingCreatures);
        this.sleepingCreatures.clear();
    }

}
