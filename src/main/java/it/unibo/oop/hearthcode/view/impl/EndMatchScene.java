package it.unibo.oop.hearthcode.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.model.player.api.PlayerType;
import it.unibo.oop.hearthcode.view.api.EndMatchView;

public final class EndMatchScene extends AbstractBackgroundScene implements EndMatchView {

    private static final long serialVersionUID = 1L;

    private static final String BACKGROUND_PATH = "/images/menu-background.png";

    private static final int BUTTON_WIDTH = 280;
    private static final int BUTTON_HEIGHT = 100;

    private static final PlayerId HUMAN_PLAYER = new PlayerId(PlayerType.HUMAN_PLAYER);

    private final JButton menuButton;
    private final JLabel resultLabel;

    public EndMatchScene(final PlayerId playerId) {
        super(BACKGROUND_PATH);

        this.setLayout(new BorderLayout());

        this.menuButton = this.createImageButton(
            "/images/menu-normal.png",
            "/images/menu-hover.png",
            "/images/menu-pressed.png",
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );

        this.resultLabel = new JLabel(
            playerId.equals(HUMAN_PLAYER) ? "YOU WON" : "YOU LOST",
            SwingConstants.CENTER
        );

        this.resultLabel.setFont(new Font("Arial Black", Font.BOLD, 100));

        this.resultLabel.setForeground(Color.BLACK);

        this.resultLabel.setOpaque(true);
        this.resultLabel.setBackground(Color.WHITE);

        this.resultLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));

        this.initializeLayout();
    }

    private void initializeLayout() {
        // pannello centrale verticale
        final JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        this.resultLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.menuButton.setAlignmentX(CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(this.resultLabel);
        centerPanel.add(Box.createVerticalStrut(10)); // spazio piccolo
        centerPanel.add(this.menuButton);
        centerPanel.add(Box.createVerticalGlue());

        this.add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void onMenu(final Runnable action) {
        this.menuButton.addActionListener(event -> action.run());
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
}
