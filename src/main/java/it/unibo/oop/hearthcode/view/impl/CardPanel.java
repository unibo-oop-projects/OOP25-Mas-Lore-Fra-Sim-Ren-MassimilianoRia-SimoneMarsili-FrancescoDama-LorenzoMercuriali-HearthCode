package it.unibo.oop.hearthcode.view.impl;

import java.awt.GridLayout;

import javax.swing.JComponent;

public class CardPanel extends AbstractBackgroundScene {

    private static final String BACKGROUND_PATH = "/images/menu-background.png";
    private static final int ROWS = 2;
    private static final int COLUMNS = 5;
    
    public CardPanel() {
        super(BACKGROUND_PATH);
        this.setLayout(new GridLayout(ROWS, COLUMNS));
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
}
