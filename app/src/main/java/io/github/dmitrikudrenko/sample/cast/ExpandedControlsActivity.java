package io.github.dmitrikudrenko.sample.cast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.media.widget.ExpandedControllerActivity;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.sample.GeraltApplication;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.di.OptionsMenu;
import io.github.dmitrikudrenko.sample.ui.navigation.Router;

import javax.inject.Inject;

public class ExpandedControlsActivity extends ExpandedControllerActivity {
    @Inject
    @OptionsMenu
    int optionsMenu;

    @Inject
    Router router;

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        GeraltApplication.get().plus(new Module()).inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(optionsMenu, menu);
        CastButtonFactory.setUpMediaRouteButton(this, menu, R.id.media_route_menu_item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.queue) {
            router.openVideoQueue();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @dagger.Module
    public class Module {
        @OptionsMenu
        @Provides
        int provideOptionsMenu() {
            return R.menu.m_expanded_controllers;
        }

        @Provides
        AppCompatActivity provideActivity() {
            return ExpandedControlsActivity.this;
        }
    }

    @Subcomponent(modules = Module.class)
    public interface Component {
        void inject(ExpandedControlsActivity activity);
    }
}
