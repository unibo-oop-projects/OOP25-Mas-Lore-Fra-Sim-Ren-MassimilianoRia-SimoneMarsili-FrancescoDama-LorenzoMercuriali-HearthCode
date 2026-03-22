package it.unibo.oop.hearthcode.view.impl;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.unibo.oop.hearthcode.model.creature.api.CardId;
import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.view.api.CardComponent;

public final class CardComponentImpl extends JButton implements CardComponent {

    private static final long serialVersionUID = 1L;

    private final CardId cardId;

    private final String cardName;
    private final int manaCost;
    private int attack;
    private int health;

    public CardComponentImpl(
        final CardId cardId,
        final CreatureDefinition def,
        final ImageIcon icon
    ) {
        this.cardId = cardId;
        this.cardName = def.name();
        this.health = def.health();
        this.attack = def.attackValue();
        this.manaCost = def.manaCost();

        this.setFocusPainted(false);
        this.setVerticalTextPosition(BOTTOM);
        this.setHorizontalTextPosition(CENTER);
        this.setIcon(icon);

        this.refreshText();
    }

    private void refreshText() {
        this.setText(
            "<html><center>"
                + this.cardName
                + "<br>Mana: " + this.manaCost
                + "<br>Atk: " + this.attack + " | Hp: " + this.health
                + "</center></html>"
        );
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

}
