package io.github.dmitrikudrenko.mdrxl.sample.ui.data.list;

import android.content.Intent;
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
        getMenuInflater().inflate(R.menu.m_list, menu);
        return super.onCreateOptionsMenu(menu);
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
