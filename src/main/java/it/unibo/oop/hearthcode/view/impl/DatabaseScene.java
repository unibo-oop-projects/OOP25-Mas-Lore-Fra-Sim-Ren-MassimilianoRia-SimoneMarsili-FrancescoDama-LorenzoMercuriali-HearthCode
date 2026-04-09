package it.unibo.oop.hearthcode.view.impl;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComponent;

import it.unibo.oop.hearthcode.view.api.DatabaseView;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

public class DatabaseScene extends AbstractBackgroundScene implements DatabaseView {

    private static final long serialVersionUID = 1L;

    private static final int BUTTON_WIDTH = ViewMetrics.menuButtonWidth();
    private static final int BUTTON_HEIGHT = ViewMetrics.menuButtonHeight();
    private static final String BACKGROUND_PATH = "/images/menu-background.png";
    private final JButton backButton;

    public DatabaseScene() {
        super(BACKGROUND_PATH);
        this.setLayout(new BorderLayout());
        final CardsPanel cardPanel = new CardsPanel();
        this.add(cardPanel.getComponent(), BorderLayout.CENTER);
            this.backButton = this.createImageButton(
            "/images/back-normal.png",
            "/images/back-hover.png",
            "/images/back-pressed.png",
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );
        this.add(this.backButton, BorderLayout.SOUTH);
        this.add(cardPanel, BorderLayout.CENTER);
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void onBack(Runnable action) {
        this.backButton.addActionListener(event -> action.run());
    }
    
}
