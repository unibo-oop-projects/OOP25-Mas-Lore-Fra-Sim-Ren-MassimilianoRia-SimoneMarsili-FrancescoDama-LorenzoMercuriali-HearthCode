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
            endBanner("/images/you-lost.png"),
            endBanner("/images/you-won.png"),
            button("/images/play-normal.png"),
            button("/images/play-hover.png"),
            button("/images/play-pressed.png"),
            button("/images/deck-normal.png"),
            button("/images/deck-hover.png"),
            button("/images/deck-pressed.png"),
            button("/images/quit-normal.png"),
            button("/images/quit-hover.png"),
            button("/images/quit-pressed.png"),
            button("/images/back-normal.png"),
            button("/images/back-hover.png"),
            button("/images/back-pressed.png"),
            button("/images/menu-normal.png"),
            button("/images/menu-hover.png"),
            button("/images/menu-pressed.png"),
            button("/images/normal-normal.png"),
            button("/images/normal-hover.png"),
            button("/images/normal-pressed.png"),
            button("/images/hard-normal.png"),
            button("/images/hard-hover.png"),
            button("/images/hard-pressed.png")
        );
    }

    /**
     * Builds the preload plan for the match scene and card textures.
     * 
     * @return the preload plan for the match
     */
    public static List<ImageLoadRequest> match() {
        return Stream.concat(
            Stream.of(ImageLoadRequest.scaled(
                "/images/cards/utility/card_cover.png",
                ViewMetrics.cardWidth(),
                ViewMetrics.cardHeight()
            )),
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

    private static ImageLoadRequest button(final String path) {
        return ImageLoadRequest.scaled(path, ViewMetrics.menuButtonWidth(), ViewMetrics.menuButtonHeight());
    }

    private static ImageLoadRequest endBanner(final String path) {
        return ImageLoadRequest.scaled(path, ViewMetrics.viewportWidth(), ViewMetrics.endBannerHeight());
    }

    /**
     * Builds scaled preload requests for all creature card images listed in the creature catalog.
     *
     * @param pathResolver resolves each creature name to its image path
     * @return a stream of creature card preload requests
     */
    private static Stream<ImageLoadRequest> creatureCardRequests(final Function<String, String> pathResolver) {
        return CREATURE_NAMES.stream()
            .map(pathResolver)
            .map(path -> ImageLoadRequest.scaled(
                path,
                ViewMetrics.cardWidth(),
                ViewMetrics.cardHeight()
            ));
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
