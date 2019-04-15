package co.uk.cpt.proximity_calculator.printer;

import org.junit.jupiter.api.Test;

import static co.uk.cpt.proximity_calculator.printer.Order.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderTest {

    @Test
    void givenNullString_whenGetByArgument_returnSmallestFirst() {
        assertEquals(SMALLEST_FIRST, getByArgument(null));
    }

    @Test
    void givenEmptyString_whenGetByArgument_returnSmallestFirst() {
        assertEquals(SMALLEST_FIRST, getByArgument(""));
    }

    @Test
    void givenInvalidString_whenGetByArgument_returnSmallestFirst() {
        assertEquals(SMALLEST_FIRST, getByArgument("invalid"));
    }

    @Test
    void givenSmallestFirst_whenGetByArgument_returnSmallestFirst() {
        assertEquals(SMALLEST_FIRST, getByArgument(SMALLEST_FIRST.getArgument()));
    }

    @Test
    void givenLargestFirst_whenGetByArgument_returnLargestFirst() {
        assertEquals(LARGEST_FIRST, getByArgument(LARGEST_FIRST.getArgument()));
    }

}