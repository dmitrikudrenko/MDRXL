package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list;

import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.BaseFragmentHolderRxActivity;
import io.github.dmitrikudrenko.mdrxl.sample.ui.navigation.GeraltWomenDetailsNavigation;
import io.github.dmitrikudrenko.mdrxl.sample.ui.settings.SettingsActivity;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.details.GeraltWomanActivity;

public class GeraltWomenActivity extends BaseFragmentHolderRxActivity<GeraltWomenFragment>
        implements GeraltWomenDetailsNavigation {

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
        final MenuItem item = menu.findItem(R.id.search);
        item.setOnActionExpandListener(new SearchViewExpandListener(menu));

        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        searchView.setOnCloseListener(() -> {
            getFragment().onSearchClosed();
            return false;
        });
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void navigateToGeraltWomanDetails(final long id) {
        startActivity(GeraltWomanActivity.intent(this, id));
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
