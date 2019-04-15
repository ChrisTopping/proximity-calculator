package co.uk.cpt.proximity_calculator.importer;

import co.uk.cpt.proximity_calculator.business.Business;
import co.uk.cpt.proximity_calculator.location.LatLon;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CsvBusinessImporter implements BusinessImporter<BusinessRepository<File>> {

    private static final String DOUBLE_PRECISION_REGEX = "^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$";

    @Override
    public List<Business> importBusinesses(BusinessRepository<File> repository) throws IOException {
        return Files.lines(repository.get().toPath())
                .map(String::trim) // trim whitespace
                .filter(s -> !s.isEmpty()).filter(s -> !s.isBlank()) // remove empty lines
                .map(s -> s.split(",\\s*")) // split each comma separated line
                .filter(strings -> strings.length == 3) // remove any lines without exactly 3 variables
                .map(this::getBusinessFromStrings) // convert each remaining line into a business
                .filter(Optional::isPresent) // remove lines which couldn't be converted due to unparsable lat/lon values
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<Business> getBusinessFromStrings(String[] strings) {
        if (!isDouble(strings[1]) || !isDouble(strings[2]))
            return Optional.empty();

        try {
            return Optional.of(new Business(strings[0], new LatLon(Double.parseDouble(strings[1]), Double.parseDouble(strings[2]))));
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }

    private boolean isDouble(String string) {
        return string.matches(DOUBLE_PRECISION_REGEX);
    }

}