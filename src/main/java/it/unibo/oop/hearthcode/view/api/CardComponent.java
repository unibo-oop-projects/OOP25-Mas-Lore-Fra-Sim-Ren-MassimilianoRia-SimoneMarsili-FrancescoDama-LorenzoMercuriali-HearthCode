package it.unibo.oop.hearthcode.view.api;

import javax.swing.JButton;

import it.unibo.oop.hearthcode.model.creature.api.CardId;

/**
 * UI contract for a card component shown in the view.
 */
public interface CardComponent {

    /**
     * Returns the identifier of the represented card.
     *
     * @return the card identifier
     */
    CardId getCardId();

    /**
     * Updates the displayed health value of the card.
     *
     * @param newHealth the new health value
     */
    void setHealth(int newHealth);

    /**
     * Returns the Swing component representing this card.
     *
     * @return the root component of the card
     */
    JButton getComponent();

}
