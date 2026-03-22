package it.unibo.oop.hearthcode.view.api;

import it.unibo.oop.hearthcode.model.creature.api.CardId;

public interface CardComponent {

    CardId getCardId();

    void setHealth(int newHealth);

}