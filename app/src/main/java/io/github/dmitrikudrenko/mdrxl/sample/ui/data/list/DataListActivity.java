package io.github.dmitrikudrenko.mdrxl.sample.ui.data.list;

import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.BaseFragmentHolderRxActivity;
import io.github.dmitrikudrenko.mdrxl.sample.ui.data.details.DataDetailsActivity;
import io.github.dmitrikudrenko.mdrxl.sample.ui.navigation.DataDetailsNavigation;
import io.github.dmitrikudrenko.mdrxl.sample.ui.settings.SettingsActivity;

public class DataListActivity extends BaseFragmentHolderRxActivity<DataListFragment>
        implements DataDetailsNavigation {

    @Override
    protected DataListFragment createFragment() {
        return new DataListFragment();
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
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextFocusChangeListener((view, queryTextFocused) -> {
            if (!queryTextFocused) {
                searchView.setIconified(true);
            }
        });
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
    public void navigateToDataDetails(final long id) {
        startActivity(DataDetailsActivity.intent(this, id));
    }
}
