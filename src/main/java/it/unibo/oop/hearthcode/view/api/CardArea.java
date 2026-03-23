package it.unibo.oop.hearthcode.view.api;

import java.util.List;

import javax.swing.JComponent;

import it.unibo.oop.hearthcode.model.creature.api.CardId;

/**
 * UI area containing a collection of card components.
 */
public interface CardArea {

    /**
     * Returns the card component associated with the given card identifier.
     *
     * @param cardId the card identifier
     * @return the matching card component
     */
    CardComponent getCard(CardId cardId);

    /**
     * Returns all the card components currently contained in this area.
     *
     * @return the list of card components
     */
    List<CardComponent> getCards();

    /**
     * Returns the Swing component representing this card area.
     *
     * @return the root component of the card area
     */
    JComponent getComponent();

    /**
     * Adds a card component to this area.
     *
     * @param card the card component to add
     */
    void addCard(CardComponent card);

    /**
     * Removes the card component associated with the given card identifier.
     *
     * @param cardId the card identifier
     */
    void removeCard(CardId cardId);

}
