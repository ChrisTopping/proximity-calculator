package co.uk.cpt.proximity_calculator.importer;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvBusinessRepositoryTest {

    @Test
    void givenLocationIsNull_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new CsvBusinessRepository(null));
    }

    @Test
    void givenLocationIsNotFile_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new CsvBusinessRepository(new File("not a file")));
    }

    @Test
    void givenFileHasIncorrectExtension_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new CsvBusinessRepository(new File("application.properties")));
    }
}