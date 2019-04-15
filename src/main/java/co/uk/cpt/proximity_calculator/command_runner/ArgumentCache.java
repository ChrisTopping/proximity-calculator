package co.uk.cpt.proximity_calculator.command_runner;

import org.apache.commons.cli.CommandLine;

interface ArgumentCache<T> {

    void save(CommandLine cmd);

    void clear();

    T get();

}