package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list;

import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import butterknife.BindView;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.BaseFragmentHolderRxActivity;
import io.github.dmitrikudrenko.mdrxl.sample.ui.settings.SettingsActivity;

import javax.annotation.Nullable;

public class GeraltWomenActivity extends BaseFragmentHolderRxActivity<GeraltWomenFragment> {
    @Nullable
    @BindView(R.id.details)
    ViewGroup detailsContainer;

    private MenuItem searchItem;
    private SearchView searchView;
    private MuteableSearchViewOnQueryTextListener onQueryTextListener;

    @Override
    protected GeraltWomenFragment createFragment() {
        return new GeraltWomenFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.m_search, menu);
        getMenuInflater().inflate(R.menu.m_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        searchItem = menu.findItem(R.id.search);
        searchItem.setOnActionExpandListener(new SearchViewExpandListener(menu));

        searchView = (SearchView) searchItem.getActionView();
        searchView.setIconified(false);
        onQueryTextListener = new MuteableSearchViewOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                getFragment().onSearchQuerySubmitted(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                getFragment().onSearchQueryChanged(query);
                return false;
            }
        });
        searchView.setOnQueryTextListener(onQueryTextListener);
        searchView.setOnCloseListener(() -> {
            getFragment().onSearchClosed();
            return false;
        });

        getFragment().onOptionsMenuPrepared();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void showSearchQuery(final String value) {
        onQueryTextListener.mute();
        searchItem.expandActionView();
        searchView.setQuery(value, false);
        searchView.clearFocus();
        onQueryTextListener.unmute();
    }

    private static class MuteableSearchViewOnQueryTextListener implements SearchView.OnQueryTextListener {
        private final SearchView.OnQueryTextListener onQueryTextListener;
        private boolean mute;

        MuteableSearchViewOnQueryTextListener(final SearchView.OnQueryTextListener onQueryTextListener) {
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

    private static class SearchViewExpandListener implements MenuItem.OnActionExpandListener {
        private final Menu menu;

        SearchViewExpandListener(final Menu menu) {
            this.menu = menu;
        }

        @Override
        public boolean onMenuItemActionExpand(final MenuItem item) {
            onMenuItemAction(item, true);
            return true;
        }

        @Override
        public boolean onMenuItemActionCollapse(final MenuItem item) {
            onMenuItemAction(item, false);
            return true;
        }

        private void onMenuItemAction(final MenuItem item, final boolean expand) {
            for (int i = 0; i < menu.size(); i++) {
                final MenuItem menuItem = menu.getItem(i);
                if (menuItem.getItemId() != item.getItemId()) {
                    menuItem.setVisible(!expand);
                }
            }
        }
    }
}
