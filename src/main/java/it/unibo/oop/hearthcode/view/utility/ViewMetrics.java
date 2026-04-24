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
    private static final int H_GAP = (int) (SCREEN_WIDTH * 0.001);
    private static final int V_GAP = (int) (SCREEN_HEIGHT * 0.001);
    private static final int OUTER_PADDING = (int) (SCREEN_WIDTH * 0.001);
    private static final int CARD_HEIGHT = (int) (SCREEN_HEIGHT * 0.22);
    private static final int CARD_WIDTH = (int) Math.round(CARD_HEIGHT * 0.66);
    private static final int CARD_AREA_HEIGHT = CARD_HEIGHT + 26;
    private static final int SIDE_PANEL_WIDTH = (int) (SCREEN_WIDTH * 0.15);
    private static final int ACTION_BUTTON_WIDTH = (int) (SIDE_PANEL_WIDTH * 0.8);
    private static final int ACTION_BUTTON_HEIGHT = (int) (SCREEN_HEIGHT * 0.05);
    private static final int MENU_BUTTON_WIDTH = (int) (SCREEN_WIDTH * 0.21);
    private static final int MENU_BUTTON_HEIGHT = (int) (MENU_BUTTON_WIDTH * 0.35);
    private static final int MENU_VERTICAL_GAP = (int) (SCREEN_HEIGHT * 0.015);
    private static final int PLAYER_STATS_SECTION_GAP = (int) (SCREEN_HEIGHT * 0.025);
    private static final int END_BANNER_HEIGHT = (int) (SCREEN_WIDTH * 0.13);

    private ViewMetrics() {
    }

    /**
     * @return the screen height
     */
    public static int screenHeight() {
        return SCREEN_HEIGHT;
    }

    /**
     * @return the screen width
     */
    public static int screenWidth() {
        return SCREEN_WIDTH;
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
