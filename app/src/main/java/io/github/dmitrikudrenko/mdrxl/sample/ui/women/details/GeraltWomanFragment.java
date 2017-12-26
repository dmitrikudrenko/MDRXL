package io.github.dmitrikudrenko.mdrxl.sample.ui.women.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxFragment;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.SampleApplication;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.details.di.WomanId;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.details.di.WomanScope;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ImageLoader;

import javax.inject.Inject;
import javax.inject.Provider;

public class GeraltWomanFragment extends RxFragment implements GeraltWomanView {
    private static final String ARG_ID = "arg_id";

    @Inject
    Provider<GeraltWomanPresenter> samplePresenterProvider;

    @Inject
    ImageLoader imageLoader;

    @InjectPresenter
    GeraltWomanPresenter geraltWomanPresenter;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.photo)
    ImageView photoView;
    @BindView(R.id.name)
    EditText nameView;
    @BindView(R.id.profession)
    EditText professionView;
    @BindView(R.id.hair_color)
    EditText hairColorView;

    public static GeraltWomanFragment create(final long id) {
        final GeraltWomanFragment fragment = new GeraltWomanFragment();
        final Bundle args = new Bundle();
        args.putLong(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    public GeraltWomanPresenter providePresenter() {
        if (geraltWomanPresenter == null) {
            geraltWomanPresenter = samplePresenterProvider.get();
        }
        return geraltWomanPresenter;
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        SampleApplication.get().plus(new Module(getArguments().getLong(ARG_ID))).inject(this);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (geraltWomanPresenter == null) {
            geraltWomanPresenter = (GeraltWomanPresenter) getLastCustomNonConfigurationInstance();
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_woman, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        refreshLayout.setOnRefreshListener(() -> geraltWomanPresenter.onRefresh());
        setupInputView(nameView, Fields.NAME);
        setupInputView(professionView, Fields.PROFESSION);
        setupInputView(hairColorView, Fields.HAIR_COLOR);
    }

    private void setupInputView(final EditText inputView, final String tag) {
        inputView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                geraltWomanPresenter.onDataChanged(tag, v.getText().toString());
                v.clearFocus();
            }
            return false;
        });
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
    public void showName(final String value) {
        nameView.setText(value);
    }

    @Override
    public void showPhoto(final String value) {
        imageLoader.loadPhotoInto(value, photoView);
    }

    @Override
    public void showProfession(final String value) {
        professionView.setText(value);
    }

    @Override
    public void showHairColor(final String value) {
        hairColorView.setText(value);
    }

    @Override
    public void showError(final String error) {
        showToast(error);
    }

    @Override
    public void showMessage(final String message) {
        showToast(message);
    }

    private void showToast(final String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @dagger.Module
    public class Module {
        final long id;

        public Module(final long id) {
            this.id = id;
        }

        @WomanId
        @Provides
        long provideId() {
            return id;
        }

        @Provides
        RxLoaderManager provideLoaderManager() {
            return new RxLoaderManager(getLoaderManager());
        }
    }

    @WomanScope
    @Subcomponent(modules = Module.class)
    public interface Component {
        void inject(GeraltWomanFragment activity);
    }
}
