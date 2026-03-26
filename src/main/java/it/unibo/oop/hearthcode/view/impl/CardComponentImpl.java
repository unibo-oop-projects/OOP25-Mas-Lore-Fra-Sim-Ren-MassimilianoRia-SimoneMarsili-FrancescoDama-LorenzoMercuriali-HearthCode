package it.unibo.oop.hearthcode.view.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.view.api.CardComponent;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Texture-based implementation of {@link CardComponent}.
 * The whole button surface is the card image, while only health text
 * is painted at the bottom of the button.
 */
public final class CardComponentImpl extends JButton implements CardComponent {

    private static final long serialVersionUID = 1L;

    private static final int ARC_SIZE = 18;
    private static final int SELECTION_BORDER_THICKNESS = 3;
    private static final int TEXT_BOTTOM_MARGIN = 8;
    private static final int TEXT_SHADOW_OFFSET = 1;

    private static final Color SELECTION_BORDER_COLOR = new Color(210, 40, 40, 230);
    private static final Color HEALTH_TEXT_COLOR = Color.WHITE;
    private static final Color HEALTH_TEXT_SHADOW_COLOR = new Color(0, 0, 0, 210);

    private final transient CardId cardId;
    private final transient ImageIcon frontIcon;
    private final transient ImageIcon backIcon;

    private final int maxHealth;
    private int currentHealth;
    private boolean faceUp;
    private boolean selectedVisual;

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
        this.frontIcon = new ImageIcon(frontIcon.getImage());
        this.backIcon = new ImageIcon(backIcon.getImage());
        this.maxHealth = def.health();
        this.currentHealth = def.health();
        this.faceUp = false;
        this.selectedVisual = false;

        final Dimension size = new Dimension(
            ViewMetrics.cardWidth(),
            ViewMetrics.cardHeight()
        );

        this.setPreferredSize(size);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setOpaque(false);
        this.setText(null);
        this.setIcon(null);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        final ImageIcon texture = this.faceUp ? this.frontIcon : this.backIcon;
        g2.drawImage(texture.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);

        if (this.faceUp) {
            this.paintHealthText(g2);
        }

        if (this.selectedVisual) {
            g2.setStroke(new BasicStroke(SELECTION_BORDER_THICKNESS));
            g2.setColor(SELECTION_BORDER_COLOR);
            g2.draw(new RoundRectangle2D.Float(
                SELECTION_BORDER_THICKNESS / 2.0f,
                SELECTION_BORDER_THICKNESS / 2.0f,
                this.getWidth() - SELECTION_BORDER_THICKNESS,
                this.getHeight() - SELECTION_BORDER_THICKNESS,
                ARC_SIZE,
                ARC_SIZE
            ));
        }

        g2.dispose();
    }

    private void paintHealthText(final Graphics2D g2) {
        final String text = this.currentHealth + " / " + this.maxHealth;
        final int fontSize = Math.max(14, Math.min(22, this.getWidth() / 7));
        final Font font = this.getFont().deriveFont(Font.BOLD, (float) fontSize);

        g2.setFont(font);
        final FontMetrics fm = g2.getFontMetrics();

        final int textX = (this.getWidth() - fm.stringWidth(text)) / 2;
        final int textY = this.getHeight() - TEXT_BOTTOM_MARGIN;

        g2.setColor(HEALTH_TEXT_SHADOW_COLOR);
        g2.drawString(text, textX + TEXT_SHADOW_OFFSET, textY + TEXT_SHADOW_OFFSET);

        g2.setColor(HEALTH_TEXT_COLOR);
        g2.drawString(text, textX, textY);
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
        this.repaint();
    }

    @Override
    public void setSelectedVisual(final boolean selected) {
        this.selectedVisual = selected;
        this.repaint();
    }

}
