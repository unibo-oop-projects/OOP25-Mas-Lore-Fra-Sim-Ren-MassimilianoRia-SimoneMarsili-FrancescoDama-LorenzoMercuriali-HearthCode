package it.unibo.oop.hearthcode.view.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Simple area containing card buttons.
 */
final class CardArea extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final float PANEL_TITLE_FONT_SIZE = 14f;
    private static final Color AREA_BACKGROUND = new Color(50, 68, 43, 185);
    private static final Color AREA_BORDER = new Color(163, 132, 70);
    private static final Color AREA_TITLE = new Color(243, 228, 183);

    private final Map<CardId, CardComponent> cards = new LinkedHashMap<>();

    /**
     * Builds the UI area containing card components.
     *
     * @param title the title of the panel
     */
    CardArea(final String title) {
        super(new FlowLayout(
            FlowLayout.LEFT,
            ViewMetrics.horizontalGap() * 2,
            ViewMetrics.verticalGap() * 2
        ));
        this.setOpaque(false);
        this.setBackground(AREA_BACKGROUND);
        final TitledBorder titledBorder = BorderFactory.createTitledBorder(
            new CompoundBorder(
                new LineBorder(AREA_BORDER, 1, true),
                new EmptyBorder(10, 10, 10, 10)
            ),
            title
        );
        titledBorder.setTitleColor(AREA_TITLE);
        titledBorder.setTitleFont(titledBorder.getTitleFont().deriveFont(Font.BOLD, PANEL_TITLE_FONT_SIZE));
        this.setBorder(titledBorder);
        this.setPreferredSize(new Dimension(0, ViewMetrics.cardAreaHeight()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    void addCard(final CardComponent card) {
        this.cards.put(card.getCardId(), card);
        this.add(card);
        this.refreshArea();
    }

    CardComponent getCard(final CardId cardId) {
        final CardComponent card = this.cards.get(cardId);
        if (card == null) {
            throw new IllegalArgumentException("Card not found in area: " + cardId);
        }
        return card;
    }

    List<CardComponent> getCards() {
        return List.copyOf(this.cards.values());
    }

    void removeCard(final CardId cardId) {
        final CardComponent card = this.cards.remove(cardId);
        if (card == null) {
            throw new IllegalArgumentException("Card not found in area: " + cardId);
        }
        this.remove(card);
        this.refreshArea();
    }

    private void refreshArea() {
        this.revalidate();
        this.repaint();
    }

}
