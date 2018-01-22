package io.github.dmitrikudrenko.sample.ui.settings;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.view.View;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class SettingActivityTest {
    private SettingsActivity activity;
    private SettingsFragment fragment;
    private final SettingsPresenter presenter = mock(SettingsPresenter.class);

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(SettingsActivity.class);
        fragment = activity.getFragment();
        fragment.presenter = presenter;
    }

    @Test
    public void shouldBackButtonBeDisplayed() {
        assertNotNull(activity.getSupportActionBar());
        assertEquals("Home button is not set as up button",
                ActionBar.DISPLAY_HOME_AS_UP,
                activity.getSupportActionBar().getDisplayOptions() & ActionBar.DISPLAY_HOME_AS_UP);
    }

    @Test
    public void shouldComponentsBeVisible() {
        assertVisible(fragment.successButton);
        assertVisible(fragment.timeoutButton);
        assertVisible(fragment.errorButton);
    }

    @Test
    public void shouldOnSuccessSetCalledIfSuccessButtonClicked() {
        fragment.successButton.setChecked(true);
        verify(presenter).onSuccessSet();
    }

    @Test
    public void shouldOnTimeoutSetCalledIfTimeoutButtonClicked() {
        fragment.timeoutButton.setChecked(true);
        verify(presenter).onTimeoutSet();
    }

    @Test
    public void shouldOnErrorSetCalledIfErrorButtonClicked() {
        fragment.errorButton.setChecked(true);
        verify(presenter).onErrorSet();
    }

    @Test
    public void shouldSuccessButtonBeCheckedIfSuccessSettingSet() {
        fragment.showSuccessSetting(true);
        assertTrue(fragment.successButton.isChecked());
    }

    @Test
    public void shouldTimeoutButtonBeCheckedIfTimeoutSettingSet() {
        fragment.showTimeoutSetting(true);
        assertTrue(fragment.timeoutButton.isChecked());
    }

    @Test
    public void shouldErrorButtonBeCheckedIfErrorSettingSet() {
        fragment.showErrorSetting(true);
        assertTrue(fragment.errorButton.isChecked());
    }

    private void assertVisible(final View view) {
        assertVisible(view, true);
    }

    private void assertVisible(final View view, final boolean visible) {
        assertNotNull(view);
        assertTrue((view.getVisibility() == View.VISIBLE) == visible);
    }
}
