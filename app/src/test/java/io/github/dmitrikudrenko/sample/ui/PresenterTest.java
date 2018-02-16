package io.github.dmitrikudrenko.sample.ui;

import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import io.github.dmitrikudrenko.RxTest;
import io.github.dmitrikudrenko.mdrxl.commands.CommandStarter;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.sample.ui.navigation.Router;
import io.github.dmitrikudrenko.sample.utils.ui.messages.MessageFactory;
import org.junit.Before;
import org.robolectric.Robolectric;

import static org.mockito.Mockito.*;

public abstract class PresenterTest extends RxTest {
    protected AppCompatActivity activity;
    protected RxLoaderManager rxLoaderManager;
    protected CommandStarter commandStarter;
    protected Router router;
    protected MessageFactory messageFactory;

    @CallSuper
    @Override
    @Before
    public void setUp() {
        super.setUp();
        activity = Robolectric.setupActivity(AppCompatActivity.class);
        rxLoaderManager = spy(new RxLoaderManager(activity.getSupportLoaderManager()));
        commandStarter = mock(CommandStarter.class);
        router = mock(Router.class);
        messageFactory = mock(MessageFactory.class);
    }
}
