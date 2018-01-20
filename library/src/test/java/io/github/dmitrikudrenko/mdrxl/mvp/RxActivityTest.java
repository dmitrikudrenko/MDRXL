package io.github.dmitrikudrenko.mdrxl.mvp;

import io.github.dmitrikudrenko.mdrxl.BuildConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RxActivityTest {
    private RxActivity activity;

    @Before
    public void setUp() {
        activity = spy(Robolectric.setupActivity(RxActivity.class));
    }

    @Test
    public void shouldBackPressedIfNavigatedUp() {
        activity.onSupportNavigateUp();
        verify(activity).onBackPressed();
    }
}
