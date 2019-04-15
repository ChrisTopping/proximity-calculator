package co.uk.cpt.proximity_calculator.business;

import co.uk.cpt.proximity_calculator.location.LatLon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BusinessTest {

    @Test
    void givenNoName_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Business(null, new LatLon(0,0)));
    }

    @Test
    void givenNoLatLon_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Business("Fleetsmart", null));
    }

    @Test
    void toStringTest() {
        Business business = new Business("Business", new LatLon(1, 2));
        assertEquals("Business (1.00000, 2.00000)", business.toString());
    }

}