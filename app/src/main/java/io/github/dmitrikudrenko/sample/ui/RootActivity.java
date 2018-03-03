package io.github.dmitrikudrenko.sample.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.mvp.RxActivity;
import io.github.dmitrikudrenko.sample.GeraltApplication;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.di.MultiWindow;
import io.github.dmitrikudrenko.sample.utils.ui.view.BottomNavigationView;
import io.github.dmitrikudrenko.sample.utils.ui.view.INavigationView;

import javax.inject.Inject;

public class RootActivity extends RxActivity implements RootView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigation_view)
    INavigationView navigationView;

    @Inject
    RootActivity_NavigationViewDelegateFactory navigationViewDelegateFactory;

    @Inject
    @InjectPresenter
    RootPresenter presenter;

    @ProvidePresenter
    RootPresenter providePresenter() {
        return presenter;
    }

    private NavigationViewDelegate navigationViewDelegate;

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        GeraltApplication.get().plus(new Module()).inject(this);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_navigation);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        navigationView.setOnNavigationItemSelectedListener(this::onMenuItemSelected);
        navigationViewDelegate = navigationViewDelegateFactory.create(navigationView);
    }

    private boolean onMenuItemSelected(final MenuItem menuItem) {
        final int itemId = menuItem.getItemId();
        switch (itemId) {
            case R.id.women:
                presenter.onWomenClicked();
                return true;
            case R.id.video:
                presenter.onVideoClicked();
                return true;
            case R.id.settings:
                presenter.onSettingsClicked();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void setWomenSectionSelected() {
        navigationView.setItemSelected(R.id.women);
    }

    public void showNavigationView() {
        navigationViewDelegate.onShowNavigationViewCalled();
    }

    public void hideNavigationView() {
        navigationViewDelegate.onHideNavigationViewCalled();
    }

    @dagger.Module
    public class Module {

    }

    @Subcomponent(modules = Module.class)
    public interface Component {
        void inject(RootActivity activity);
    }

    @AutoFactory
    static class NavigationViewDelegate {
        private final boolean multiWindow;
        private final INavigationView view;

        NavigationViewDelegate(@Provided @MultiWindow final boolean multiWindow,
                               final INavigationView view) {
            this.multiWindow = multiWindow;
            this.view = view;
        }

        void onShowNavigationViewCalled() {
            if (!multiWindow) {
                final BottomNavigationView bottomNavigationView = (BottomNavigationView) view;
                bottomNavigationView.postDelayed(() ->
                        bottomNavigationView.setVisibility(View.VISIBLE), 200);
            }
        }

        void onHideNavigationViewCalled() {
            if (!multiWindow) {
                ((BottomNavigationView) view).setVisibility(View.GONE);
            }
        }
    }
}
