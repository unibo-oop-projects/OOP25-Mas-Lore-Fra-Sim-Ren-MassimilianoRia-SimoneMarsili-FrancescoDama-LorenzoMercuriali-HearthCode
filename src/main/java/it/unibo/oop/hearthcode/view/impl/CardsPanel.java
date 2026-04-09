package it.unibo.oop.hearthcode.view.impl;

import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import it.unibo.oop.hearthcode.model.creature.api.CreatureDefinition;
import it.unibo.oop.hearthcode.model.database.impl.CreatureDatabaseFactory;
import it.unibo.oop.hearthcode.view.utility.ImageLoader;
import it.unibo.oop.hearthcode.view.utility.ViewMetrics;

public class CardsPanel extends AbstractBackgroundScene {

    private static final String BACKGROUND_PATH = "/images/menu-background.png";
    private static final String CREATURE_IMAGES_PATH = "src/main/resources/images/cards/creatures";
    private static final int ROWS = 2;
    private static final int COLUMNS = 5;
    List<CreatureDefinition> definitions;
    List<ImageIcon> images;

    
    public CardsPanel() {
        super(BACKGROUND_PATH);
        this.definitions = new ArrayList<>(CreatureDatabaseFactory.createFromFile("creatures.txt").getAll());
        final Path folder = Paths.get(CREATURE_IMAGES_PATH);
        try {
            List<Path> iconsPaths = Files.list(folder)
                    .filter(Files::isRegularFile)
                    .filter(p -> {
                        String nome = p.toString().toLowerCase();
                        return nome.endsWith(".jpg") ||
                               nome.endsWith(".jpeg") ||
                               nome.endsWith(".png") ||
                               nome.endsWith(".gif");
                    })
                    .collect(Collectors.toList());
            iconsPaths.stream().forEach(i -> this.images.add(ImageLoader.load(i.toString(), ViewMetrics.cardWidth(), ViewMetrics.cardHeight())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(this.definitions, (a, b) -> a.name().compareTo(b.name()));
        this.setLayout(new GridLayout(ROWS, COLUMNS));
        this.initializeLayout();
        
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    private void initializeLayout() {
        IntStream.range(0, this.definitions.size())
            .forEach(i -> this.add(this.images.get(i));
    }
}
