package io.github.dmitrikudrenko.mdrxl.sample.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
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

import javax.inject.Inject;
import javax.inject.Provider;

public class SampleActivity extends RxActivity implements SampleView {
    @Inject
    Provider<SamplePresenter> samplePresenterProvider;

    @Inject
    Provider<SettingsPresenter> settingsPresenterProvider;

    @InjectPresenter
    SamplePresenter samplePresenter;

    SettingsPresenter settingsPresenter;

    private SwipeRefreshLayout refreshLayout;

    private EditText dataIdView;
    private EditText dataNameView;
    private EditText dataFirstAttributeView;
    private EditText dataSecondAttributeView;
    private EditText dataThirdAttributeView;

    private View focusedView;

    @ProvidePresenter
    public SamplePresenter providePresenter() {
        if (samplePresenter == null) {
            samplePresenter = samplePresenterProvider.get();
        }
        return samplePresenter;
    }

    public SettingsPresenter getSettingsPresenter() {
        if (settingsPresenter == null) {
            settingsPresenter = settingsPresenterProvider.get();
        }
        return settingsPresenter;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_sample);
        refreshLayout = findViewById(R.id.refresh_layout);
        dataIdView = findViewById(R.id.id);
        dataNameView = findViewById(R.id.name);
        dataFirstAttributeView = findViewById(R.id.first_attribute);
        dataSecondAttributeView = findViewById(R.id.second_attribute);
        dataThirdAttributeView = findViewById(R.id.third_attribute);

        refreshLayout.setOnRefreshListener(() -> samplePresenter.onRefresh());
        setupInputView(dataNameView, Fields.NAME);
        setupInputView(dataFirstAttributeView, Fields.FIRST_ATTRIBUTE);
        setupInputView(dataSecondAttributeView, Fields.SECOND_ATTRIBUTE);
        setupInputView(dataThirdAttributeView, Fields.THIRD_ATTRIBUTE);

        final SettingsViewGroup settingsViewGroup = new SettingsViewGroup(findViewById(R.id.content));
        settingsViewGroup.attachPresenter(getSettingsPresenter());
    }

    private void setupInputView(final EditText inputView, final String tag) {
        inputView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                focusedView = inputView;
            } else {
                if (focusedView == inputView) {
                    focusedView = null;
                }
                samplePresenter.onRefresh();
            }
        });
        inputView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                samplePresenter.onDataChanged(tag, v.getText().toString());
                v.clearFocus();
            }
            return false;
        });
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        final PresenterContainer container = (PresenterContainer) getLastCustomNonConfigurationInstance();
        if (container != null) {
            samplePresenter = container.getSamplePresenter();
            settingsPresenter = container.getSettingsPresenter();
        }
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
    public void showId(final String value) {
        dataIdView.setText(value);
    }

    @Override
    public void showName(final String value) {
        if (focusedView != dataNameView) {
            dataNameView.setText(value);
        }
    }

    @Override
    public void showFirstAttribute(final String value) {
        if (focusedView != dataFirstAttributeView) {
            dataFirstAttributeView.setText(value);
        }
    }

    @Override
    public void showSecondAttribute(final String value) {
        if (focusedView != dataSecondAttributeView) {
            dataSecondAttributeView.setText(value);
        }
    }

    @Override
    public void showThirdAttribute(final String value) {
        if (focusedView != dataThirdAttributeView) {
            dataThirdAttributeView.setText(value);
        }
    }

    @Override
    public void showError(final String error) {
        showToast(error);
    }

    @Override
    public void showMessage(final String message) {
        showToast(message);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return new PresenterContainer(samplePresenter, settingsPresenter);
    }

    private void showToast(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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

    private static class PresenterContainer {
        private final SamplePresenter samplePresenter;
        private final SettingsPresenter settingsPresenter;

        PresenterContainer(final SamplePresenter samplePresenter,
                           final SettingsPresenter settingsPresenter) {
            this.samplePresenter = samplePresenter;
            this.settingsPresenter = settingsPresenter;
        }

        SamplePresenter getSamplePresenter() {
            return samplePresenter;
        }

        SettingsPresenter getSettingsPresenter() {
            return settingsPresenter;
        }
    }
}
