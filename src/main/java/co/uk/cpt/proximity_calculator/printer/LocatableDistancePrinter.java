package co.uk.cpt.proximity_calculator.printer;

import co.uk.cpt.proximity_calculator.business.Locatable;
import co.uk.cpt.proximity_calculator.distance.Distance;

import java.util.Map;

public interface LocatableDistancePrinter<T extends Locatable> {

    void print(T origin, Map<T, Distance> map, Distance.Unit unit);

}