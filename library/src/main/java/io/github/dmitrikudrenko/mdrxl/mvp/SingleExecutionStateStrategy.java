package io.github.dmitrikudrenko.mdrxl.mvp;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

import java.util.List;

//clear commands queue
//after execution command will be removed from queue
public class SingleExecutionStateStrategy implements StateStrategy {
    @Override
    public <View extends MvpView> void beforeApply(final List<ViewCommand<View>> currentState,
                                                   final ViewCommand<View> incomingCommand) {
        currentState.clear();
        currentState.add(incomingCommand);
    }

    @Override
    public <View extends MvpView> void afterApply(final List<ViewCommand<View>> currentState,
                                                  final ViewCommand<View> incomingCommand) {
        currentState.remove(incomingCommand);
    }
}
