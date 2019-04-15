package co.uk.cpt.proximity_calculator.arguments;

import co.uk.cpt.proximity_calculator.arguments.ArgumentValidator.Validity;
import co.uk.cpt.proximity_calculator.arguments.input_retriever.UserInputRetriever;
import co.uk.cpt.proximity_calculator.command_runner.CommandRunner;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class ArgumentListener {

    public final static String DEFAULT_HELPER = "Please enter program arguments or type \"-h\" for help.\n";
    private static final Logger log = Logger.getLogger(ArgumentListener.class.getName());
    private final static String FAILED_PARSE_HELPER = "Could not parse command line arguments. Please enter program arguments or type \"-h\" for help.\n";
    private final ArgumentValidator validator;
    private final UserInputRetriever inputRetriever;
    private final Options options;
    private final HelpFormatter formatter;
    private CommandRunner runner = null;

    @Autowired
    public ArgumentListener(ArgumentValidator validator, UserInputRetriever inputRetriever) {
        if (validator == null)
            throw new IllegalArgumentException("Argument validator must not be null.");
        if (inputRetriever == null)
            throw new IllegalArgumentException("Input retriever must not be null.");
        this.validator = validator;
        this.inputRetriever = inputRetriever;
        options = new Options();
        initializeOptions();
        formatter = new HelpFormatter();
    }

    private void initializeOptions() {
        Option.getAll().forEach(o -> options.addOption(
                org.apache.commons.cli.Option.builder(o.getOption())
                        .longOpt(o.getName())
                        .hasArg(o.hasArgs())
                        .desc(o.getHelpString())
                        .build()));
    }

    public CommandLine listen(CommandRunner runner) {
        this.runner = runner;
        return parseUserInput(DEFAULT_HELPER);
    }

    public CommandLine parseUserInput(String message) {
        String[] userInput = inputRetriever.retrieve(message).split("\\s+");
        String[] savedArguments = getSavedArguments();
        return parse(ArrayUtils.addAll(userInput, savedArguments));
    }

    private String[] getSavedArguments() {
        return runner != null ? runner.getSavedArguments() : new String[]{};
    }

    private CommandLine parse(String[] arguments) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, arguments);
        } catch (ParseException e) {
            return parseUserInput(FAILED_PARSE_HELPER);
        }

        return handleValidity(cmd, validator.validate(cmd));
    }

    public void checkForSingleInstance(CommandRunner runner, String[] arguments) {
        this.runner = runner;
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, arguments);
        } catch (ParseException ignored) {

        }

        Validity validity = validator.validate(cmd);
        switch (validity) {
            case VALID_SINGLE_INSTANCE:
                runner.run(cmd);
                System.exit(0);
            case INVALID_SINGLE_INSTANCE:
                System.out.println(getMissingArgumentsString(cmd));
                System.exit(1);
            case HELP:
                printHelp();
                System.exit(0);
        }
    }

    private CommandLine handleValidity(CommandLine cmd, Validity validity) {
        switch (validity) {
            case INVALID:
                return parseUserInput(DEFAULT_HELPER);
            case VALID_SINGLE_INSTANCE:
                return cmd;
            case INVALID_SINGLE_INSTANCE:
                return null;
            case MISSING_REQUIRED_COMMANDS:
                return retryDueToMissingArguments(cmd);
            case EXIT:
                System.out.println("Goodbye");
                System.exit(0);
            case HELP:
                printHelp();
                return parseUserInput(DEFAULT_HELPER);
            case VALID:
            default:
                return cmd;
        }
    }

    private void printHelp() {
        formatter.printHelp("utility-name", options, false);
    }

    private List<Option> getMissingOptions(CommandLine cmd) {
        return Option.getAll().stream().filter(Option::isRequired).filter(option -> !cmd.hasOption(option.getOption())).collect(Collectors.toList());
    }

    private CommandLine retryDueToMissingArguments(CommandLine cmd) {
        String missingArgumentsString = getMissingArgumentsString(cmd);
        return parseUserInput(missingArgumentsString + " " + DEFAULT_HELPER);
    }

    private String getMissingArgumentsString(CommandLine cmd) {
        StringJoiner joiner = new StringJoiner(", ");
        List<Option> missingOptions = getMissingOptions(cmd);
        missingOptions.forEach(option -> joiner.add(option.getName()));
        return String.format("%s argument%s required.", joiner.toString(), (missingOptions.size() > 1) ? "s are" : " is");
    }
}