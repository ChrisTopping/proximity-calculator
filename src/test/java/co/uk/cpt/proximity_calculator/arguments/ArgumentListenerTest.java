package co.uk.cpt.proximity_calculator.arguments;

import co.uk.cpt.proximity_calculator.arguments.input_retriever.ConsoleUserInputRetriever;
import co.uk.cpt.proximity_calculator.arguments.input_retriever.UserInputRetriever;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static co.uk.cpt.proximity_calculator.arguments.ArgumentListener.DEFAULT_HELPER;
import static co.uk.cpt.proximity_calculator.arguments.ArgumentValidator.Validity.VALID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class ArgumentListenerTest {

    private static CommandLine emptyCommandLine;

    @Mock
    private ArgumentValidator validator;
    @Mock
    private UserInputRetriever retriever;

    @InjectMocks
    private ArgumentListener listener;

    @BeforeAll
    static void setUpAll() {
        try {
            emptyCommandLine = new DefaultParser().parse(new Options(), new String[]{""});
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenNullValidator_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->new ArgumentListener(null, new ConsoleUserInputRetriever()));
    }

    @Test
    void givenNullInputRetriever_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ArgumentListener(new ArgumentValidator(), null));
    }

    @Test
    void givenValidValidator_andValidInputRetriever_doNotThrowException() {
        new ArgumentListener(new ArgumentValidator(), new ConsoleUserInputRetriever());
    }

    @Test
    void whenListen_retrieverCalled_withDefaultHelperMessage() {
        doReturn("").when(retriever).retrieve(any());
        doReturn(VALID).when(validator).validate(any());
        listener.listen(null);
        verify(retriever).retrieve(DEFAULT_HELPER);
    }

    @Test
    void whenListen_givenValidatorReturnsValid_returnCmd() {
        doReturn("").when(retriever).retrieve(any());
        doReturn(VALID).when(validator).validate(any());
        CommandLine cmd = listener.listen(null);
        assertEquals(emptyCommandLine.hasOption(""), cmd.hasOption(""));
    }

}