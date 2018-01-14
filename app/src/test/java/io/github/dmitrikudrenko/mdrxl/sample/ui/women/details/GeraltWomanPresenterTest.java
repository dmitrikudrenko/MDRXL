package io.github.dmitrikudrenko.mdrxl.sample.ui.women.details;

import android.support.v7.app.AppCompatActivity;
import io.github.dmitrikudrenko.mdrxl.commands.CommandStarter;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.sample.BuildConfig;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands.GeraltWomanUpdateCommandRequest;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomanLoaderFactory;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenRepository;
import io.github.dmitrikudrenko.mdrxl.sample.ui.navigation.Router;
import io.github.dmitrikudrenko.mdrxl.sample.utils.EventBus;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ui.messages.MessageFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class GeraltWomanPresenterTest {
    private AppCompatActivity activity;

    private GeraltWomanPresenter presenter;
    private RxLoaderManager loaderManager;
    private GeraltWomenRepository repository;
    private CommandStarter commandStarter;
    private Router router;
    private MessageFactory messageFactory;
    private EventBus eventBus;
    private final int id = 0;

    private GeraltWomanView view;
    private GeraltWomanView$$State state;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(AppCompatActivity.class);

        loaderManager = spy(new RxLoaderManager(activity.getSupportLoaderManager()));
        repository = mock(GeraltWomenRepository.class);
        commandStarter = mock(CommandStarter.class);
        router = mock(Router.class);
        messageFactory = mock(MessageFactory.class);
        eventBus = mock(EventBus.class);
        presenter = new GeraltWomanPresenter(
                loaderManager,
                new GeraltWomanLoaderFactory(() -> activity, () -> repository),
                commandStarter,
                router,
                messageFactory,
                eventBus,
                id
        );

        view = mock(GeraltWomanView.class);
        state = mock(GeraltWomanView$$State.class);

        presenter.setViewState(state);
        presenter.attachView(view);
    }

    @Test
    public void shouldUpdateDataOnAttach() {
        verify(loaderManager).init(anyInt(), argThat(new ArgumentMatcher<RxLoaderArguments>() {
            @Override
            public boolean matches(final Object argument) {
                return argument instanceof RxLoaderArguments
                        && ((RxLoaderArguments) argument).getLong("id") == id;
            }
        }), any(RxLoaderCallbacks.class));
        verify(commandStarter).execute(any(GeraltWomanUpdateCommandRequest.class));
    }
}
