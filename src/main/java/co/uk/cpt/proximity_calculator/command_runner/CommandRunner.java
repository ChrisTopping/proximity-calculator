package co.uk.cpt.proximity_calculator.command_runner;

import org.apache.commons.cli.CommandLine;

public interface CommandRunner {

    void run(CommandLine cmd);

    void checkForSingleInstance(CommandLine cmd);

    String[] getSavedArguments();
}