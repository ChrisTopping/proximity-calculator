package co.uk.cpt.proximity_calculator.command_runner;

import co.uk.cpt.proximity_calculator.arguments.ArgumentListener;
import co.uk.cpt.proximity_calculator.arguments.Option;
import co.uk.cpt.proximity_calculator.business.Business;
import co.uk.cpt.proximity_calculator.distance_from_origin_finder.DistanceFromOriginFinder;
import co.uk.cpt.proximity_calculator.importer.BusinessImporter;
import co.uk.cpt.proximity_calculator.importer.CsvBusinessRepository;
import co.uk.cpt.proximity_calculator.printer.OrderedLocatableDistancePrinter;
import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DefaultCommandRunnerTest {

    private final CommandLine emptyCommandLine = new CommandLine.Builder().build();
    @Mock
    private ArgumentListener listener;
    @Mock
    private BusinessImporter<CsvBusinessRepository> importer;
    @Mock
    private DistanceFromOriginFinder<Business> distanceFinder;
    @Mock
    private OrderedLocatableDistancePrinter<Business> printer;
    @Mock
    private CommandChecker<Option> checker;
    @Mock
    private ArgumentCache<Map<Option, String>> cache;

    @InjectMocks
    private DefaultCommandRunner runner;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenCommandNull_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> runner.run(null));
    }

    @Test
    void givenCommandHasSave_callCache_save() {
        setCheckerReturnValue(true, emptyCommandLine, Option.SAVE_ARGUMENTS);
        runner.run(emptyCommandLine);
        verify(cache).save(emptyCommandLine);
        verifyNoMoreInteractions(cache);
    }

    @Test
    void givenCommandHasClear_callCache_clear() {
        setCheckerReturnValue(false, emptyCommandLine, Option.SAVE_ARGUMENTS);
        setCheckerReturnValue(true, emptyCommandLine, Option.CLEAR_ARGUMENTS);
        runner.run(emptyCommandLine);
        verify(cache).clear();
        verifyNoMoreInteractions(cache);
    }

    @Test
    void givenCommandHasPrint_callCache_get() {
        setCheckerReturnValue(false, emptyCommandLine, Option.SAVE_ARGUMENTS);
        setCheckerReturnValue(false, emptyCommandLine, Option.CLEAR_ARGUMENTS);
        setCheckerReturnValue(true, emptyCommandLine, Option.PRINT_ARGUMENTS);
        doReturn(new HashMap<>()).when(cache).get();
        runner.run(emptyCommandLine);
        verify(cache, times(2)).get();
        verifyNoMoreInteractions(cache);
    }

    private void setCheckerReturnValue(boolean hasCommand, CommandLine cmd, Option option) {
        doReturn(hasCommand).when(checker).hasCommand(cmd, option);
    }

}