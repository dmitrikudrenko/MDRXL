package io.github.dmitrikudrenko.mdrxl.sample.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxActivity;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.SampleApplication;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

public class SampleActivity extends RxActivity implements SampleView {
    @Inject
    Provider<SamplePresenter> presenterProvider;

    @Nullable
    @InjectPresenter
    SamplePresenter presenter;

    private SwipeRefreshLayout refreshLayout;
    private EditText contentView;

    private boolean hasContentViewFocus;

    @ProvidePresenter
    public SamplePresenter providePresenter() {
        if (presenter == null) {
            presenter = presenterProvider.get();
        }
        return presenter;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_sample);
        refreshLayout = findViewById(R.id.refresh_layout);
        contentView = findViewById(R.id.content);
        refreshLayout.setOnRefreshListener(() -> presenter.onRefresh());
        contentView.setOnFocusChangeListener((v, hasFocus) -> hasContentViewFocus = hasFocus);
        contentView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.onDataChanged(v.getText().toString());
                contentView.clearFocus();
            }
            return false;
        });
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        presenter = (SamplePresenter) getLastCustomNonConfigurationInstance();
        SampleApplication.get().plus(new SampleModule()).inject(this);
    }

    @Override
    public void startLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void stopLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showData(final String data) {
        if (!hasContentViewFocus) {
            contentView.setText(data);
        }
    }

    @Override
    public void showError(final String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Module
    public class SampleModule {
        @Provides
        RxLoaderManager provideLoaderManager() {
            return new RxLoaderManager(getSupportLoaderManager());
        }
    }

    @Subcomponent(modules = SampleModule.class)
    public interface SampleComponent {
        void inject(SampleActivity activity);
    }
}
