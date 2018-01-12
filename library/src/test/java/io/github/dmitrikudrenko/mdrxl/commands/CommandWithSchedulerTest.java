package io.github.dmitrikudrenko.mdrxl.commands;

import org.junit.Before;
import org.junit.Test;
import rx.Scheduler;

import javax.inject.Provider;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class CommandWithSchedulerTest {
    private CommandWithScheduler<Command> commandWithScheduler;

    private Command command;
    private Scheduler scheduler;

    @Before
    public void setUp() {
        command = mock(Command.class);
        final Provider<Command> provider = () -> command;
        scheduler = mock(Scheduler.class);
        commandWithScheduler = new CommandWithScheduler<>(provider, scheduler);
    }

    @Test
    public void shouldReturnSameScheduler() {
        assertEquals(commandWithScheduler.getScheduler(), scheduler);
    }

    @Test
    public void shouldReturnCommand() {
        assertEquals(commandWithScheduler.getCommand(), command);
    }
}
