package it.unibo.oop.hearthcode.view.api;

import it.unibo.oop.hearthcode.model.player.api.PlayerId;

public interface PlayerArea {

    PlayerId getPlayerId();

    CardArea getHandArea();

    CardArea getArmyArea();

    void initHealth(int maxHealth);

    void setCurrentHealth(int currentHealth);

    void setMana(int currentMana, int maxMana);

}