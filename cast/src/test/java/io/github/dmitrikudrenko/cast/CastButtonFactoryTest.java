package io.github.dmitrikudrenko.cast;

import android.content.Context;
import android.view.Menu;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
public class CastButtonFactoryTest {
    private Context context;
    private Menu menu;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.systemContext;
        menu = mock(Menu.class);
        when(menu.findItem(anyInt())).thenReturn(null);
    }

    @Test
    public void shouldNotThrowExceptionIfNoMenuItem() {
        CastButtonFactoryExt.setUpCastDependentButton(context, menu, 0);
    }
}
