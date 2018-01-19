package io.github.dmitrikudrenko.sample.utils.ui;

import android.view.Menu;
import android.view.MenuItem;

public class SearchViewExpandListener implements MenuItem.OnActionExpandListener {
    private final Menu menu;

    public SearchViewExpandListener(final Menu menu) {
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
