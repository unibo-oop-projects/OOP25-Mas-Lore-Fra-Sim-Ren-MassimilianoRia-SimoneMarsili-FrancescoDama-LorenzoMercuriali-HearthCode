package it.unibo.oop.hearthcode.view.impl;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.view.api.CardArea;
import it.unibo.oop.hearthcode.view.api.CardComponent;

public final class CardAreaImpl extends JPanel implements CardArea {

    private static final long serialVersionUID = 1L;
    
    private static final int PANEL_HEIGHT = 220;

    private final Map<CardId, CardComponentImpl> cards = new LinkedHashMap<>();

    public CardAreaImpl(final String title) {
        super(new FlowLayout(FlowLayout.LEFT, 8, 8));
        this.setOpaque(false);
        this.setBorder(BorderFactory.createTitledBorder(title));
        this.setPreferredSize(new Dimension(0, PANEL_HEIGHT));
    }

    @Override
    public void addCard(final CardComponentImpl card) {
        if (this.cards.containsKey(card.getCardId())) {
            throw new IllegalArgumentException("Unique CardId already present in this CardArea.");
        }
        this.cards.put(card.getCardId(), card);
        this.add(card);
        this.revalidate();
        this.repaint();
    }

    @Override
    public CardComponent getCard(final CardId cardId) {
        final CardComponent card = this.cards.get(cardId);
        if (card == null) {
            throw new IllegalArgumentException("Requested CardId absent in this CardArea.");
        }
        return card;
    }

    @Override
    public void removeCard(final CardId cardId) {
        final CardComponentImpl card = this.cards.remove(cardId);
        if (card == null) {
            throw new IllegalArgumentException("Requested CardId absent in this CardArea.");
        }
        this.remove(card);
        this.revalidate();
        this.repaint();
    }

    @Override
    public List<CardComponent> getCards() {
        return List.copyOf(this.cards.values());
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

}
