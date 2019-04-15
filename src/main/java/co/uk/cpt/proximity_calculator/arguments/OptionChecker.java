package co.uk.cpt.proximity_calculator.arguments;

import co.uk.cpt.proximity_calculator.command_runner.CommandChecker;
import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

@Component
public class OptionChecker implements CommandChecker<Option> {

    @Override
    public boolean hasCommand(CommandLine cmd, Option option) {
        return cmd.hasOption(option.getOption());
    }

    @Override
    public String getArgumentValue(CommandLine cmd, Option option) {
        return cmd.getOptionValue(option.getOption());
    }

}