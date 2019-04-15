package co.uk.cpt.proximity_calculator.arguments.input_retriever;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleUserInputRetriever implements UserInputRetriever {

    @Override
    public String retrieve(String message) {
        System.out.println(message);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

}