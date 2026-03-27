package it.unibo.oop.hearthcode.view.impl;

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
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Simple implementation of {@link CardArea}.
 */
public final class CardAreaImpl extends JPanel implements CardArea {

    private static final long serialVersionUID = 1L;

    private final Map<CardId, CardComponent> cards = new LinkedHashMap<>();

    /**
     * Builds the UI area containing card components.
     *
     * @param title the title of the panel
     */
    public CardAreaImpl(final String title) {
        super(new FlowLayout(
            FlowLayout.LEFT,
            ViewMetrics.horizontalGap(),
            ViewMetrics.verticalGap()
        ));
        this.setOpaque(false);
        this.setBorder(BorderFactory.createTitledBorder(title));
    }

    @Override
    public void addCard(final CardComponent card) {
        this.cards.put(card.getCardId(), card);
        this.add(card.getComponent());
        this.revalidate();
        this.repaint();
    }

    @Override
    public CardComponent getCard(final CardId cardId) {
        final CardComponent card = this.cards.get(cardId);
        if (card == null) {
            throw new IllegalArgumentException("Card not found in area: " + cardId);
        }
        return card;
    }

    @Override
    public List<CardComponent> getCards() {
        return List.copyOf(this.cards.values());
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void removeCard(final CardId cardId) {
        final CardComponent card = this.cards.remove(cardId);
        if (card == null) {
            throw new IllegalArgumentException("Card not found in area: " + cardId);
        }
        this.remove(card.getComponent());
        this.revalidate();
        this.repaint();
    }

}
