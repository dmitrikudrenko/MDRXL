package io.github.dmitrikudrenko.mdrxl.loader;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import io.github.dmitrikudrenko.mdrxl.RxTest;
import junit.framework.AssertionFailedError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import rx.Observable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class RxLoaderTest extends RxTest {
    private static final int TEST_TIMEOUT_MS = 3000;
    private static final Throwable throwable = new RuntimeException();

    private AppCompatActivity activity;

    @Before
    public void setUp() {
        super.setUp();
        activity = Robolectric.setupActivity(AppCompatActivity.class);
    }

    @Test
    public void shouldDeliverData() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final RxLoader<Object> loader = new RxLoaderImpl(activity, Observable.just("query"));
        activity.getSupportLoaderManager().initLoader(0, null, new RxLoaderCallbacks<Object>() {
            @Override
            protected RxLoader<Object> getLoader(final int id, final RxLoaderArguments args) {
                return loader;
            }

            @Override
            protected void onSuccess(final int id, final Object data) {
                assertEquals("query", data);
                latch.countDown();
            }

            @Override
            protected void onError(final int id, final Throwable error) {
                throw new AssertionFailedError("The loader should return data");
            }
        });
        assertTrue("Timeout", latch.await(TEST_TIMEOUT_MS, TimeUnit.MILLISECONDS));
    }

    @Test
    public void shouldDeliverError() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final RxLoader<Object> errorLoader = new RxLoaderErrorImpl(activity);
        activity.getSupportLoaderManager().initLoader(0, null, new RxLoaderCallbacks<Object>() {
            @Override
            protected RxLoader<Object> getLoader(final int id, final RxLoaderArguments args) {
                return errorLoader;
            }

            @Override
            protected void onSuccess(final int id, final Object data) {
                throw new AssertionFailedError("The loader should return error");
            }

            @Override
            protected void onError(final int id, final Throwable error) {
                assertEquals(throwable, error);
                latch.countDown();
            }
        });
        assertTrue("Timeout", latch.await(TEST_TIMEOUT_MS, TimeUnit.MILLISECONDS));
    }

    @Test
    public void shouldFlushSearchQuery() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);
        final RxLoader<Object> loader = new SearchedRxLoader(activity, Observable.just("query"));
        loader.setSearchQuery("query");
        activity.getSupportLoaderManager().initLoader(0, null, new RxLoaderCallbacks<Object>() {
            @Override
            protected RxLoader<Object> getLoader(final int id, final RxLoaderArguments args) {
                return loader;
            }

            @Override
            protected void onSuccess(final int id, final Object data) {
                if (latch.getCount() == 2) {
                    assertEquals("query", data);
                    latch.countDown();
                    loader.flushSearch();
                } else if (latch.getCount() == 1) {
                    assertNull(data);
                    latch.countDown();
                }
            }

            @Override
            protected void onError(final int id, final Throwable error) {
                throw new AssertionFailedError("The loader should return data");
            }
        });
        assertTrue("Timeout", latch.await(TEST_TIMEOUT_MS, TimeUnit.MILLISECONDS));
    }

    @Test
    public void shouldDeliverDataIfContentChanged() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);
        final RxLoader<Object> loader = new RxLoaderImpl(activity, Observable.just("query"));
        activity.getSupportLoaderManager().initLoader(0, null, new RxLoaderCallbacks<Object>() {
            @Override
            protected RxLoader<Object> getLoader(final int id, final RxLoaderArguments args) {
                return loader;
            }

            @Override
            protected void onSuccess(final int id, final Object data) {
                if (latch.getCount() == 2) {
                    assertEquals("query", data);
                    latch.countDown();
                    loader.onContentChanged();
                } else if (latch.getCount() == 1) {
                    assertEquals("query", data);
                    latch.countDown();
                }
            }

            @Override
            protected void onError(final int id, final Throwable error) {
                throw new AssertionFailedError("The loader should return data");
            }
        });
        assertTrue("Timeout", latch.await(TEST_TIMEOUT_MS, TimeUnit.MILLISECONDS));
    }

    @Test
    public void shouldCallLifecycleEvents() {
        final ActivityController<AppCompatActivity> activityController =
                Robolectric.buildActivity(AppCompatActivity.class);
        activityController.setup();
        final AppCompatActivity activity = activityController.get();
        final RxLoaderImpl loader = spy(new RxLoaderImpl(activity, Observable.just("query")));
        activity.getSupportLoaderManager().initLoader(0, null, new RxLoaderCallbacks<Object>() {
            @Override
            protected RxLoader<Object> getLoader(final int id, final RxLoaderArguments args) {
                return loader;
            }

            @Override
            protected void onSuccess(final int id, final Object data) {

            }

            @Override
            protected void onError(final int id, final Throwable error) {

            }
        });
        activityController.pause().stop();
        verify(loader).onStopLoading();
        activityController.destroy();
        verify(loader).onReset();
    }

    private static class RxLoaderImpl extends RxLoader<Object> {
        final Observable<Object> source;

        RxLoaderImpl(final Context context, final Observable<Object> source) {
            super(context);
            this.source = source;
        }

        @Override
        protected Observable<Object> create(final String query) {
            return source.delay(1000, TimeUnit.MILLISECONDS);
        }
    }

    private static class SearchedRxLoader extends RxLoaderImpl {

        SearchedRxLoader(final Context context, final Observable<Object> source) {
            super(context, source);
        }

        @Override
        protected Observable<Object> create(final String query) {
            return source.map(o -> o.equals(query) ? o : null);
        }
    }

    private static class RxLoaderErrorImpl extends RxLoader<Object> {

        RxLoaderErrorImpl(final Context context) {
            super(context);
        }

        @Override
        protected Observable<Object> create(final String query) {
            return Observable.error(throwable);
        }
    }
}
