package it.unibo.oop.hearthcode.view.impl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Panel that keeps its only child centered with a fixed aspect ratio.
 */
public final class BlackBounds extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a black letterbox panel.
     */
    public BlackBounds() {
        super(null);
        this.setBackground(Color.BLACK);
        this.setOpaque(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doLayout() {
        final Component child = this.getComponent(0);
        final Dimension viewport = ViewMetrics.viewportSize(this.getSize());
        final int x = (this.getWidth() - viewport.width) / 2;
        final int y = (this.getHeight() - viewport.height) / 2;
        child.setBounds(x, y, viewport.width, viewport.height);
    }

}
