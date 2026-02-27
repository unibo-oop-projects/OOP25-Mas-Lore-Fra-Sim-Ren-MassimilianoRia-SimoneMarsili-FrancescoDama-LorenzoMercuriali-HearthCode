package it.unibo.oop.hearthcode.model.file_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.oop.hearthcode.model.file_management.impl.TextFileFullReader;

/**
 * A simple test for {@link TextFileFullReader}.
 */
final class TestTextFileFullReader {

    private static final String TEST_FILE = "creatures.txt";
    private static final int EXPECTED_N_LINES = 10;
    private TextFileFullReader reader;

    @BeforeEach
    void initTest() {
        final var stream = ClassLoader.getSystemResourceAsStream(TEST_FILE);
        this.reader = new TextFileFullReader(stream);
    }

    @Test
    void testValidRead() {
        final var extract = this.reader.readAll();
        assertFalse(extract.isEmpty());
        assertEquals(EXPECTED_N_LINES, extract.get().size());
    }

}
