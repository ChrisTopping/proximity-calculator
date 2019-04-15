package co.uk.cpt.proximity_calculator.distance;

import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.Optional;

@EqualsAndHashCode
public class Distance implements Comparable<Distance> {

    private final double distanceInMetres;

    public Distance(double distance, Unit unit) {
        if (unit == null)
            throw new IllegalArgumentException("No unit of length provided for distance.");
        this.distanceInMetres = distance * unit.getToMetres();
    }

    public static Distance ofZero() {
        return new Distance(0, Unit.METRE);
    }

    public double in(Unit unit) {
        return distanceInMetres * unit.getFromMetres();
    }

    @Override
    public int compareTo(Distance o) {
        return Double.compare(distanceInMetres, o.distanceInMetres);
    }

    public enum Unit {
        METRE(1D, "metres", "m", 0),
        KILOMETRE(1000D, "kilometres", "km", 2),
        MILE(1609.344D, "miles", "mi", 2),
        ;

        // number of metres in this unit
        private final double toMetres;
        private final String longForm;
        private final String shortForm;
        private final int precision;

        Unit(double toMetres, String longForm, String shortForm, int precision) {
            this.toMetres = toMetres;
            this.longForm = longForm;
            this.shortForm = shortForm;
            this.precision = precision;
        }

        public static Optional<Unit> getByShortForm(String shortForm) {
            return Arrays.stream(values()).filter(unit -> unit.shortForm.equals(shortForm)).findAny();
        }

        public double getToMetres() {
            return toMetres;
        }

        public double getFromMetres() {
            return 1 / toMetres;
        }

        public String getLongForm() {
            return longForm;
        }

        public String getShortForm() {
            return shortForm;
        }

        public int getPrecision() {
            return precision;
        }
    }
}