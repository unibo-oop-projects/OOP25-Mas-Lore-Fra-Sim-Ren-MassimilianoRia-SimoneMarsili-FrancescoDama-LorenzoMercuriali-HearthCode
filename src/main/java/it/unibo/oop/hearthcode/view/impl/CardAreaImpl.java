package it.unibo.oop.hearthcode.view.impl;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.view.api.CardArea;
import it.unibo.oop.hearthcode.view.api.CardComponent;

/**
 * Implementation of {@link CardArea}.
 */
public final class CardAreaImpl extends JPanel implements CardArea {

    private static final long serialVersionUID = 1L;

    private static final int PANEL_HEIGHT = 220;

    @SuppressFBWarnings(
        value = "SE_TRANSIENT_FIELD_NOT_RESTORED",
        justification = "This Swing UI component is not meant to support meaningful deserialization."
    )
    private final transient Map<CardId, CardComponent> cards = new LinkedHashMap<>();

    /**
     * Builds the UI area containing card components.
     *
     * @param title the title of the panel
     */
    public CardAreaImpl(final String title) {
        super(new FlowLayout(FlowLayout.LEFT, 8, 8));
        this.setOpaque(false);
        this.setBorder(BorderFactory.createTitledBorder(title));
        this.setPreferredSize(new Dimension(0, PANEL_HEIGHT));
    }

    @Override
    public void addCard(final CardComponent card) {
        if (this.cards.containsKey(card.getCardId())) {
            throw new IllegalArgumentException("Unique CardId already present in this CardArea.");
        }
        this.cards.put(card.getCardId(), card);
        this.add(card.getComponent());
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
        final CardComponent card = this.cards.remove(cardId);
        if (card == null) {
            throw new IllegalArgumentException("Requested CardId absent in this CardArea.");
        }
        this.remove(card.getComponent());
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
