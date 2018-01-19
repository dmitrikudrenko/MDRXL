package io.github.dmitrikudrenko.sample.di;

import android.content.res.Resources;
import dagger.Module;
import dagger.Provides;
import io.github.dmitrikudrenko.sample.R;

@Module
public class ConfigurationModule {
    @MultiWindow
    @Provides
    boolean provideMultiWindowConfiguration(final Resources resources) {
        return resources.getBoolean(R.bool.tablet);
    }

    @UnderTest
    @Provides
    boolean provideUnderTestConfiguration() {
        try {
            Class.forName("org.robolectric.Robolectric");
            return true;
        } catch (final ClassNotFoundException ex) {
            return false;
        }
    }
}
