package co.uk.cpt.proximity_calculator.arguments;

import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static co.uk.cpt.proximity_calculator.arguments.ArgumentValidator.Validity.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;


class ArgumentValidatorTest {

    @Mock
    private CommandLine cmd;

    private final ArgumentValidator validator = new ArgumentValidator();

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenCmdIsNull_returnInvalid() {
        assertEquals(INVALID, validator.validate(null));
    }

    @Test
    void givenCmdIsMissingRequiredOptions_returnMissingRequiredCommands() {
        returnFalseByDefault();
        assertEquals(MISSING_REQUIRED_COMMANDS, validator.validate(cmd));
    }

    @Test
    void givenCmdHasHelp_returnValidWithHelp() {
        returnFalseByDefault();
        returnTrueForOption(Option.HELP);
        assertEquals(HELP, validator.validate(cmd));
    }

    @Test
    void givenCmdHasExit_returnValidWithExit() {
        returnFalseByDefault();
        returnTrueForOption(Option.EXIT);
        assertEquals(EXIT, validator.validate(cmd));
    }

    @Test
    void givenCmdHasSave_returnValid() {
        returnFalseByDefault();
        returnTrueForOption(Option.SAVE_ARGUMENTS);
        assertEquals(VALID, validator.validate(cmd));
    }

    @Test
    void givenCmdHasClear_returnValid() {
        returnFalseByDefault();
        returnTrueForOption(Option.CLEAR_ARGUMENTS);
        assertEquals(VALID, validator.validate(cmd));
    }

    @Test
    void givenCmdHasPrint_returnValid() {
        returnFalseByDefault();
        returnTrueForOption(Option.PRINT_ARGUMENTS);
        assertEquals(VALID, validator.validate(cmd));
    }

    @Test
    void givenCmdHasAllRequiredOptions_returnValid() {
        returnFalseByDefault();
        Arrays.stream(Option.values()).filter(Option::isRequired).forEach(this::returnTrueForOption);
        assertEquals(VALID, validator.validate(cmd));
    }

    private void returnTrueForOption(Option printArguments) {
        doReturn(true).when(cmd).hasOption(printArguments.getOption());
    }

    private void returnFalseByDefault() {
        doReturn(false).when(cmd).hasOption(any());
    }

}