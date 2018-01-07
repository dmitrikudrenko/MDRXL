package io.github.dmitrikudrenko.mdrxl.mvp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.TestRunnable;
import rx.Subscription;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class RxPresenterTest {
    private RxPresenter<RxView> presenter;

    @Before
    public void setUp() {
        presenter = new RxPresenterImpl();
    }

    @Test
    public void shouldUnsubscribeSubscriptionOnDestroy() {
        final Subscription subscription = new TestSubscriber<>();
        presenter.add(subscription);
        presenter.onDestroy();
        assertTrue(subscription.isUnsubscribed());
    }

    @Test
    public void shouldRunnableBeRun() {
        final TestRunnable runnable = new TestRunnable();
        presenter.runOnUiThread(runnable);
        assertTrue(runnable.wasRun);
    }

    @Test
    public void shouldErrorRunnableBeRun() {
        final TestRunnable runnable = new TestRunnable() {
            @Override
            public void run() {
                super.run();
                throw new RuntimeException();
            }
        };
        presenter.runOnUiThread(runnable);
        assertTrue(runnable.wasRun);
    }

    private static class RxPresenterImpl extends RxPresenter<RxView> {
    }
}
