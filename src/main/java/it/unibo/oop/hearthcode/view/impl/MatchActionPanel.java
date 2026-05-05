package it.unibo.oop.hearthcode.view.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Panel containing the actions available during a match.
 */
final class MatchActionPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int BUTTONS_COUNT = 5;
    private static final float ACTION_BUTTON_FONT_SIZE = 14f;
    private static final float PANEL_TITLE_FONT_SIZE = 14f;
    private static final int BUTTON_VERTICAL_PADDING = 8;
    private static final int BUTTON_HORIZONTAL_PADDING = 12;
    private static final int PANEL_INNER_PADDING = 10;
    private static final int PANEL_OUTER_PADDING = 4;
    private static final Color ACTION_PANEL_BACKGROUND = new Color(41, 57, 39, 220);
    private static final Color ACTION_PANEL_BORDER = new Color(172, 141, 74);
    private static final Color ACTION_PANEL_TITLE = new Color(241, 225, 178);
    private static final Color PRIMARY_BUTTON_DISABLED = new Color(73, 78, 61);
    private static final Color BUTTON_TEXT = new Color(247, 239, 214);
    private static final Color PRIMARY_BUTTON = new Color(82, 113, 68);
    private static final Color PRIMARY_BUTTON_HOVER = new Color(103, 136, 83);
    private static final Color DANGER_BUTTON = new Color(136, 78, 52);
    private static final Color DANGER_BUTTON_HOVER = new Color(160, 97, 66);

    private final JButton attackHeroButton;
    private final JButton attackCreatureButton;
    private final JButton placeCardButton;
    private final JButton endTurnButton;
    private final JButton exitButton;

    MatchActionPanel() {
        this.attackHeroButton = createActionButton("ATTACK HERO", PRIMARY_BUTTON, PRIMARY_BUTTON_HOVER);
        this.attackCreatureButton = createActionButton("ATTACK CREATURE", PRIMARY_BUTTON, PRIMARY_BUTTON_HOVER);
        this.placeCardButton = createActionButton("PLACE CARD", PRIMARY_BUTTON, PRIMARY_BUTTON_HOVER);
        this.endTurnButton = createActionButton("END TURN", PRIMARY_BUTTON, PRIMARY_BUTTON_HOVER);
        this.exitButton = createActionButton("EXIT", DANGER_BUTTON, DANGER_BUTTON_HOVER);

        this.setPreferredSize(new Dimension(ViewMetrics.sidePanelWidth(), 0));
        this.setLayout(new GridLayout(BUTTONS_COUNT, 1, 0, ViewMetrics.verticalGap() * 2));
        this.setBackground(ACTION_PANEL_BACKGROUND);
        this.setOpaque(true);
        this.setBorder(createPanelBorder("Actions"));
        this.add(this.attackHeroButton);
        this.add(this.attackCreatureButton);
        this.add(this.placeCardButton);
        this.add(this.endTurnButton);
        this.add(this.exitButton);
    }

    void onAttackHero(final Runnable action) {
        this.attackHeroButton.addActionListener(event -> action.run());
    }

    void onAttackCreature(final Runnable action) {
        this.attackCreatureButton.addActionListener(event -> action.run());
    }

    void onPlaceCard(final Runnable action) {
        this.placeCardButton.addActionListener(event -> action.run());
    }

    void onEndTurn(final Runnable action) {
        this.endTurnButton.addActionListener(event -> action.run());
    }

    void onExitGame(final Runnable action) {
        this.exitButton.addActionListener(event -> action.run());
    }

    void setActionsEnabled(
        final boolean attackHeroEnabled,
        final boolean attackCreatureEnabled,
        final boolean placeCardEnabled,
        final boolean endTurnEnabled
    ) {
        this.attackHeroButton.setEnabled(attackHeroEnabled);
        this.attackCreatureButton.setEnabled(attackCreatureEnabled);
        this.placeCardButton.setEnabled(placeCardEnabled);
        this.endTurnButton.setEnabled(endTurnEnabled);
        this.exitButton.setEnabled(true);
    }

    private static JButton createActionButton(
        final String text,
        final Color background,
        final Color hoverBackground
    ) {
        final JButton button = new JButton(text);
        final Dimension size = new Dimension(ViewMetrics.actionButtonWidth(), ViewMetrics.actionButtonHeight());
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setForeground(BUTTON_TEXT);
        button.setBackground(background);
        button.setFont(button.getFont().deriveFont(Font.BOLD, ACTION_BUTTON_FONT_SIZE));
        button.setBorder(new CompoundBorder(
            new LineBorder(background.brighter(), 1, true),
            new EmptyBorder(
                BUTTON_VERTICAL_PADDING,
                BUTTON_HORIZONTAL_PADDING,
                BUTTON_VERTICAL_PADDING,
                BUTTON_HORIZONTAL_PADDING
            )
        ));
        button.setRolloverEnabled(true);
        button.addChangeListener(event -> updateButtonBackground(button, background, hoverBackground));
        return button;
    }

    private static CompoundBorder createPanelBorder(final String title) {
        final TitledBorder titledBorder = BorderFactory.createTitledBorder(
            new CompoundBorder(
                new LineBorder(ACTION_PANEL_BORDER, 1, true),
                new EmptyBorder(
                    PANEL_INNER_PADDING,
                    PANEL_INNER_PADDING,
                    PANEL_INNER_PADDING,
                    PANEL_INNER_PADDING
                )
            ),
            title
        );
        titledBorder.setTitleColor(ACTION_PANEL_TITLE);
        titledBorder.setTitleFont(titledBorder.getTitleFont().deriveFont(Font.BOLD, PANEL_TITLE_FONT_SIZE));
        return new CompoundBorder(
            titledBorder,
            BorderFactory.createEmptyBorder(
                PANEL_OUTER_PADDING,
                PANEL_OUTER_PADDING,
                PANEL_OUTER_PADDING,
                PANEL_OUTER_PADDING
            )
        );
    }

    private static void updateButtonBackground(
        final JButton button,
        final Color background,
        final Color hoverBackground
    ) {
        if (!button.isEnabled()) {
            button.setBackground(PRIMARY_BUTTON_DISABLED);
            return;
        }
        if (button.getModel().isPressed()) {
            button.setBackground(background.darker());
        } else if (button.getModel().isRollover()) {
            button.setBackground(hoverBackground);
        } else {
            button.setBackground(background);
        }
    }

}
