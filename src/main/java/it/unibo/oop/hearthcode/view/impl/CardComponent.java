package it.unibo.oop.hearthcode.view.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.view.utility.CreatureImagePaths;
import it.unibo.oop.hearthcode.view.utility.ImageLoader;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Simple texture-based card button.
 */
final class CardComponent extends JButton {

    private static final long serialVersionUID = 1L;

    private static final Color SELECTED_BORDER_COLOR = new Color(226, 183, 76);
    private static final Color DORMANT_BORDER_COLOR = new Color(102, 131, 89);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color DORMANT_OVERLAY_COLOR = new Color(61, 89, 57, 110);
    private static final Color SELECTED_OVERLAY_COLOR = new Color(232, 201, 112, 48);
    private static final int OVERLAY_MARGIN = 2;
    private static final int OVERLAY_SIZE_OFFSET = 4;
    private static final int BORDER_SIZE_OFFSET = 5;
    private static final int CARD_CORNER_RADIUS = 18;
    private static final double HEALTH_TEXT_CENTER_X_RATIO = 0.5;
    private static final double HEALTH_TEXT_CENTER_Y_RATIO = 0.89;
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
    CardComponent(
        final CardId cardId,
        final CreatureDefinition def
    ) {
        this.cardId = cardId;
        this.frontIcon = ImageLoader.load(
            CreatureImagePaths.card(def.name()),
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
        super.paintComponent(g);
        final Graphics2D graphics = (Graphics2D) g.create();
        try {
            setQualityRenderingHints(graphics);
            final ImageIcon icon = this.faceUp ? this.frontIcon : this.backIcon;
            graphics.drawImage(icon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
            if (this.resting || this.selected) {
                graphics.setColor(this.selected ? SELECTED_OVERLAY_COLOR : DORMANT_OVERLAY_COLOR);
                graphics.fillRoundRect(
                    OVERLAY_MARGIN,
                    OVERLAY_MARGIN,
                    this.getWidth() - OVERLAY_SIZE_OFFSET,
                    this.getHeight() - OVERLAY_SIZE_OFFSET,
                    CARD_CORNER_RADIUS,
                    CARD_CORNER_RADIUS
                );
            }
            if (this.faceUp) {
                this.paintHealth(graphics);
            }
        } finally {
            graphics.dispose();
        }
    }

    private void paintHealth(final Graphics graphics) {
        final String text = this.currentHealth + " / " + this.maxHealth;
        final FontMetrics metrics = graphics.getFontMetrics();
        final int centerX = (int) Math.round(this.getWidth() * HEALTH_TEXT_CENTER_X_RATIO);
        final int centerY = (int) Math.round(this.getHeight() * HEALTH_TEXT_CENTER_Y_RATIO);
        final int x = centerX - metrics.stringWidth(text) / 2;
        final int y = centerY + (metrics.getAscent() - metrics.getDescent()) / 2;
        graphics.setColor(TEXT_COLOR);
        graphics.drawString(text, x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintBorder(final Graphics g) {
        if (!this.selected && !this.resting) {
            return;
        }
        g.setColor(this.selected ? SELECTED_BORDER_COLOR : DORMANT_BORDER_COLOR);
        g.drawRoundRect(
            OVERLAY_MARGIN,
            OVERLAY_MARGIN,
            this.getWidth() - BORDER_SIZE_OFFSET,
            this.getHeight() - BORDER_SIZE_OFFSET,
            CARD_CORNER_RADIUS,
            CARD_CORNER_RADIUS
        );
    }

    CardId getCardId() {
        return this.cardId;
    }

    void setHealth(final int newHealth) {
        this.currentHealth = newHealth;
        this.repaint();
    }

    void setFaceUp(final boolean faceUp) {
        this.faceUp = faceUp;
        this.repaint();
    }

    void setSelectedVisual(final boolean isCardSelected) {
        this.selected = isCardSelected;
        this.repaint();
    }

    void setRestingVisual(final boolean isCardResting) {
        this.resting = isCardResting;
        this.repaint();
    }

    private static void setQualityRenderingHints(final Graphics2D graphics) {
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

}
