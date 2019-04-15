package co.uk.cpt.proximity_calculator;

import co.uk.cpt.proximity_calculator.arguments.ArgumentListener;
import co.uk.cpt.proximity_calculator.command_runner.CommandRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProximityCalculator implements CommandLineRunner {

    @Autowired
    private ArgumentListener parser;
    @Autowired
    private CommandRunner runner;

    public static void main(String[] args) {
        SpringApplication.run(ProximityCalculator.class, args);
    }

    @Override
    public void run(String[] args) {
        listenToUserInput(args);
    }

    private void listenToUserInput(String[] args) {
        parser.checkForSingleInstance(runner, args);

        System.out.println("Welcome to Proximity Calculator!");
        while (true)
            runner.run(parser.listen(runner));
    }

}