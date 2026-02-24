package it.unibo.oop.heartcode.model.file_management.impl;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import it.unibo.oop.heartcode.model.file_management.api.FullReader;

/**
 * An implementation of {@link FullReader} interface that reads all the lines from a text file.
 */
public class TextFileFullReader implements FullReader<String> {

    private static final Logger LOG = Logger.getLogger(TextFileFullReader.class.getName());
    private final Path path;

    /**
     * Constructs a new TextFileFullReader.
     * 
     * @param path the path of the file to be read
     */
    public TextFileFullReader(final Path path) {
        this.path = path;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<String>> readAll() {
        try {
            final List<String> lines = Files.readAllLines(this.path);
            return lines.isEmpty() ? Optional.empty() : Optional.of(lines);
        } catch (final IOException e) {
            LOG.log(Level.SEVERE, "Error while reading from file", e);
            return Optional.empty();
        }
    }
}
