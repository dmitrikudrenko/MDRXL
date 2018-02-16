package io.github.dmitrikudrenko.sample.cast;

@SuppressWarnings("unused")
public class CastOptionsProvider extends io.github.dmitrikudrenko.cast.CastOptionsProvider {
    @Override
    protected String expandedControllerActivityClassName() {
        return ExpandedControlsActivity.class.getName();
    }
}
