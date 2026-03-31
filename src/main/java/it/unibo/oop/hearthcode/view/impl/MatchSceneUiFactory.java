package it.unibo.oop.hearthcode.view.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

final class MatchSceneUiFactory {

    private static final int BUTTONS_NUMBER = 5;
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

    private MatchSceneUiFactory() {
    }

    static JButton createActionButton(
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

    static JPanel createPanel() {
        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    static JComponent createActionPanel(
        final JButton attackHeroButton,
        final JButton attackCreatureButton,
        final JButton placeCardButton,
        final JButton endTurnButton,
        final JButton exitButton
    ) {
        final JPanel actionPanel = createTitledPanel("Actions");
        actionPanel.setPreferredSize(new Dimension(ViewMetrics.sidePanelWidth(), 0));
        actionPanel.setLayout(new GridLayout(BUTTONS_NUMBER, 1, 0, ViewMetrics.verticalGap() * 2));
        actionPanel.add(attackHeroButton);
        actionPanel.add(attackCreatureButton);
        actionPanel.add(placeCardButton);
        actionPanel.add(endTurnButton);
        actionPanel.add(exitButton);
        return actionPanel;
    }

    private static JPanel createTitledPanel(final String title) {
        final JPanel panel = createPanel();
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
        panel.setBorder(new CompoundBorder(
            titledBorder,
            BorderFactory.createEmptyBorder(
                PANEL_OUTER_PADDING,
                PANEL_OUTER_PADDING,
                PANEL_OUTER_PADDING,
                PANEL_OUTER_PADDING
            )
        ));
        panel.setBackground(ACTION_PANEL_BACKGROUND);
        panel.setOpaque(true);
        return panel;
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
