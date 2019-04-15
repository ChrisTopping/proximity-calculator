package co.uk.cpt.proximity_calculator.printer;

import co.uk.cpt.proximity_calculator.business.Locatable;
import co.uk.cpt.proximity_calculator.distance.Distance;

import java.util.Map;

public interface OrderedLocatableDistancePrinter<T extends Locatable> extends LocatableDistancePrinter<T> {

    void print(T origin, Map<T, Distance> map, Distance.Unit unit, Order order);

}