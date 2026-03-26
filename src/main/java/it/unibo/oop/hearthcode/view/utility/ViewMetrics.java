package it.unibo.oop.hearthcode.view.utility;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Centralized metrics for the match UI.
 */
public final class ViewMetrics {

    private static final Dimension SCREEN = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SCREEN_WIDTH = SCREEN.width;
    private static final int SCREEN_HEIGHT = SCREEN.height;
    private static final int H_GAP = clamp((int) (SCREEN_WIDTH * 0.0045), 6, 14);
    private static final int V_GAP = clamp((int) (SCREEN_HEIGHT * 0.0065), 6, 14);
    private static final int OUTER_PADDING = clamp((int) (SCREEN_WIDTH * 0.004), 4, 12);
    private static final int CARD_WIDTH = clamp((int) (SCREEN_WIDTH * 0.068), 100, 150);
    private static final int CARD_HEIGHT = (int) Math.round(CARD_WIDTH * 1.3);
    private static final int CARD_TEXT_HEIGHT = 40;
    private static final int CARD_COMPONENT_HEIGHT = CARD_HEIGHT + CARD_TEXT_HEIGHT + 4;
    private static final int CARD_AREA_HEIGHT = clamp(CARD_COMPONENT_HEIGHT + 32, 210, 300);
    private static final int PLAYER_PANEL_HEIGHT = CARD_AREA_HEIGHT + (OUTER_PADDING * 2);
    private static final int ARMIES_PANEL_HEIGHT = (CARD_AREA_HEIGHT * 2) + V_GAP;
    private static final int SIDE_PANEL_WIDTH = clamp((int) (SCREEN_WIDTH * 0.13), 170, 280);
    private static final int ACTION_BUTTON_WIDTH = clamp((int) (SIDE_PANEL_WIDTH * 0.82), 130, 220);
    private static final int ACTION_BUTTON_HEIGHT = clamp((int) (SCREEN_HEIGHT * 0.055), 38, 56);

    private ViewMetrics() {
    }

    private static int clamp(final int value, final int min, final int max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * @return the preferred card width
     */
    public static int cardWidth() {
        return CARD_WIDTH;
    }

    /**
     * @return the preferred card height (image only)
     */
    public static int cardHeight() {
        return CARD_HEIGHT;
    }

    /**
     * @return the extra text height used under card image
     */
    public static int cardTextHeight() {
        return CARD_TEXT_HEIGHT;
    }

    /**
     * @return the full preferred card component height
     */
    public static int cardComponentHeight() {
        return CARD_COMPONENT_HEIGHT;
    }

    /**
     * @return the preferred height for a card area
     */
    public static int cardAreaHeight() {
        return CARD_AREA_HEIGHT;
    }

    /**
     * @return the preferred height for a player panel
     */
    public static int playerPanelHeight() {
        return PLAYER_PANEL_HEIGHT;
    }

    /**
     * @return the preferred height for the central armies panel
     */
    public static int armiesPanelHeight() {
        return ARMIES_PANEL_HEIGHT;
    }

    /**
     * @return the preferred width for side panels
     */
    public static int sidePanelWidth() {
        return SIDE_PANEL_WIDTH;
    }

    /**
     * @return the preferred width for action buttons
     */
    public static int actionButtonWidth() {
        return ACTION_BUTTON_WIDTH;
    }

    /**
     * @return the preferred height for action buttons
     */
    public static int actionButtonHeight() {
        return ACTION_BUTTON_HEIGHT;
    }

    /**
     * @return the horizontal gap between components
     */
    public static int horizontalGap() {
        return H_GAP;
    }

    /**
     * @return the vertical gap between components
     */
    public static int verticalGap() {
        return V_GAP;
    }

    /**
     * @return outer padding for panels
     */
    public static int outerPadding() {
        return OUTER_PADDING;
    }

}
