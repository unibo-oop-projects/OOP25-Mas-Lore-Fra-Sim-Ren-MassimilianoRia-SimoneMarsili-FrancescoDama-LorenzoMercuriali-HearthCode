package it.unibo.oop.hearthcode.model.player.api;

import java.io.Serializable;

/**
 * Identifies the player.
 * 
 * @param type the {@link PlayerType} of the player
 */
public record PlayerId(PlayerType type) implements Serializable {

    public static final PlayerId HUMAN = new PlayerId(PlayerType.HUMAN_PLAYER);

    public static final PlayerId AI = new PlayerId(PlayerType.AI_PLAYER);

    private static final long serialVersionUID = 1L;

    /**
     * Factory method returning canonical instances.
     *
     * @param type the player type
     * @return the corresponding canonical {@link PlayerId}
     */
    public static PlayerId of(final PlayerType type) {
        return switch (type) {
            case HUMAN_PLAYER -> HUMAN;
            case AI_PLAYER -> AI;
        };
    }

}
