package it.unibo.oop.hearthcode.view.api;

import java.util.List;

import javax.swing.JComponent;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.view.impl.CardComponentImpl;

public interface CardArea {

    CardComponent getCard(CardId cardId);

    List<CardComponent> getCards();

    JComponent getComponent();

    void addCard(CardComponentImpl card);

    void removeCard(CardId cardId);

}