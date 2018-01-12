package io.github.dmitrikudrenko.mdrxl.mvp;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.ViewCommand;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class SingleExecutionStateStrategyTest {
    private SingleExecutionStateStrategy strategy;

    @Before
    public void setUp() {
        strategy = new SingleExecutionStateStrategy();
    }

    @Test
    public void shouldExecuteCommandAndRemoveIt() {
        final List<ViewCommand<MvpView>> currentState = new ArrayList<>();
        final ViewCommand<MvpView> incomingCommand = mock(ViewCommand.class);

        strategy.beforeApply(currentState, incomingCommand);

        assertEquals(1, currentState.size());
        assertEquals(incomingCommand, currentState.get(0));

        strategy.afterApply(currentState, incomingCommand);

        assertEquals(0, currentState.size());
    }
}
