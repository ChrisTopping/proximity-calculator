package co.uk.cpt.proximity_calculator.importer;

import co.uk.cpt.proximity_calculator.business.Business;

import java.io.IOException;
import java.util.List;

public interface BusinessImporter<T extends BusinessRepository> {

    List<Business> importBusinesses(T repository) throws IOException;

}