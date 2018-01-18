package io.github.dmitrikudrenko.core.commands;

import android.app.Activity;
import com.google.common.io.Resources;
import io.github.dmitrikudrenko.core.RxTest;
import io.github.dmitrikudrenko.core.di.NetworkModule;
import io.github.dmitrikudrenko.core.local.database.Database;
import io.github.dmitrikudrenko.core.local.database.GeraltWomenSqliteOpenHelper;
import io.github.dmitrikudrenko.core.local.repository.GeraltWomenRepository;
import io.github.dmitrikudrenko.core.remote.GeraltWomenRemoteRepository;
import io.github.dmitrikudrenko.core.remote.WomenApi;
import io.github.dmitrikudrenko.core.remote.model.Photos;
import io.github.dmitrikudrenko.core.remote.model.Woman;
import io.github.dmitrikudrenko.core.remote.model.Women;
import io.github.dmitrikudrenko.core.remote.settings.Settings;
import org.junit.Before;
import org.robolectric.Robolectric;
import rx.Single;

import java.io.IOException;
import java.nio.charset.Charset;

public class CommandTest extends RxTest {
    protected GeraltWomenRepository localRepository;
    protected GeraltWomenRemoteRepository remoteRepository;
    protected Activity activity;

    @Before
    public void setUp() {
        super.setUp();
        activity = Robolectric.setupActivity(Activity.class);

        localRepository = setupLocalRepository();
        remoteRepository = setupRemoteRepository();
    }

    private GeraltWomenRepository setupLocalRepository() {
        final GeraltWomenSqliteOpenHelper helper = new GeraltWomenSqliteOpenHelper(activity);
        final Database database = new Database(helper);

        return new GeraltWomenRepository(database);
    }

    private GeraltWomenRemoteRepository setupRemoteRepository() {
        final WomenApi womenApi = new WomenApi() {
            @Override
            public Single<Women> getWomen() {
                return CommandTest.this.getWomen();
            }

            @Override
            public Single<Photos> getPhotos(final long womanId) {
                return CommandTest.this.getPhotos(womanId);
            }

            @Override
            public Single<Woman> getWoman(final long womanId) {
                return CommandTest.this.getWoman(womanId);
            }
        };
        return new GeraltWomenRemoteRepository(Settings::success, womenApi);
    }

    protected Single<Women> getWomen() {
        throw new UnsupportedOperationException();
    }

    protected Single<Woman> getWoman(final long womanId) {
        throw new UnsupportedOperationException();
    }

    protected Single<Photos> getPhotos(final long womanId) {
        throw new UnsupportedOperationException();
    }

    protected <T> T readFromResource(final String resource, final Class<T> clazz) throws IOException {
        final String data = Resources.toString(
                Resources.getResource(resource), Charset.defaultCharset());
        return new NetworkModule().provideGson().fromJson(data, clazz);
    }
}
