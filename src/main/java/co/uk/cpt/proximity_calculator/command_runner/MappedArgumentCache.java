package co.uk.cpt.proximity_calculator.command_runner;

import co.uk.cpt.proximity_calculator.arguments.Option;
import co.uk.cpt.proximity_calculator.arguments.OptionChecker;
import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class MappedArgumentCache<T> implements ArgumentCache<Map<Option, String>> {

    private final OptionChecker checker;

    private final Map<Option, String> savedArguments = new HashMap<>();

    @Autowired
    public MappedArgumentCache(OptionChecker checker) {
        this.checker = checker;
    }

    @Override
    public void save(CommandLine cmd) {
        Arrays.stream(Option.values())
                .filter(option -> !option.equals(Option.HELP))
                .filter(option -> !option.equals(Option.EXIT))
                .filter(option -> !option.equals(Option.SAVE_ARGUMENTS))
                .filter(option -> !option.equals(Option.CLEAR_ARGUMENTS))
                .forEach(option -> addCommandToDefaults(cmd, option));
    }

    private void addCommandToDefaults(CommandLine cmd, Option option) {
        if (checker.hasCommand(cmd, option))
            savedArguments.put(option, checker.getArgumentValue(cmd, option));
    }

    @Override
    public void clear() {
        savedArguments.clear();
    }

    @Override
    public Map<Option, String> get() {
        return savedArguments;
    }

}