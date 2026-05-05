package it.unibo.oop.hearthcode.view.utility;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Centralized metrics for the match UI.
 */
public final class ViewMetrics {

    private static final int VIEWPORT_ASPECT_WIDTH = 16;
    private static final int VIEWPORT_ASPECT_HEIGHT = 9;
    private static final double PREFERRED_CARD_HEIGHT_RATIO = 0.22;
    private static final double CARD_WIDTH_HEIGHT_RATIO = 0.66;
    private static final int CARD_AREA_VERTICAL_DECORATION = 40;
    private static final int MATCH_CARD_ROWS = 4;
    private static final int MATCH_VERTICAL_BORDER_UNITS = 10;
    private static final int MATCH_VERTICAL_GAP_UNITS = 4;
    private static final Dimension SCREEN = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension VIEWPORT = viewportSize(SCREEN);
    private static final int VIEWPORT_WIDTH = VIEWPORT.width;
    private static final int VIEWPORT_HEIGHT = VIEWPORT.height;
    private static final int H_GAP = (int) (VIEWPORT_WIDTH * 0.001);
    private static final int V_GAP = (int) (VIEWPORT_HEIGHT * 0.001);
    private static final int OUTER_PADDING = (int) (VIEWPORT_WIDTH * 0.001);
    private static final int CARD_HEIGHT = computeCardHeight();
    private static final int CARD_WIDTH = (int) Math.round(CARD_HEIGHT * CARD_WIDTH_HEIGHT_RATIO);
    private static final int CARD_AREA_HEIGHT = CARD_HEIGHT + CARD_AREA_VERTICAL_DECORATION;
    private static final int SIDE_PANEL_WIDTH = (int) (VIEWPORT_WIDTH * 0.15);
    private static final int ACTION_BUTTON_WIDTH = (int) (SIDE_PANEL_WIDTH * 0.8);
    private static final int ACTION_BUTTON_HEIGHT = (int) (VIEWPORT_HEIGHT * 0.05);
    private static final int MENU_BUTTON_WIDTH = (int) (VIEWPORT_WIDTH * 0.21);
    private static final int MENU_BUTTON_HEIGHT = (int) (MENU_BUTTON_WIDTH * 0.35);
    private static final int MENU_VERTICAL_GAP = (int) (VIEWPORT_HEIGHT * 0.015);
    private static final int PLAYER_STATS_SECTION_GAP = (int) (VIEWPORT_HEIGHT * 0.025);
    private static final int END_BANNER_HEIGHT = (int) (VIEWPORT_WIDTH * 0.13);

    private ViewMetrics() {
    }

    private static int computeCardHeight() {
        final int preferredHeight = (int) (VIEWPORT_HEIGHT * PREFERRED_CARD_HEIGHT_RATIO);
        final int availableHeight = VIEWPORT_HEIGHT
            - CARD_AREA_VERTICAL_DECORATION * MATCH_CARD_ROWS
            - OUTER_PADDING * MATCH_VERTICAL_BORDER_UNITS
            - V_GAP * MATCH_VERTICAL_GAP_UNITS;
        final int fittingHeight = availableHeight / MATCH_CARD_ROWS;
        return Math.max(1, Math.min(preferredHeight, fittingHeight));
    }

    /**
     * Computes the largest viewport fitting the fixed application aspect ratio.
     *
     * @param bounds the available bounds
     * @return the viewport size
     */
    public static Dimension viewportSize(final Dimension bounds) {
        if (bounds.width <= 0 || bounds.height <= 0) {
            return new Dimension(0, 0);
        }
        final double targetRatio = (double) VIEWPORT_ASPECT_WIDTH / VIEWPORT_ASPECT_HEIGHT;
        final double boundsRatio = (double) bounds.width / bounds.height;
        if (boundsRatio > targetRatio) {
            return new Dimension((int) Math.round(bounds.height * targetRatio), bounds.height);
        }
        return new Dimension(bounds.width, (int) Math.round(bounds.width / targetRatio));
    }

    /**
     * @return the viewport width
     */
    public static int viewportWidth() {
        return VIEWPORT_WIDTH;
    }

    /**
     * @return the preferred card height
     */
    public static int cardHeight() {
        return CARD_HEIGHT;
    }

    /**
     * @return the preferred card width
     */
    public static int cardWidth() {
        return CARD_WIDTH;
    }

    /**
     * @return the preferred card area height
     */
    public static int cardAreaHeight() {
        return CARD_AREA_HEIGHT;
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

    /**
     * @return the preferred width for menu buttons
     */
    public static int menuButtonWidth() {
        return MENU_BUTTON_WIDTH;
    }

    /**
     * @return the preferred height for menu buttons
     */
    public static int menuButtonHeight() {
        return MENU_BUTTON_HEIGHT;
    }

    /**
     * @return the vertical gap between menu buttons
     */
    public static int menuVerticalGap() {
        return MENU_VERTICAL_GAP;
    }

    /**
     * @return the vertical gap between sections in the player stats panel
     */
    public static int playerStatsSectionGap() {
        return PLAYER_STATS_SECTION_GAP;
    }

    /**
     * @return the preferred height for end banner
     */
    public static int endBannerHeight() {
        return END_BANNER_HEIGHT;
    }

}
