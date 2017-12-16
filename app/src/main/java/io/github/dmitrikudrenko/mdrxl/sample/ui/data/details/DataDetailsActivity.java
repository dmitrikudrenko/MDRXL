package io.github.dmitrikudrenko.mdrxl.sample.ui.data.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxActivity;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.SampleApplication;

import javax.inject.Inject;
import javax.inject.Provider;

public class DataDetailsActivity extends RxActivity implements DataDetailsView {
    @Inject
    Provider<DataDetailsPresenter> samplePresenterProvider;

    @InjectPresenter
    DataDetailsPresenter dataDetailsPresenter;

    private SwipeRefreshLayout refreshLayout;

    private EditText dataIdView;
    private EditText dataNameView;
    private EditText dataFirstAttributeView;
    private EditText dataSecondAttributeView;
    private EditText dataThirdAttributeView;

    private View focusedView;

    @ProvidePresenter
    public DataDetailsPresenter providePresenter() {
        if (dataDetailsPresenter == null) {
            dataDetailsPresenter = samplePresenterProvider.get();
            dataDetailsPresenter.setId(getIntent().getLongExtra("id", -1));
        }
        return dataDetailsPresenter;
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

        refreshLayout.setOnRefreshListener(() -> dataDetailsPresenter.onRefresh());
        setupInputView(dataNameView, Fields.NAME);
        setupInputView(dataFirstAttributeView, Fields.FIRST_ATTRIBUTE);
        setupInputView(dataSecondAttributeView, Fields.SECOND_ATTRIBUTE);
        setupInputView(dataThirdAttributeView, Fields.THIRD_ATTRIBUTE);
    }

    private void setupInputView(final EditText inputView, final String tag) {
        inputView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                focusedView = inputView;
            } else {
                if (focusedView == inputView) {
                    focusedView = null;
                }
                dataDetailsPresenter.onRefresh();
            }
        });
        inputView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                dataDetailsPresenter.onDataChanged(tag, v.getText().toString());
                v.clearFocus();
            }
            return false;
        });
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        dataDetailsPresenter = (DataDetailsPresenter) getLastCustomNonConfigurationInstance();
        SampleApplication.get().plus(new Module()).inject(this);
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
        return dataDetailsPresenter;
    }

    private void showToast(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public static Intent intent(final Context context, final long id) {
        return new Intent(context, DataDetailsActivity.class)
                .putExtra("id", id);
    }

    @dagger.Module
    public class Module {
        @Provides
        RxLoaderManager provideLoaderManager() {
            return new RxLoaderManager(getSupportLoaderManager());
        }
    }

    @Subcomponent(modules = Module.class)
    public interface Component {
        void inject(DataDetailsActivity activity);
    }
}
