package it.unibo.oop.hearthcode.view.api;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Factory for the reusable UI components of the match scene.
 */
public interface MatchSceneUiFactory {

    /**
     * Creates a styled action button for the match scene.
     *
     * @param text the button label
     * @param background the default background color
     * @param hoverBackground the background color shown on hover
     * @return the configured action button
     */
    JButton createActionButton(String text, Color background, Color hoverBackground);

    /**
     * Creates a transparent panel for composing match scene areas.
     *
     * @return the created panel
     */
    JPanel createPanel();

    /**
     * Creates the panel containing the action buttons of the match scene.
     *
     * @param attackHeroButton the attack-hero button
     * @param attackCreatureButton the attack-creature button
     * @param placeCardButton the place-card button
     * @param endTurnButton the end-turn button
     * @param exitButton the exit button
     * @return the configured action panel
     */
    JComponent createActionPanel(
        JButton attackHeroButton,
        JButton attackCreatureButton,
        JButton placeCardButton,
        JButton endTurnButton,
        JButton exitButton
    );

}
