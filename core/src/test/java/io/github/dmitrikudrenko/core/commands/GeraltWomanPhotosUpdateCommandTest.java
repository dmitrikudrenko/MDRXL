package io.github.dmitrikudrenko.core.commands;

import io.github.dmitrikudrenko.core.local.cursor.GeraltWomanPhotoCursor;
import io.github.dmitrikudrenko.core.remote.model.Photos;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import rx.Single;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class GeraltWomanPhotosUpdateCommandTest extends CommandTest {
    private GeraltWomanPhotosUpdateCommand command;

    @Override
    public void setUp() {
        super.setUp();
        command = new GeraltWomanPhotosUpdateCommand(
                remoteRepository,
                localRepository
        );
    }

    @Test
    public void shouldUpdateData() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        localRepository.getPhotos(0).subscribe(
                photos -> {
                    if (photos.getCount() == 0 && latch.getCount() == 1) {
                        //first loading empty data, it's ok
                    } else {
                        checkData(photos);
                        latch.countDown();
                    }
                },
                error -> {
                    latch.countDown();
                    throw new AssertionError();
                }
        );
        command.execute(new GeraltWomanPhotosUpdateCommandRequest(0)).subscribe();

        assertTrue(latch.await(1, TimeUnit.SECONDS));
    }

    private void checkData(final GeraltWomanPhotoCursor cursor) {
        assertEquals(5, cursor.getCount());
        assertTrue(cursor.moveToFirst());
        assertEquals(1, cursor.getId());
        assertEquals("http://vignette1.wikia.nocookie.net/witcher/images/2/27/Triss-TW3-new-render.png/revision/latest?cb=20160402173701",
                cursor.getUrl());
    }

    @Override
    protected Single<Photos> getPhotos(final long womanId) {
        try {
            return Single.just(readFromResource("photos.json", Photos.class));
        } catch (final IOException e) {
            return Single.error(new RuntimeException("Can't read resource"));
        }
    }
}
