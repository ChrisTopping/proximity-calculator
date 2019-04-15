package co.uk.cpt.proximity_calculator.command_runner;

import org.apache.commons.cli.CommandLine;

public interface CommandChecker<T> {

    boolean hasCommand(CommandLine cmd, T command);

    String getArgumentValue(CommandLine cmd, T command);

}