package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list;

import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import butterknife.BindView;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.BaseFragmentHolderRxActivity;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.SearchableController;
import io.github.dmitrikudrenko.mdrxl.sample.ui.settings.SettingsActivity;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ui.MuteableSearchViewOnQueryTextListener;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ui.SearchViewExpandListener;

import javax.annotation.Nullable;

public class GeraltWomenActivity extends BaseFragmentHolderRxActivity<GeraltWomenFragment>
        implements SearchableController {
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

    @Override
    public void showSearchQuery(final String value) {
        onQueryTextListener.mute();
        searchItem.expandActionView();
        searchView.setQuery(value, false);
        searchView.clearFocus();
        onQueryTextListener.unmute();
    }
}
