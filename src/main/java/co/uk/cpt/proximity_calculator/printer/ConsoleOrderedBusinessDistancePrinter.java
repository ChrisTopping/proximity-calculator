package co.uk.cpt.proximity_calculator.printer;

import co.uk.cpt.proximity_calculator.business.Business;
import co.uk.cpt.proximity_calculator.distance.Distance;
import co.uk.cpt.proximity_calculator.distance.Distance.Unit;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ConsoleOrderedBusinessDistancePrinter implements OrderedLocatableDistancePrinter<Business> {

    @Override
    public void print(Business origin, Map<Business, Distance> map, Unit unit) {
        printHeader(origin);
        map.forEach((business, distance) -> printEntry(unit, business, distance));
    }

    @Override
    public void print(Business origin, Map<Business, Distance> map, Unit unit, Order order) {
        List<Map.Entry<Business, Distance>> list = getSmallestToLargest(map);
        if (order.equals(Order.LARGEST_FIRST))
            Collections.reverse(list);

        printHeader(origin);
        list.forEach(entry -> printEntry(unit, entry.getKey(), entry.getValue()));
    }

    private void printHeader(Business origin) {
        System.out.println("Proximity to " + origin.getName());
    }

    private List<Map.Entry<Business, Distance>> getSmallestToLargest(Map<Business, Distance> map) {
        return map.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toList());
    }

    private void printEntry(Unit unit, Business key, Distance value) {
        String precision = "%." + unit.getPrecision() + "f";
        System.out.println(String.format("%s: " + precision + " %s", key.getName(), value.in(unit), unit.getShortForm()));
    }
}