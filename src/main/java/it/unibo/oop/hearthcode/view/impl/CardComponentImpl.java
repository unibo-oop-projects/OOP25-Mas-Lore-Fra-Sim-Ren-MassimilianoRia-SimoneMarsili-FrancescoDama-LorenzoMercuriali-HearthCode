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
import it.unibo.oop.hearthcode.view.utility.ImageLoader;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Simple texture-based implementation of {@link CardComponent}.
 */
public final class CardComponentImpl extends JButton implements CardComponent {

    private static final long serialVersionUID = 1L;

    private static final Border NORMAL_BORDER = BorderFactory.createEmptyBorder(3, 3, 3, 3);
    private static final Border SELECTED_BORDER = BorderFactory.createLineBorder(new Color(210, 40, 40), 3);
    private static final Border DORMANT_BORDER = BorderFactory.createLineBorder(new Color(130, 130, 130), 3);

    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 180);
    private static final Color DORMANT_OVERLAY_COLOR = new Color(120, 120, 120, 110);

    private static final int TEXT_BOTTOM_MARGIN = 17;
    private static final int SHADOW_OFFSET = 1;

    private final transient CardId cardId;
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
        this.setBorderPainted(true);
        this.setText(null);

        this.updateBorder();
        this.setFaceUp(false);
    }

    private void updateIcon() {
        final ImageIcon icon = this.faceUp ? this.frontIcon : this.backIcon;
        this.setIcon(icon);
        this.setDisabledIcon(icon);
        this.repaint();
    }

    private void updateBorder() {
        if (this.selected) {
            this.setBorder(SELECTED_BORDER);
        } else if (this.resting) {
            this.setBorder(DORMANT_BORDER);
        } else {
            this.setBorder(NORMAL_BORDER);
        }
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        if (this.resting) {
            g.setColor(DORMANT_OVERLAY_COLOR);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

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
    public void setSelectedVisual(final boolean isCardSelected) {
        this.selected = isCardSelected;
        this.updateBorder();
        this.repaint();
    }

    @Override
    public void setRestingVisual(final boolean isCardResting) {
        this.resting = isCardResting;
        this.updateBorder();
        this.repaint();
    }

}
