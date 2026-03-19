package it.unibo.oop.hearthcode.model.file_management.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.unibo.oop.hearthcode.model.file_management.api.FullReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * An implementation of the {@link FullReader} interface that reads all lines from a text file stream.
 */
public class TextFileFullReader implements FullReader<String> {

    private static final Logger LOG = Logger.getLogger(TextFileFullReader.class.getName());
    private final InputStream stream;

    /**
     * Constructs a new TextFileFullReader.
     * 
     * @param stream the stream to read from
     */
    public TextFileFullReader(final InputStream stream) {
        this.stream = stream;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<String>> readAll() {
        try (BufferedReader reader = 
                new BufferedReader(new InputStreamReader(this.stream, StandardCharsets.UTF_8));
        ) {
            final List<String> lines = reader.lines().toList();
            return lines.isEmpty() ? Optional.empty() : Optional.of(lines);
        } catch (final IOException e) {
            LOG.log(Level.SEVERE, "Error while reading from file", e);
            return Optional.empty();
        }
    }
}
