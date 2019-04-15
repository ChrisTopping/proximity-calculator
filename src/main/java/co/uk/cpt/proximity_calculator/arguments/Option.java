package co.uk.cpt.proximity_calculator.arguments;

import co.uk.cpt.proximity_calculator.distance.Distance;
import co.uk.cpt.proximity_calculator.printer.Order;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static java.util.Arrays.asList;

public enum Option {
    BUSINESS("business", "b", true, false, "Name of business to which distances will be calculated"),
    LAT_LON("latlon", "l", true, true, "Comma separated lat/lon of business from which distances will be calculated\n[REQUIRED]"),
    FILE("file", "f", true, true, "Business .csv file location \n[REQUIRED]"),
    UNIT("unit", "u", true, false, String.format("Units of distance %s \n%s", getUnitOptionsString(), "[DEFAULT = metre]")),
    ORDER("order", "o", true, false, String.format("Sort order of distance %s \n%s", getOrderOptionsString(), "[DEFAULT = smallest to largest]")),

    HELP("help", "h", false, false, "Help"),
    EXIT("exit", "x", false, false, "Exit"),
    SAVE_ARGUMENTS("save", "s", false, false, "Save the given arguments so that they do not need to be repeated in subsequent commands"),
    CLEAR_ARGUMENTS("clear", "c", false, false, "Clear saved arguments"),
    PRINT_ARGUMENTS("arguments", "a", false, false, "Show saved arguments"),
    RUN_ONCE("singleinstance", "i", false, false, "Run a single instance"),
    ;

    private final String name;
    private final String option;
    private final boolean hasArgs;
    private final boolean required;
    private final String helpString;

    Option(String name, String option, boolean hasArgs, boolean required, String helpString) {
        this.name = name;
        this.option = option;
        this.hasArgs = hasArgs;
        this.required = required;
        this.helpString = helpString;
    }

    public static List<? extends Option> getAll() {
        return asList(Option.values());
    }

    private static String getUnitOptionsString() {
        StringJoiner joiner = new StringJoiner("\n- ");
        Arrays.stream(Distance.Unit.values()).forEach(unit -> joiner.add(unit.getShortForm() + ": " + unit.getLongForm()));
        return "\n- " + joiner.toString();
    }

    private static String getOrderOptionsString() {
        StringJoiner joiner = new StringJoiner("\n- ");
        Arrays.stream(Order.values()).forEach(order -> joiner.add(order.getArgument() + ": " + order.getDescription()));
        return "\n- " + joiner.toString();
    }

    public String getName() {
        return name;
    }

    public String getOption() {
        return option;
    }

    public boolean hasArgs() {
        return hasArgs;
    }

    public boolean isRequired() {
        return required;
    }

    public String getHelpString() {
        return helpString;
    }

}