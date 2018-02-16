package io.github.dmitrikudrenko;

import android.support.annotation.CallSuper;
import org.junit.After;
import org.junit.Before;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

public abstract class RxTest {
    private static final int TEST_TIMEOUT_MS = 2000;

    @CallSuper
    @Before
    public void setUp() {
        RxJavaHooks.onIOScheduler(Schedulers.immediate());
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @CallSuper
    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

    protected void await() {
        try {
            Thread.sleep(TEST_TIMEOUT_MS);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
