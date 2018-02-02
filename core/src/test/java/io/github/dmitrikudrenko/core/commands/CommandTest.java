package io.github.dmitrikudrenko.core.commands;

import android.app.Activity;
import com.google.common.io.Resources;
import io.github.dmitrikudrenko.core.RxTest;
import io.github.dmitrikudrenko.core.di.NetworkModule;
import io.github.dmitrikudrenko.core.local.database.Database;
import io.github.dmitrikudrenko.core.local.database.WitcherSqliteOpenHelper;
import io.github.dmitrikudrenko.core.local.repository.GeraltWomenRepository;
import io.github.dmitrikudrenko.core.local.repository.WitcherVideoRepository;
import io.github.dmitrikudrenko.core.remote.WitcherApi;
import io.github.dmitrikudrenko.core.remote.WitcherRemoteRepository;
import io.github.dmitrikudrenko.core.remote.model.video.Videos;
import io.github.dmitrikudrenko.core.remote.model.woman.Woman;
import io.github.dmitrikudrenko.core.remote.model.woman.Women;
import io.github.dmitrikudrenko.core.remote.model.woman.photo.Photos;
import io.github.dmitrikudrenko.core.remote.settings.Settings;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import rx.Single;

import java.io.IOException;
import java.nio.charset.Charset;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public abstract class CommandTest extends RxTest {
    protected GeraltWomenRepository womenLocalRepository;
    protected WitcherVideoRepository videoLocalRepository;
    protected WitcherRemoteRepository remoteRepository;
    protected Activity activity;

    @Before
    public void setUp() {
        super.setUp();
        activity = Robolectric.setupActivity(Activity.class);

        womenLocalRepository = setupWomenLocalRepository();
        videoLocalRepository = setupVideoLocalRepository();
        remoteRepository = setupRemoteRepository();
    }

    private GeraltWomenRepository setupWomenLocalRepository() {
        final WitcherSqliteOpenHelper helper = new WitcherSqliteOpenHelper(activity);
        final Database database = new Database(helper);

        return new GeraltWomenRepository(database);
    }

    private WitcherVideoRepository setupVideoLocalRepository() {
        final WitcherSqliteOpenHelper helper = new WitcherSqliteOpenHelper(activity);
        final Database database = new Database(helper);

        return new WitcherVideoRepository(database);
    }

    private WitcherRemoteRepository setupRemoteRepository() {
        final WitcherApi witcherApi = new WitcherApi() {
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

            @Override
            public Single<Videos> getVideos() {
                return CommandTest.this.getVideos();
            }
        };
        return new WitcherRemoteRepository(Settings::success, witcherApi);
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

    protected Single<Videos> getVideos() {
        throw new UnsupportedOperationException();
    }

    protected <T> T readFromResource(final String resource, final Class<T> clazz) throws IOException {
        final String data = Resources.toString(
                Resources.getResource(resource), Charset.defaultCharset());
        return new NetworkModule().provideGson().fromJson(data, clazz);
    }
}
