package io.github.dmitrikudrenko.sample.ui.women.details;

import io.github.dmitrikudrenko.core.commands.GeraltWomanUpdateCommandRequest;
import io.github.dmitrikudrenko.core.events.EventSource;
import io.github.dmitrikudrenko.core.local.loader.women.GeraltWomanLoaderFactory;
import io.github.dmitrikudrenko.core.local.repository.GeraltWomenRepository;
import io.github.dmitrikudrenko.mdrxl.commands.CommandStarter;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.sample.BuildConfig;
import io.github.dmitrikudrenko.sample.ui.PresenterTest;
import io.github.dmitrikudrenko.sample.ui.navigation.Router;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class GeraltWomanPresenterTest extends PresenterTest {
    private GeraltWomanPresenter presenter;
    private GeraltWomenRepository repository;
    private CommandStarter commandStarter;
    private Router router;
    private EventSource eventSource;
    private final int id = 0;

    private GeraltWomanView view;
    private GeraltWomanView$$State state;

    @Before
    public void setUp() {
        super.setUp();
        repository = mock(GeraltWomenRepository.class);
        commandStarter = mock(CommandStarter.class);
        router = mock(Router.class);
        eventSource = mock(EventSource.class);
        presenter = new GeraltWomanPresenter(
                rxLoaderManager,
                new GeraltWomanLoaderFactory(() -> activity, () -> repository),
                commandStarter,
                router,
                messageFactory,
                eventSource,
                id
        );

        view = mock(GeraltWomanView.class);
        state = mock(GeraltWomanView$$State.class);

        presenter.setViewState(state);
        presenter.attachView(view);
    }

    @Test
    public void shouldUpdateDataOnAttach() {
        verify(rxLoaderManager).init(anyInt(), argThat(new ArgumentMatcher<RxLoaderArguments>() {
            @Override
            public boolean matches(final Object argument) {
                return argument instanceof RxLoaderArguments
                        && ((RxLoaderArguments) argument).getLong("id") == id;
            }
        }), any(RxLoaderCallbacks.class));
        verify(commandStarter).execute(any(GeraltWomanUpdateCommandRequest.class));
    }
}
