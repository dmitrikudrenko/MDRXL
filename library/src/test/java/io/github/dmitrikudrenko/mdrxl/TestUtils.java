package io.github.dmitrikudrenko.mdrxl;

import android.content.res.Configuration;

public class TestUtils {
    public static Configuration changeOrientation(final Configuration configuration) {
        final Configuration newConfiguration = new Configuration(configuration);
        newConfiguration.orientation = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
                ? Configuration.ORIENTATION_LANDSCAPE : Configuration.ORIENTATION_LANDSCAPE;
        return newConfiguration;
    }
}
