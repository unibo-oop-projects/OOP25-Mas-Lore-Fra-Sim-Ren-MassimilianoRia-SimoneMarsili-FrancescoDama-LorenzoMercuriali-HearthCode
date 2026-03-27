package it.unibo.oop.hearthcode.view.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.view.api.CardComponent;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Simple texture-based implementation of {@link CardComponent}.
 */
public final class CardComponentImpl extends JButton implements CardComponent {

    private static final long serialVersionUID = 1L;

    private static final Border NORMAL_BORDER = BorderFactory.createEmptyBorder(3, 3, 3, 3);
    private static final Border SELECTED_BORDER = BorderFactory.createLineBorder(new Color(210, 40, 40), 3);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 180);
    private static final int TEXT_BOTTOM_MARGIN = 17;
    private static final int SHADOW_OFFSET = 1;

    private final CardId cardId;
    private final ImageIcon frontIcon;
    private final ImageIcon backIcon;
    private final int maxHealth;

    private int currentHealth;
    private boolean faceUp;

    /**
     * Builds the component representing a specific card.
     *
     * @param cardId the identifier of the represented card
     * @param def the card definition
     * @param frontIcon the front card image
     * @param backIcon the back card image
     */
    public CardComponentImpl(
        final CardId cardId,
        final CreatureDefinition def,
        final ImageIcon frontIcon,
        final ImageIcon backIcon
    ) {
        this.cardId = cardId;
        this.frontIcon = frontIcon;
        this.backIcon = backIcon;
        this.maxHealth = def.health();
        this.currentHealth = def.health();

        final Dimension size = new Dimension(ViewMetrics.cardWidth(), ViewMetrics.cardHeight());
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        this.setMaximumSize(size);

        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setOpaque(false);
        this.setBorder(NORMAL_BORDER);
        this.setBorderPainted(true);
        this.setText(null);
        this.setFaceUp(false);
    }

    private void updateIcon() {
        final ImageIcon icon = this.faceUp ? this.frontIcon : this.backIcon;
        this.setIcon(icon);
        this.setDisabledIcon(icon);
        this.repaint();
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (!this.faceUp) {
            return;
        }

        final String text = this.currentHealth + " / " + this.maxHealth;
        final int fontSize = Math.max(14, this.getWidth() / 9);
        final Font font = this.getFont().deriveFont(Font.BOLD, (float) fontSize);
        g.setFont(font);

        final FontMetrics metrics = g.getFontMetrics();
        final int x = (this.getWidth() - metrics.stringWidth(text)) / 2;
        final int y = this.getHeight() - TEXT_BOTTOM_MARGIN;

        g.setColor(SHADOW_COLOR);
        g.drawString(text, x + SHADOW_OFFSET, y + SHADOW_OFFSET);
        g.setColor(TEXT_COLOR);
        g.drawString(text, x, y);
    }

    @Override
    public CardId getCardId() {
        return this.cardId;
    }

    @Override
    public void setHealth(final int newHealth) {
        this.currentHealth = newHealth;
        this.repaint();
    }

    @Override
    public JButton getComponent() {
        return this;
    }

    @Override
    public void setFaceUp(final boolean faceUp) {
        this.faceUp = faceUp;
        this.updateIcon();
    }

    @Override
    public void setSelectedVisual(final boolean selected) {
        this.setBorder(selected ? SELECTED_BORDER : NORMAL_BORDER);
        this.repaint();
    }

}
