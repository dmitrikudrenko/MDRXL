package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import io.github.dmitrikudrenko.mdrxl.RxTest;
import io.github.dmitrikudrenko.mdrxl.commands.CommandStarter;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.sample.BuildConfig;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands.GeraltWomenUpdateCommandRequest;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenCursor;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenLoader;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenRepository;
import io.github.dmitrikudrenko.mdrxl.sample.ui.navigation.Router;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ui.messages.MessageFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import rx.Observable;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.isNull;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class GeraltWomenPresenterTest extends RxTest {
    private static final int TEST_TIMEOUT_MS = 1000;

    private GeraltWomenPresenter presenter;
    private GeraltWomenView view;
    private GeraltWomenView$$State state;

    private RxLoaderManager loaderManager;
    private CommandStarter commandStarter;
    private GeraltWomenLoader loader;
    private GeraltWomenRepository repository;
    private MessageFactory messageFactory;
    private Router router;

    @Before
    public void setUp() {
        super.setUp();
        final AppCompatActivity activity = Robolectric.setupActivity(AppCompatActivity.class);

        loaderManager = spy(new RxLoaderManager(activity.getSupportLoaderManager()));
        commandStarter = mock(CommandStarter.class);
        repository = mock(GeraltWomenRepository.class);
        loader = new GeraltWomenLoader(activity, repository);
        messageFactory = mock(MessageFactory.class);
        router = mock(Router.class);

        view = mock(GeraltWomenView.class);
        state = mock(GeraltWomenView$$State.class);
    }

    @NonNull
    private GeraltWomenPresenter createPresenter(final boolean multiWindow) {
        return new GeraltWomenPresenter(
                loaderManager,
                () -> loader,
                commandStarter,
                router,
                messageFactory,
                multiWindow
        );
    }

    private void attach() {
        attach(false);
    }

    private void attach(final boolean multiWindow) {
        presenter = createPresenter(multiWindow);
        presenter.setViewState(state);
        presenter.attachView(view);
        await();
    }

    @Test
    public void shouldLoadDataOnAttachView() {
        attach();
        verify(loaderManager).init(anyInt(), any(), any());
        verify(commandStarter).execute(any(GeraltWomenUpdateCommandRequest.class));
    }

    @Test
    public void shouldStartLoadingViewOnAttachView() {
        attach();
        verify(state).startLoading();
    }

    @Test
    public void shouldStopLoadingAndShowErrorMessageIfError() {
        final String errorMessage = "Error message";
        final Throwable error = new RuntimeException(errorMessage);
        when(repository.get(any(String.class))).thenReturn(Observable.error(error));

        attach();

        verify(state).stopLoading();
        verify(messageFactory).showError(errorMessage);
    }

    @Test
    public void shouldStopLoadingAndShowDataIfSuccess() {
        when(repository.get(or(anyString(), (String) isNull())))
                .thenReturn(Observable.just(mock(GeraltWomenCursor.class)));

        attach();

        verify(state).stopLoading();
    }

    @Test
    public void shouldOpenFirstElementIfExistsAndTablet() throws InterruptedException {
        final GeraltWomenCursor cursor = mock(GeraltWomenCursor.class);
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToPosition(anyInt())).thenReturn(true);
        when(cursor.getId()).thenReturn(1);
        when(repository.get(or(anyString(), (String) isNull())))
                .thenReturn(Observable.just(cursor));

        attach(true);

        verify(state).notifyDataChanged(any(DiffUtil.DiffResult.class));
        verify(router).openGeraltWoman(anyLong());
    }

    private void await() {
        try {
            Thread.sleep(TEST_TIMEOUT_MS);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
