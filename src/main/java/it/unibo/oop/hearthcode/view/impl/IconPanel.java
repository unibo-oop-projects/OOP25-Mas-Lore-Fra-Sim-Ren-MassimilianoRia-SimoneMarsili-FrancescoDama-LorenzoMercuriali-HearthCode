package it.unibo.oop.hearthcode.view.impl;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IconPanel extends JPanel {
    
    public IconPanel(final ImageIcon icon) {
        final JLabel creatureIcon = new JLabel(icon);
        this.add(creatureIcon);
    }
}
