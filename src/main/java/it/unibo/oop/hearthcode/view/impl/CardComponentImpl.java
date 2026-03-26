package it.unibo.oop.hearthcode.view.impl;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.view.api.CardComponent;

/**
 * Implementation of {@link CardComponent}.
 */
public final class CardComponentImpl extends JButton implements CardComponent {

    private static final long serialVersionUID = 1L;

    private final transient CardId cardId;

    private final String cardName;
    private final int manaCost;
    private final int attack;
    private int health;
    private final ImageIcon frontIcon;
    private final ImageIcon backIcon;
    private boolean isFaceUp;

    /**
     * Builds the component representing a specific card.
     *
     * @param cardId the identifier of the represented card
     * @param def the card definition
     * @param frontIcon the front card image
     * @param backIcon the back card image
     */
    public CardComponentImpl(
        final CardId cardId,
        final CreatureDefinition def,
        final ImageIcon frontIcon,
        final ImageIcon backIcon
    ) {
        this.cardId = cardId;
        this.cardName = def.name();
        this.health = def.health();
        this.attack = def.attackValue();
        this.manaCost = def.manaCost();
        this.frontIcon = new ImageIcon(frontIcon.getImage());
        this.backIcon = new ImageIcon(backIcon.getImage());

        this.setFocusPainted(false);
        this.setVerticalTextPosition(BOTTOM);
        this.setHorizontalTextPosition(CENTER);

        this.refreshText();
    }

    private void refreshText() {
        if (this.isFaceUp) {
            this.setIcon(frontIcon);
            this.setText(
                "<html><center>"
                    + this.cardName
                    + "<br>Mana: " + this.manaCost
                    + "<br>Atk: " + this.attack + " | Hp: " + this.health
                    + "</center></html>"
            );
        } else {
            this.setIcon(backIcon);
            this.setText("");
        }
    }

    @Override
    public CardId getCardId() {
        return this.cardId;
    }

    @Override
    public void setHealth(final int newHealth) {
        this.health = newHealth;
        this.refreshText();
    }

    @Override
    public JButton getComponent() {
        return this;
    }

    @Override
    public void setFaceUp(final boolean faceUp) {
        this.isFaceUp = faceUp;
        this.refreshText();
    }
}
