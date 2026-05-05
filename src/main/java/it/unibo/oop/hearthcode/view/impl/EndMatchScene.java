package it.unibo.oop.hearthcode.view.impl;

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.model.player.api.PlayerId;
import it.unibo.oop.hearthcode.view.api.EndMatchView;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Implementation of {@link EndMatchView}.
 */
public final class EndMatchScene extends AbstractBackgroundScene implements EndMatchView {

    private static final long serialVersionUID = 1L;

    private static final String BACKGROUND_PATH = "/images/menu-background.png";
    private static final int BUTTON_WIDTH = ViewMetrics.menuButtonWidth();
    private static final int BUTTON_HEIGHT = ViewMetrics.menuButtonHeight();
    private static final int RESULT_WIDTH = ViewMetrics.viewportWidth();
    private static final int RESULT_HEIGHT = ViewMetrics.endBannerHeight();
    private static final PlayerId HUMAN_PLAYER = PlayerId.HUMAN;

    private final JButton menuButton;
    private final JLabel resultImageLabel;

    /**
     * Initializes the end match scene.
     *
     * @param playerId the winner of the match
     */
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

        this.resultImageLabel = new JLabel(this.loadResultIcon(playerId));
        this.resultImageLabel.setAlignmentX(CENTER_ALIGNMENT);

        this.initializeLayout();
    }

    private void initializeLayout() {
        final JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        this.menuButton.setAlignmentX(CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(this.resultImageLabel);
        centerPanel.add(Box.createVerticalStrut(ViewMetrics.menuVerticalGap()));
        centerPanel.add(this.menuButton);
        centerPanel.add(Box.createVerticalGlue());

        this.add(centerPanel, BorderLayout.CENTER);
    }

    private ImageIcon loadResultIcon(final PlayerId playerId) {
        final String imagePath = playerId.equals(HUMAN_PLAYER)
            ? "/images/you-won.png"
            : "/images/you-lost.png";
        return loadIcon(imagePath, RESULT_WIDTH, RESULT_HEIGHT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMenu(final Runnable action) {
        this.menuButton.addActionListener(event -> action.run());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JComponent getComponent() {
        return this;
    }

}
