package it.unibo.oop.hearthcode.view.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

/**
 * Central catalog of image preload plans.
 */
public final class ImagePreloadCatalog {

    private static final String CREATURES_RESOURCE = "/creatures.txt";
    private static final String CREATURE_IMAGE_PREFIX = "/images/cards/creatures/";
    private static final String CREATURE_IMAGE_SUFFIX = ".png";

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
            button("/images/play-normal.png"),
            button("/images/play-hover.png"),
            button("/images/play-pressed.png"),
            button("/images/settings-normal.png"),
            button("/images/settings-hover.png"),
            button("/images/settings-pressed.png"),
            button("/images/quit-normal.png"),
            button("/images/quit-hover.png"),
            button("/images/quit-pressed.png"),
            button("/images/back-normal.png"),
            button("/images/back-hover.png"),
            button("/images/back-pressed.png"),
            button("/images/menu-normal.png"),
            button("/images/menu-hover.png"),
            button("/images/menu-pressed.png")
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
            creatureCardRequests()
        ).toList();
    }

    private static ImageLoadRequest button(final String path) {
        return ImageLoadRequest.scaled(path, ViewMetrics.menuButtonWidth(), ViewMetrics.menuButtonHeight());
    }

    /**
     * Builds scaled preload requests for all creature card images listed in the creature catalog.
     * 
     * @return a stream of creature card preload requests
     */
    private static Stream<ImageLoadRequest> creatureCardRequests() {
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
                .map(name -> ImageLoadRequest.scaled(
                    CREATURE_IMAGE_PREFIX + name + CREATURE_IMAGE_SUFFIX,
                    ViewMetrics.cardWidth(),
                    ViewMetrics.cardHeight()
                ))
                .toList()
                .stream();
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
