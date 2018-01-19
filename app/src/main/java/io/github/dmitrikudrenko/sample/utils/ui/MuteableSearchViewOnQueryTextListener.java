package io.github.dmitrikudrenko.sample.utils.ui;

import android.support.v7.widget.SearchView;

public class MuteableSearchViewOnQueryTextListener implements SearchView.OnQueryTextListener {
    private final SearchView.OnQueryTextListener onQueryTextListener;
    private boolean mute;

    public MuteableSearchViewOnQueryTextListener(final SearchView.OnQueryTextListener onQueryTextListener) {
        this.onQueryTextListener = onQueryTextListener;
    }

    @Override
    public boolean onQueryTextSubmit(final String query) {
        return !mute && onQueryTextListener.onQueryTextSubmit(query);
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
        return !mute && onQueryTextListener.onQueryTextChange(newText);
    }

    public void mute() {
        this.mute = true;
    }

    public void unmute() {
        this.mute = false;
    }
}
