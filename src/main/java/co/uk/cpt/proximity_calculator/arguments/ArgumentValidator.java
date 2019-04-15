package co.uk.cpt.proximity_calculator.arguments;

import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

import static co.uk.cpt.proximity_calculator.arguments.ArgumentValidator.Validity.*;

@Component
public class ArgumentValidator {

    public Validity validate(CommandLine cmd) {
        if (cmd == null) {
            return INVALID;
        } else if (hasOption(cmd, Option.RUN_ONCE) && doesCommandHaveAllRequiredOptions(cmd)) {
            return VALID_SINGLE_INSTANCE;
        } else if (hasOption(cmd, Option.RUN_ONCE)) {
            return INVALID_SINGLE_INSTANCE;
        } else if (hasOption(cmd, Option.SAVE_ARGUMENTS) || hasOption(cmd, Option.CLEAR_ARGUMENTS) || hasOption(cmd, Option.PRINT_ARGUMENTS)) {
            return VALID;
        } else if (hasOption(cmd, Option.EXIT)) {
            return EXIT;
        } else if (hasOption(cmd, Option.HELP)) {
            return HELP;
        } else if (!doesCommandHaveAllRequiredOptions(cmd)) {
            return MISSING_REQUIRED_COMMANDS;
        }
        return VALID;
    }

    private boolean hasOption(CommandLine cmd, Option option) {
        return cmd.hasOption(option.getOption());
    }

    private boolean doesCommandHaveAllRequiredOptions(CommandLine cmd) {
        return Option.getAll().stream().filter(Option::isRequired).allMatch(option -> cmd.hasOption(option.getOption()));
    }

    public enum Validity {
        INVALID,
        VALID_SINGLE_INSTANCE,
        INVALID_SINGLE_INSTANCE,
        VALID,
        EXIT,
        HELP,
        MISSING_REQUIRED_COMMANDS,
        ;
    }
}