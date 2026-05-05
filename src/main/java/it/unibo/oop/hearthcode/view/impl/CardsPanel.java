package it.unibo.oop.hearthcode.view.impl;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabase;
import it.unibo.oop.hearthcode.view.utility.CreatureImagePaths;
import it.unibo.oop.hearthcode.view.utility.ImageLoader;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

/**
 * Grid panel showing all creature cards available in the deck view.
 */
public final class CardsPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int ROWS = 2;
    private static final int COLUMNS = 5;
    private static final int PANEL_PADDING_FACTOR = 4;
    private static final int HORIZONTAL_GAP_FACTOR = 18;
    private static final int VERTICAL_GAP_FACTOR = 3;

    /**
     * Builds the deck cards panel.
     * 
     * @param definitions the creatures to use
     */
    public CardsPanel(final CreatureDatabase definitions) {
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(
            ViewMetrics.outerPadding() * PANEL_PADDING_FACTOR,
            ViewMetrics.outerPadding() * PANEL_PADDING_FACTOR,
            ViewMetrics.outerPadding() * PANEL_PADDING_FACTOR,
            ViewMetrics.outerPadding() * PANEL_PADDING_FACTOR
        ));
        this.setLayout(new GridLayout(
            ROWS,
            COLUMNS,
            ViewMetrics.horizontalGap() * HORIZONTAL_GAP_FACTOR,
            ViewMetrics.verticalGap() * VERTICAL_GAP_FACTOR
        ));
        this.initializeLayout(definitions);
    }

    private void initializeLayout(final CreatureDatabase definitions) {
        definitions.getAll().forEach(def -> this.add(new IconPanel(ImageLoader.load(
            CreatureImagePaths.deck(def.name()),
            ViewMetrics.cardWidth(),
            ViewMetrics.cardHeight()
        ))));
    }

}
