package io.github.dmitrikudrenko.core.remote.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import io.github.dmitrikudrenko.core.AppCompatForTestActivity;
import io.github.dmitrikudrenko.core.BuildConfig;
import io.github.dmitrikudrenko.core.RxTest;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class SettingsLoaderTest extends RxTest {
    private AppCompatActivity activity;
    private NetworkSettingsRepository repository;
    private SettingsLoader loader;

    @Before
    public void setUp() {
        super.setUp();
        activity = Robolectric.setupActivity(AppCompatForTestActivity.class);
        final SharedPreferences sharedPreferences = RuntimeEnvironment.application
                .getSharedPreferences("filename", Context.MODE_PRIVATE);
        repository = new NetworkSettingsRepository(sharedPreferences);
        loader = new SettingsLoader(activity, repository);
    }

    @Test
    public void shouldReturnDefaultSettingsSync() {
        assertTrue(repository.getSync().isSuccess());
    }

    @Test
    public void shouldReturnDefaultSettingsAsync() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        activity.getSupportLoaderManager().initLoader(0, null, new RxLoaderCallbacks<Settings>() {
            @Override
            protected RxLoader<Settings> getLoader(final int id, final RxLoaderArguments args) {
                return loader;
            }

            @Override
            protected void onSuccess(final int id, final Settings data) {
                assertTrue(data.isSuccess());
                latch.countDown();
            }

            @Override
            protected void onError(final int id, final Throwable error) {
                throw new AssertionError("Unexpected error", error);
            }
        });
        assertTrue(latch.await(1, TimeUnit.SECONDS));
    }

    @Test
    public void shouldChangeSettingsSync() throws InterruptedException {
        repository.set(NetworkSettingsRepository.NetworkPreference.KEY_TIMEOUT);
        assertTrue(repository.getSync().isTimeout());

        repository.set(NetworkSettingsRepository.NetworkPreference.KEY_ERROR);
        assertTrue(repository.getSync().isError());

        repository.set(NetworkSettingsRepository.NetworkPreference.KEY_SUCCESS);
        assertTrue(repository.getSync().isSuccess());
    }

    //TODO not working
//    @Test
    public void shouldChangeSettingsAsync() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);
        activity.getSupportLoaderManager().initLoader(0, null, new RxLoaderCallbacks<Settings>() {
            @Override
            protected RxLoader<Settings> getLoader(final int id, final RxLoaderArguments args) {
                return loader;
            }

            @Override
            protected void onSuccess(final int id, final Settings data) {
                if (latch.getCount() == 2) {
                    assertTrue(data.isSuccess());
                    repository.set(NetworkSettingsRepository.NetworkPreference.KEY_ERROR);
                } else {
                    assertTrue(data.isError());
                }
                latch.countDown();
            }

            @Override
            protected void onError(final int id, final Throwable error) {
                throw new AssertionError("Unexpected error", error);
            }
        });
        assertTrue(latch.await(1, TimeUnit.SECONDS));
    }
}
