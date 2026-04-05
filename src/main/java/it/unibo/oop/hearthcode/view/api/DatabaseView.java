package it.unibo.oop.hearthcode.view.api;

import javax.swing.JPanel;

import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;

/**
 * View contract for the database Scene.
 */
public interface DatabaseView extends Scene  {

    /**
     * Binds the back action.
     */
    void onBack(Runnable action);

    /**
     * It creates a panel with the card and its attributes.
     * 
     * @param def the creature to be shown in the Panel
     * @return a Jpanel with the card icon and its attributes
     */
    JPanel createCardPanel(CreatureDefinition def);
}
