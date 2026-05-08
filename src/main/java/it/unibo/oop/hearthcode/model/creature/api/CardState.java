package it.unibo.oop.hearthcode.model.creature.api;

/**
 * Representation of a card state.
 */
public interface CardState {

    /**
     * @return the card identifier
     */
    CardId getCardId();

    /**
     * @return the mana cost
     */
    int getManaCost();

    /**
     * @return the health value
     */
    int getHealth();

    /**
     * @return the attack value
     */
    int getAttackValue();

    /**
     * @return whether the card is usable
     */
    boolean isUsable();

    /**
     * Decreases the health of the card.
     *
     * @param damage the amount of damage
     */
    void damage(int damage);

    /**
     * Marks the card as exhausted.
     */
    void exhaust();

    /**
     * Marks the card as usable.
     */
    void awaken();

    /**
     * @return whether the card is dead
     */
    boolean isDead();

}
