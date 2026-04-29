package it.unibo.oop.hearthcode.view.impl;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;

import it.unibo.oop.hearthcode.view.api.DifficultySelectionView;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Implementation of {@link DifficultySelectionView}.
 */
public final class DifficultySelectionScene extends AbstractBackgroundScene implements DifficultySelectionView {

    private static final long serialVersionUID = 1L;

    private static final String BACKGROUND_PATH = "/images/menu-background.png";
    private static final int BUTTON_WIDTH = ViewMetrics.menuButtonWidth();
    private static final int BUTTON_HEIGHT = ViewMetrics.menuButtonHeight();
    private static final int BUTTON_PADDING_Y = ViewMetrics.menuVerticalGap();
    private final JButton normalButton;
    private final JButton hardButton;
    private final JButton backButton;

    /**
     * Builds the scene and initializes the layout.
     */
    public DifficultySelectionScene() {
        super(BACKGROUND_PATH);
        this.setLayout(new GridBagLayout());
        this.normalButton = this.createImageButton(
            "/images/normal-normal.png",
            "/images/normal-hover.png",
            "/images/normal-pressed.png",
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );
        this.hardButton = this.createImageButton(
            "/images/hard-normal.png",
            "/images/hard-hover.png",
            "/images/hard-pressed.png",
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );
        this.backButton = this.createImageButton(
            "/images/back-normal.png",
            "/images/back-hover.png",
            "/images/back-pressed.png",
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );
        this.initializeLayout();
    }

    private void initializeLayout() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(BUTTON_PADDING_Y, 0, BUTTON_PADDING_Y, 0);
        gbc.gridy = 0;
        this.add(this.normalButton, gbc);
        gbc.gridy = 1;
        this.add(this.hardButton, gbc);
        gbc.gridy = 2;
        this.add(this.backButton, gbc);
    }

    @Override
    public void onNormal(final Runnable action) {
        this.normalButton.addActionListener(event -> action.run());
    }

    @Override
    public void onHard(final Runnable action) {
        this.hardButton.addActionListener(event -> action.run());
    }

    @Override
    public void onBack(final Runnable action) {
        this.backButton.addActionListener(event -> action.run());
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

}
