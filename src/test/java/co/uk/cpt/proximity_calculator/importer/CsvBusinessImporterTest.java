package co.uk.cpt.proximity_calculator.importer;

import co.uk.cpt.proximity_calculator.business.Business;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvBusinessImporterTest {

    @Test
    void shouldReturnAllBusinesses() throws IOException {
        CsvBusinessImporter importer = new CsvBusinessImporter();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("location_list.csv")).getFile());

        Collection<Business> businesses = importer.importBusinesses(new CsvBusinessRepository(file));

        StringJoiner joiner = new StringJoiner("\n");
        businesses.forEach(business -> joiner.add(business.toString()));

        String allBusinesses = "Alex's Apartment (55.93560, -4.51627)\n" +
                "Bob's Builders (53.42097, -3.02944)\n" +
                "Claire's Crib (53.76611, -2.60247)\n" +
                "Dave's Domicile (53.56859, -3.04843)\n" +
                "Eric's Electricals (53.19821, -2.81754)\n" +
                "Greta's Gaff (53.39329, -2.14722)\n" +
                "Hannah's Home (53.38424, -3.01505)\n" +
                "Irene's Imaging (53.55660, -2.31622)\n" +
                "John's Joint (53.72512, -1.86034)\n" +
                "Kathy's Kennels (53.76610, -2.60248)";
        assertEquals(allBusinesses, joiner.toString());
    }
}