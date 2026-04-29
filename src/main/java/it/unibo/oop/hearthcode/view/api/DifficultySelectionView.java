package it.unibo.oop.hearthcode.view.api;

/**
 * View contract for the difficulty selection scene.
 */
public interface DifficultySelectionView {

    /**
     * Binds the normal difficulty action.
     *
     * @param action the action to execute
     */
    void onNormal(Runnable action);

    /**
     * Binds the hard difficulty action.
     *
     * @param action the action to execute
     */
    void onHard(Runnable action);

    /**
     * Binds the back action.
     *
     * @param action the action to execute
     */
    void onBack(Runnable action);

}
