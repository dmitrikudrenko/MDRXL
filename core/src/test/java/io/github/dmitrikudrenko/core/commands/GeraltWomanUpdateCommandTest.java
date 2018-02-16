package io.github.dmitrikudrenko.core.commands;

import io.github.dmitrikudrenko.core.local.cursor.GeraltWomenCursor;
import io.github.dmitrikudrenko.core.remote.model.woman.Woman;
import org.junit.Test;
import rx.Single;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.*;

public class GeraltWomanUpdateCommandTest extends CommandTest {
    private GeraltWomanUpdateCommand command;

    @Override
    public void setUp() {
        super.setUp();
        command = new GeraltWomanUpdateCommand(
                remoteRepository,
                womenLocalRepository
        );
    }

    @Test
    public void shouldUpdateData() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        womenLocalRepository.getWoman(0).subscribe(
                woman -> {
                    if (woman.getCount() == 0 && latch.getCount() == 1) {
                        //first loading empty data, it's ok
                    } else {
                        checkData(woman);
                        latch.countDown();
                    }
                },
                error -> {
                    throw new AssertionError();
                }
        );
        command.execute(new GeraltWomanUpdateCommandRequest(0)).subscribe();

        assertTrue(latch.await(1, TimeUnit.SECONDS));
    }

    private void checkData(final GeraltWomenCursor cursor) {
        assertTrue(cursor.moveToFirst());

        assertEquals(1, cursor.getCount());
        assertEquals(0, cursor.getId());
        assertEquals("Triss Merigold", cursor.getName());
        assertEquals("https://firebasestorage.googleapis.com/v0/b/geralt-f5e41.appspot.com/o/13277755_521560668030505_144398546_n.jpg?alt=media&token=1ea94dbf-9ec2-48bf-adf7-4f427f9df4bf",
                cursor.getPhoto());
        assertEquals("Redhead", cursor.getHairColor());
        assertEquals(5, cursor.getPhotoCount());
    }

    @Override
    protected Single<Woman> getWoman(final long womanId) {
        try {
            return Single.just(readFromResource("woman.json", Woman.class));
        } catch (final IOException e) {
            return Single.error(new RuntimeException("Can't read resource"));
        }
    }
}
