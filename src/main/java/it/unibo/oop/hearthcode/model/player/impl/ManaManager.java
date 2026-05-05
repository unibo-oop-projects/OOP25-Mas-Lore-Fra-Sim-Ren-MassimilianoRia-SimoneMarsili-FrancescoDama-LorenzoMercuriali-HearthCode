package it.unibo.oop.hearthcode.model.player.impl;

/**
 * Manages a player's current and maximum mana for each turn.
 */
public class ManaManager {

    private static final int START_MANA = 0;
    private static final int MAXIMUM_MANA = 10;
    private int maxMana;
    private int actualMana;

    /**
     * Initializes the actual and the max Mana at 0.
     */
    public ManaManager() {
        this.maxMana = START_MANA;
        this.actualMana = START_MANA;
    }

    /**
     * @return the player's current mana
     */
    public int actualMana() {
        return this.actualMana;
    }

    /**
     * @return the player's maximum mana for the current turn
     */
    public int maxMana() {
        return this.maxMana;
    }

    /**
     * Increments the mana limit and refreshes current mana.
     */
    public void updateMana() {
        if (this.maxMana < MAXIMUM_MANA) {
            this.maxMana++;
        }
        this.actualMana = this.maxMana; 
    }

    /**
     * Decreases the player's current mana.
     * 
     * @param amount the mana to be subtracted
     */
    public void decreaseActualMana(final int amount) {
        if (this.actualMana - amount < 0) {
            throw new IllegalStateException("You don't have enough Mana!");
        } else {
           this.actualMana -= amount; 
        }
    }
}
