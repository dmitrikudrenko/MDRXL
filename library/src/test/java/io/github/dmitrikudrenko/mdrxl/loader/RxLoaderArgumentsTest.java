package io.github.dmitrikudrenko.mdrxl.loader;

import android.os.Bundle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class RxLoaderArgumentsTest {
    private static final String KEY = "key";

    private Bundle bundle;
    private RxLoaderArguments arguments;

    @Before
    public void setUp() {
        bundle = new Bundle();
        arguments = RxLoaderArguments.create(bundle);
    }

    @Test
    public void shouldCreateRxLoaderArguments() {
        assertEquals(bundle, arguments.getArgs());
    }

    @Test
    public void shouldHoldInt() {
        final int value = 1;
        arguments.putInt(KEY, value);
        assertEquals(value, arguments.getInt(KEY));
    }

    @Test
    public void shouldHoldLong() {
        final long value = 1L;
        arguments.putLong(KEY, value);
        assertEquals(value, arguments.getLong(KEY));
    }
}
