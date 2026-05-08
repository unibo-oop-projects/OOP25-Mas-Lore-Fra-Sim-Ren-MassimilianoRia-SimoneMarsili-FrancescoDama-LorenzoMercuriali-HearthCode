package it.unibo.oop.hearthcode.view.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Central catalog of image preload plans.
 */
public final class ImagePreloadCatalog {

    private static final String CREATURES_RESOURCE = "/creatures.txt";
    private static final List<String> CREATURE_NAMES = loadCreatureNames();

    private ImagePreloadCatalog() {
    }

    /**
     * Builds the preload plan for menu and shared navigation scenes.
     * 
     * @return the preload plan for the menu
     */
    public static List<ImageLoadRequest> menuAndNavigation() {
        return List.of(
            ImageLoadRequest.raw("/images/menu-background.png"),
            ImageLoadRequest.raw("/images/you-lost.png"),
            ImageLoadRequest.raw("/images/you-won.png"),
            ImageLoadRequest.raw("/images/play-normal.png"),
            ImageLoadRequest.raw("/images/play-hover.png"),
            ImageLoadRequest.raw("/images/play-pressed.png"),
            ImageLoadRequest.raw("/images/deck-normal.png"),
            ImageLoadRequest.raw("/images/deck-hover.png"),
            ImageLoadRequest.raw("/images/deck-pressed.png"),
            ImageLoadRequest.raw("/images/quit-normal.png"),
            ImageLoadRequest.raw("/images/quit-hover.png"),
            ImageLoadRequest.raw("/images/quit-pressed.png"),
            ImageLoadRequest.raw("/images/back-normal.png"),
            ImageLoadRequest.raw("/images/back-hover.png"),
            ImageLoadRequest.raw("/images/back-pressed.png"),
            ImageLoadRequest.raw("/images/menu-normal.png"),
            ImageLoadRequest.raw("/images/menu-hover.png"),
            ImageLoadRequest.raw("/images/menu-pressed.png"),
            ImageLoadRequest.raw("/images/normal-normal.png"),
            ImageLoadRequest.raw("/images/normal-hover.png"),
            ImageLoadRequest.raw("/images/normal-pressed.png"),
            ImageLoadRequest.raw("/images/hard-normal.png"),
            ImageLoadRequest.raw("/images/hard-hover.png"),
            ImageLoadRequest.raw("/images/hard-pressed.png")
        );
    }

    /**
     * Builds the preload plan for the match scene and card textures.
     * 
     * @return the preload plan for the match
     */
    public static List<ImageLoadRequest> match() {
        return Stream.concat(
            Stream.of(ImageLoadRequest.raw("/images/cards/utility/card_cover.png")),
            creatureCardRequests(CreatureImagePaths::card)
        ).toList();
    }

    /**
     * Builds the preload plan for the deck preview scene.
     *
     * @return the preload plan for the deck preview
     */
    public static List<ImageLoadRequest> database() {
        return creatureCardRequests(CreatureImagePaths::deck).toList();
    }

    /**
     * Builds preload requests for all creature card images listed in the creature catalog.
     *
     * @param pathResolver resolves each creature name to its image path
     * @return a stream of creature card preload requests
     */
    private static Stream<ImageLoadRequest> creatureCardRequests(final Function<String, String> pathResolver) {
        return CREATURE_NAMES.stream()
            .map(pathResolver)
            .map(ImageLoadRequest::raw);
    }

    private static List<String> loadCreatureNames() {
        final var resource = ImagePreloadCatalog.class.getResourceAsStream(CREATURES_RESOURCE);
        if (resource == null) {
            throw new IllegalStateException(
                "Missing resource for image preload: " + CREATURES_RESOURCE
            );
        }
        try (
            resource;
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource, StandardCharsets.UTF_8)
            )
        ) {
            return reader.lines()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(ImagePreloadCatalog::extractCreatureName)
                .toList();
        } catch (final IOException exception) {
            throw new IllegalStateException("Unable to read creature image catalog",
                exception
            );
        }
    }

    /**
     * Extracts the creature name from a catalog line.
     * 
     * @param line the catalog line to parse
     * @return the extracted creature name
     */
    private static String extractCreatureName(final String line) {
        final int separatorIndex = line.indexOf(',');
        if (separatorIndex <= 0) {
            throw new IllegalArgumentException("Invalid creature line for preload: " + line);
        }
        return line.substring(0, separatorIndex);
    }

}
