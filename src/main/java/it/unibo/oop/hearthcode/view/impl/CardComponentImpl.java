package it.unibo.oop.hearthcode.view.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.view.api.CardComponent;
import it.unibo.oop.hearthcode.view.utility.ImageLoader;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Simple texture-based implementation of {@link CardComponent}.
 */
public final class CardComponentImpl extends JButton implements CardComponent {

    private static final long serialVersionUID = 1L;

    private static final Color SELECTED_BORDER_COLOR = new Color(226, 183, 76);
    private static final Color DORMANT_BORDER_COLOR = new Color(102, 131, 89);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 180);
    private static final Color DORMANT_OVERLAY_COLOR = new Color(61, 89, 57, 110);
    private static final Color SELECTED_OVERLAY_COLOR = new Color(232, 201, 112, 48);
    private static final int OVERLAY_MARGIN = 2;
    private static final int OVERLAY_SIZE_OFFSET = 4;
    private static final int BORDER_SIZE_OFFSET = 5;
    private static final int CARD_CORNER_RADIUS = 18;
    private static final int TEXT_BOTTOM_MARGIN = 17;
    private static final int SHADOW_OFFSET = 1;
    private final CardId cardId;
    private final ImageIcon frontIcon;
    private final ImageIcon backIcon;
    private final int maxHealth;
    private int currentHealth;
    private boolean faceUp;
    private boolean selected;
    private boolean resting;

    /**
     * Builds the component representing a specific card.
     *
     * @param cardId the identifier of the represented card
     * @param def the card definition
     */
    public CardComponentImpl(
        final CardId cardId,
        final CreatureDefinition def
    ) {
        this.cardId = cardId;
        this.frontIcon = ImageLoader.load(
            "/images/cards/creatures/" + def.name() + ".png",
            ViewMetrics.cardWidth(),
            ViewMetrics.cardHeight()
        );
        this.backIcon = ImageLoader.load(
            "/images/cards/utility/card_cover.png",
            ViewMetrics.cardWidth(),
            ViewMetrics.cardHeight()
        );
        this.maxHealth = def.health();
        this.currentHealth = def.health();
        this.selected = false;
        this.resting = false;
        final Dimension size = new Dimension(ViewMetrics.cardWidth(), ViewMetrics.cardHeight());
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        this.setMaximumSize(size);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setOpaque(false);
        this.setRolloverEnabled(false);
        this.setBorderPainted(false);
        this.setText(null);
        this.setFaceUp(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final ImageIcon icon = this.faceUp ? this.frontIcon : this.backIcon;
        icon.paintIcon(this, g2d, 0, 0);
        if (this.resting) {
            g2d.setColor(DORMANT_OVERLAY_COLOR);
            g2d.fillRoundRect(
                OVERLAY_MARGIN,
                OVERLAY_MARGIN,
                this.getWidth() - OVERLAY_SIZE_OFFSET,
                this.getHeight() - OVERLAY_SIZE_OFFSET,
                CARD_CORNER_RADIUS,
                CARD_CORNER_RADIUS
            );
        }
        if (this.selected) {
            g2d.setColor(SELECTED_OVERLAY_COLOR);
            g2d.fillRoundRect(
                OVERLAY_MARGIN,
                OVERLAY_MARGIN,
                this.getWidth() - OVERLAY_SIZE_OFFSET,
                this.getHeight() - OVERLAY_SIZE_OFFSET,
                CARD_CORNER_RADIUS,
                CARD_CORNER_RADIUS
            );
        }
        if (!this.faceUp) {
            g2d.dispose();
            return;
        }
        final String text = this.currentHealth + " / " + this.maxHealth;
        final int fontSize = Math.max(14, this.getWidth() / 9);
        final Font font = this.getFont().deriveFont(Font.BOLD, (float) fontSize);
        g2d.setFont(font);
        final FontMetrics metrics = g2d.getFontMetrics();
        final int x = (this.getWidth() - metrics.stringWidth(text)) / 2;
        final int y = this.getHeight() - TEXT_BOTTOM_MARGIN;
        g2d.setColor(SHADOW_COLOR);
        g2d.drawString(text, x + SHADOW_OFFSET, y + SHADOW_OFFSET);
        g2d.setColor(TEXT_COLOR);
        g2d.drawString(text, x, y);
        g2d.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintBorder(final Graphics g) {
        if (!this.selected && !this.resting) {
            return;
        }
        final Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(4f));
        g2d.setColor(this.selected ? SELECTED_BORDER_COLOR : DORMANT_BORDER_COLOR);
        g2d.drawRoundRect(
            OVERLAY_MARGIN,
            OVERLAY_MARGIN,
            this.getWidth() - BORDER_SIZE_OFFSET,
            this.getHeight() - BORDER_SIZE_OFFSET,
            CARD_CORNER_RADIUS,
            CARD_CORNER_RADIUS
        );
        g2d.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CardId getCardId() {
        return this.cardId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHealth(final int newHealth) {
        this.currentHealth = newHealth;
        this.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JButton getComponent() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFaceUp(final boolean faceUp) {
        this.faceUp = faceUp;
        this.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedVisual(final boolean isCardSelected) {
        this.selected = isCardSelected;
        this.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRestingVisual(final boolean isCardResting) {
        this.resting = isCardResting;
        this.repaint();
    }

}
