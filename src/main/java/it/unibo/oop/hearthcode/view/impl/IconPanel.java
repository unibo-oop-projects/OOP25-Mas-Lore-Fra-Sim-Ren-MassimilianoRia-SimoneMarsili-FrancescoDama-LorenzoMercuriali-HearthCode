package it.unibo.oop.hearthcode.view.impl;

import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Lightweight panel used to display a single card image.
 */
public final class IconPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Builds the panel hosting the provided icon.
     *
     * @param icon the icon to display
     */
    public IconPanel(final ImageIcon icon) {
        this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        this.initializeLayout(icon);
    }

    private void initializeLayout(final ImageIcon icon) {
        this.add(new JLabel(icon));
    }

}

