package it.unibo.oop.hearthcode.view.impl;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;

import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabase;
import it.unibo.oop.hearthcode.view.api.DatabaseView;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Scene showing the available deck cards.
 */
public final class DatabaseScene extends AbstractBackgroundScene implements DatabaseView {

    private static final long serialVersionUID = 1L;

    private static final int BUTTON_WIDTH = ViewMetrics.menuButtonWidth();
    private static final int BUTTON_HEIGHT = ViewMetrics.menuButtonHeight();
    private static final String BACKGROUND_PATH = "/images/menu-background.png";
    private static final int TOP_MARGIN = ViewMetrics.cardHeight();
    private static final int CARDS_BOTTOM_MARGIN = ViewMetrics.menuVerticalGap() * 2;
    private static final int BUTTON_BOTTOM_MARGIN = ViewMetrics.cardHeight() / 2;
    private final JButton backButton;

    /**
     * Builds the database scene.
     * 
     * @param definitions the creatures to use
     */
    public DatabaseScene(final CreatureDatabase definitions) {
        super(BACKGROUND_PATH);
        this.setLayout(new GridBagLayout());
        this.backButton = this.createImageButton(
            "/images/back-normal.png",
            "/images/back-hover.png",
            "/images/back-pressed.png",
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );
        this.initializeLayout(definitions);
    }

    private void initializeLayout(final CreatureDatabase definitions) {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(TOP_MARGIN, 0, CARDS_BOTTOM_MARGIN, 0);
        this.add(new CardsPanel(definitions), gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, BUTTON_BOTTOM_MARGIN, 0);
        this.add(this.backButton, gbc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JComponent getComponent() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBack(final Runnable action) {
        this.backButton.addActionListener(event -> action.run());
    }

}
