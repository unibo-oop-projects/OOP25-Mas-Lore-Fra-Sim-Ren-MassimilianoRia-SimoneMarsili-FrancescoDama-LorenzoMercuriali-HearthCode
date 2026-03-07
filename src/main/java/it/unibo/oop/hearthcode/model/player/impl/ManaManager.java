package it.unibo.oop.hearthcode.model.player.impl;

/**
 * Manager of player's actual and Max mana by turn.
 */
public class ManaManager {

    private static final int START_MANA = 3;
    private int maxMana;
    private int actualMana;

    /**
     * Inizializes the actual and the max Mana at 3.
     */
    public ManaManager() {
        this.maxMana = START_MANA;
        this.actualMana = START_MANA;
    }

    /**
     * @return Player's actual Mana.
     */
    int actualMana() {
        return this.actualMana;
    }

    /**
     * @return Player's maxMana
     */
    int maxMana() {
        return this.maxMana;
    }

    /**
     * this method increments Mana Limit and set actual Mana to maxMana.
     */
    void updateMana() {
        this.maxMana++;
        this.actualMana = this.maxMana; 
    }
}
