package it.unibo.oop.hearthcode.view.utility;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Utility methods for resolving creature image resource paths.
 */
public final class CreatureImagePaths {

    private static final String CREATURES_PREFIX = "/images/cards/creatures/";
    private static final String DECK_PREFIX = CREATURES_PREFIX + "deck/";
    private static final String IMAGE_SUFFIX = ".png";

    private CreatureImagePaths() {
    }

    /**
     * Returns the main card art path for the given creature name.
     *
     * @param creatureName the database creature name
     * @return the resource path of the card art
     */
    public static String card(final String creatureName) {
        return CREATURES_PREFIX + formatCreatureName(creatureName) + IMAGE_SUFFIX;
    }

    /**
     * Returns the deck-view image path for the given creature name.
     *
     * @param creatureName the database creature name
     * @return the resource path of the deck-view card art
     */
    public static String deck(final String creatureName) {
        return DECK_PREFIX + formatCreatureName(creatureName) + IMAGE_SUFFIX;
    }

    private static String formatCreatureName(final String creatureName) {
        return Arrays.stream(creatureName.split("_"))
            .map(CreatureImagePaths::capitalize)
            .collect(Collectors.joining("_"));
    }

    private static String capitalize(final String token) {
        return Character.toUpperCase(token.charAt(0))
            + token.substring(1).toLowerCase(Locale.ROOT);
    }

}

