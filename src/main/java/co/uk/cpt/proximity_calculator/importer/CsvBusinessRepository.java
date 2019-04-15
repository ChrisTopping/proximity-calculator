package co.uk.cpt.proximity_calculator.importer;

import java.io.File;

public class CsvBusinessRepository implements BusinessRepository<File> {

    private final File location;

    public CsvBusinessRepository(File location) {
        if (location == null)
            throw new IllegalArgumentException(".csv file must be provided.");
        if (!location.isFile())
            throw new IllegalArgumentException("File location is invalid.");
        if (!location.getName().endsWith(".csv") && !location.getName().endsWith("!.CSV"))
            throw new IllegalArgumentException("File must have .csv extension.");
        if (!location.canRead()) throw new IllegalArgumentException("Cannot read .csv file.");

        this.location = location;
    }

    @Override
    public File get() {
        return location;
    }

}