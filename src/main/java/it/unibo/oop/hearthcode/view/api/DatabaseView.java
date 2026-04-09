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


}
