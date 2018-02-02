package io.github.dmitrikudrenko.core.commands;

import io.github.dmitrikudrenko.core.local.cursor.WitcherVideoCursor;
import io.github.dmitrikudrenko.core.remote.model.video.Videos;
import org.junit.Test;
import rx.Single;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.*;

public class GeraltVideosUpdateCommandTest extends CommandTest {
    private GeraltVideosUpdateCommand command;

    @Override
    public void setUp() {
        super.setUp();
        command = new GeraltVideosUpdateCommand(videoLocalRepository, remoteRepository);
    }

    @Test
    public void shouldUpdateData() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        videoLocalRepository.getVideos().subscribe(
                videos -> {
                    if (videos.getCount() == 0 && latch.getCount() == 1) {
                        //first loading empty data, it's ok
                    } else {
                        checkData(videos);
                        latch.countDown();
                    }
                },
                error -> {
                    throw new AssertionError(error.getMessage());
                }
        );
        command.execute(new GeraltVideosUpdateCommandRequest()).subscribe();

        assertTrue(latch.await(1, TimeUnit.SECONDS));
    }

    private void checkData(final WitcherVideoCursor videos) {
        assertEquals(1, videos.getCount());
        assertTrue(videos.moveToFirst());
        assertEquals(1, videos.getId());
        assertEquals("video url", videos.getUrl());
        assertEquals("video name", videos.getName());
        assertEquals("video thumbnail", videos.getThumbnail());
        assertEquals(999, videos.getDuration());
    }

    @Override
    protected Single<Videos> getVideos() {
        try {
            return Single.just(readFromResource("videos.json", Videos.class));
        } catch (final IOException e) {
            return Single.error(new RuntimeException("Can't read resource"));
        }
    }
}
