package io.github.dmitrikudrenko.mdrxl.commands;

import org.junit.Before;
import org.junit.Test;
import rx.Completable;
import rx.schedulers.TestScheduler;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.*;

public class CommandStarterTest {
    private CommandStarter commandStarter;

    private CommandRequest1 commandRequest1;
    private Command1 command1;

    private CommandRequest2 commandRequest2;

    @Before
    public void setUp() {
        commandRequest1 = new CommandRequest1();
        command1 = spy(new Command1());

        commandRequest2 = new CommandRequest2();

        final CommandWithScheduler<Command<CommandRequest1>> commandWithScheduler =
                new CommandWithScheduler<>(() -> command1, new TestScheduler());
        final Map<Class<? extends CommandRequest>, CommandWithScheduler> commands =
                Collections.singletonMap(commandRequest1.getClass(), commandWithScheduler);
        commandStarter = new CommandStarter(commands);
    }

    @Test
    public void shouldBeExecuted() {
        commandStarter.execute(commandRequest1);
        verify(command1).execute(commandRequest1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotFoundCommand() {
        commandStarter.execute(commandRequest2);
    }

    private static class CommandRequest1 extends CommandRequest {
    }

    private static class Command1 implements Command<CommandRequest1> {

        @Override
        public Completable execute(final CommandRequest1 request) {
            return Completable.complete();
        }
    }

    private static class CommandRequest2 extends CommandRequest {
    }
}
