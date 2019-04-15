package co.uk.cpt.proximity_calculator.printer;

import java.util.Arrays;
import java.util.Optional;

public enum Order {
    LARGEST_FIRST("lg", "largest to smallest"),
    SMALLEST_FIRST("sm", "smallest to largest"),
    ;

    private final String argument;
    private final String description;

    Order(String argument, String description) {
        this.argument = argument;
        this.description = description;
    }

    public static Order getByArgument(String argument) {
        Optional<Order> matching = Arrays.stream(Order.values()).filter(order -> order.argument.equals(argument)).findAny();
        return matching.orElse(SMALLEST_FIRST);
    }

    public String getArgument() {
        return argument;
    }

    public String getDescription() {
        return description;
    }

}