package co.uk.cpt.proximity_calculator.command_runner;

import co.uk.cpt.proximity_calculator.arguments.ArgumentListener;
import co.uk.cpt.proximity_calculator.arguments.Option;
import co.uk.cpt.proximity_calculator.business.Business;
import co.uk.cpt.proximity_calculator.distance.Distance;
import co.uk.cpt.proximity_calculator.distance_from_origin_finder.DistanceFromOriginFinder;
import co.uk.cpt.proximity_calculator.importer.BusinessImporter;
import co.uk.cpt.proximity_calculator.importer.CsvBusinessRepository;
import co.uk.cpt.proximity_calculator.location.LatLon;
import co.uk.cpt.proximity_calculator.printer.Order;
import co.uk.cpt.proximity_calculator.printer.OrderedLocatableDistancePrinter;
import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static co.uk.cpt.proximity_calculator.arguments.ArgumentListener.DEFAULT_HELPER;

@Component
public class DefaultCommandRunner implements CommandRunner {

    private final ArgumentListener listener;
    private final BusinessImporter<CsvBusinessRepository> importer;
    private final DistanceFromOriginFinder<Business> distanceFinder;
    private final OrderedLocatableDistancePrinter<Business> printer;
    private final CommandChecker<Option> checker;
    private final ArgumentCache<Map<Option, String>> cache;

    @Autowired
    public DefaultCommandRunner(
            ArgumentListener listener,
            BusinessImporter<CsvBusinessRepository> importer,
            DistanceFromOriginFinder<Business> distanceFinder,
            OrderedLocatableDistancePrinter<Business> printer,
            CommandChecker<Option> checker,
            ArgumentCache<Map<Option, String>> cache) {
        this.listener = listener;
        this.importer = importer;
        this.distanceFinder = distanceFinder;
        this.printer = printer;
        this.checker = checker;
        this.cache = cache;
    }

    @Override
    public void run(CommandLine cmd) {
        if (cmd == null)
            throw new IllegalArgumentException("Command line must not be null.");
        if (checker.hasCommand(cmd, Option.SAVE_ARGUMENTS))
            cache.save(cmd);
        else if (checker.hasCommand(cmd, Option.CLEAR_ARGUMENTS))
            cache.clear();
        else if (checker.hasCommand(cmd, Option.PRINT_ARGUMENTS))
            printCommands();
        else
            runCommands(cmd);
    }

    @Override
    public void checkForSingleInstance(CommandLine cmd) {
        if (cmd != null) {
            if (checker.hasCommand(cmd, Option.RUN_ONCE)) {
                runCommands(cmd);
                System.exit(0);
            }
        }
    }

    private void printCommands() {
        System.out.println(Arrays.toString(getSavedArguments()));
    }

    @Override
    public String[] getSavedArguments() {
        String[] defaultsArray = new String[cache.get().size() * 2];
        ArrayList<Map.Entry<Option, String>> entries = new ArrayList<>(cache.get().entrySet());
        for (int i = 0; i < defaultsArray.length; i += 2) {
            defaultsArray[i] = "--" + entries.get(i / 2).getKey().getName();
            defaultsArray[i + 1] = entries.get(i / 2).getValue();
        }
        return defaultsArray;
    }

    private void runCommands(CommandLine cmd) {
        String path = checker.getArgumentValue(cmd, Option.FILE);
        Optional<Business> origin = getOrigin(cmd);
        Distance.Unit unit = getUnit(cmd);
        Order order = getOrder(cmd);

        if (origin.isEmpty()) {
            listener.parseUserInput("Unable to parse lat/lon of business of origin.");
            return;
        }

        File file = new File(path);

        try {
            CsvBusinessRepository repository = new CsvBusinessRepository(file);
            List<Business> businesses = importer.importBusinesses(repository);
            Map<Business, Distance> businessDistanceMap = distanceFinder.find(origin.get(), businesses);
            printer.print(origin.get(), businessDistanceMap, unit, order);
        } catch (IllegalArgumentException e) {
            listener.parseUserInput(e.getMessage() + " " + DEFAULT_HELPER);
        } catch (IOException e) {
            listener.parseUserInput(String.format("Unable to import from file %s %s", path, DEFAULT_HELPER));
        }
    }

    private Distance.Unit getUnit(CommandLine cmd) {
        if (checker.hasCommand(cmd, Option.UNIT)) {
            Optional<Distance.Unit> unit = Distance.Unit.getByShortForm(checker.getArgumentValue(cmd, Option.UNIT));
            if (unit.isPresent())
                return unit.get();
        }

        return Distance.Unit.METRE; // default
    }

    private Order getOrder(CommandLine cmd) {
        if (checker.hasCommand(cmd, Option.ORDER))
            return Order.getByArgument(checker.getArgumentValue(cmd, Option.ORDER));
        else
            return Order.SMALLEST_FIRST;
    }

    private Optional<Business> getOrigin(CommandLine cmd) {
        String business = checker.getArgumentValue(cmd, Option.BUSINESS);
        if (business == null)
            business = "Business";

        String latLonString = checker.getArgumentValue(cmd, Option.LAT_LON);
        if (latLonString == null)
            return Optional.empty();

        Optional<LatLon> latLonOpt = parseLatLon(latLonString);

        String finalBusiness = business;
        return latLonOpt.map(latLon -> new Business(finalBusiness, latLon));
    }

    private Optional<LatLon> parseLatLon(String latLonString) {
        String[] split = latLonString.split(",");
        if (split.length != 2)
            return Optional.empty();

        try {
            double lat = Double.parseDouble(split[0]);
            double lon = Double.parseDouble(split[1]);
            return Optional.of(new LatLon(lat, lon));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

}