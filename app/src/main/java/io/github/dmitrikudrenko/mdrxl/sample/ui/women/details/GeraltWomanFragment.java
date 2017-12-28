package io.github.dmitrikudrenko.mdrxl.sample.ui.women.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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
import butterknife.OnClick;
import butterknife.Optional;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxFragment;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.SampleApplication;
import io.github.dmitrikudrenko.mdrxl.sample.di.FragmentScope;
import io.github.dmitrikudrenko.mdrxl.sample.ui.navigation.GeraltWomanPhotosNavigation;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ImageLoader;

import javax.inject.Inject;
import javax.inject.Provider;

public class GeraltWomanFragment extends RxFragment implements GeraltWomanView {
    @Inject
    Provider<GeraltWomanPresenter> presenterProvider;

    @InjectPresenter
    GeraltWomanPresenter presenter;

    @Inject
    ImageLoader imageLoader;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @Nullable
    @BindView(R.id.photo)
    ImageView photoView;
    @BindView(R.id.name)
    EditText nameView;
    @BindView(R.id.profession)
    EditText professionView;
    @BindView(R.id.hair_color)
    EditText hairColorView;

    public static GeraltWomanFragment create() {
        return new GeraltWomanFragment();
    }

    @ProvidePresenter
    public GeraltWomanPresenter providePresenter() {
        if (presenter == null) {
            presenter = presenterProvider.get();
        }
        return presenter;
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        SampleApplication.getWomanComponent().plus(new Module()).inject(this);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter == null) {
            presenter = (GeraltWomanPresenter) getLastCustomNonConfigurationInstance();
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

        refreshLayout.setOnRefreshListener(() -> presenter.onRefresh());
        setupInputView(nameView, Fields.NAME);
        setupInputView(professionView, Fields.PROFESSION);
        setupInputView(hairColorView, Fields.HAIR_COLOR);
    }

    private void setupInputView(final EditText inputView, final String tag) {
        inputView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.onDataChanged(tag, v.getText().toString());
                v.clearFocus();
            }
            return false;
        });
    }

    @Optional
    @OnClick(R.id.photo)
    void onPhotoClicked() {
        presenter.onPhotoClicked();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
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
        if (photoView != null) {
            imageLoader.loadPhotoInto(value, photoView);
        } else {
            ((GeraltWomanActivity) getActivity()).loadPhotoIntoToolbar(value);
        }
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

    @Override
    public void openPhotoGallery() {
        final FragmentActivity activity = getActivity();
        if (activity instanceof GeraltWomanPhotosNavigation) {
            ((GeraltWomanPhotosNavigation) activity).navigateToGeraltWomanPhotos();
        }
    }

    private void showToast(final String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @dagger.Module
    public class Module {
        @Provides
        RxLoaderManager provideLoaderManager() {
            return new RxLoaderManager(getLoaderManager());
        }
    }

    @FragmentScope
    @Subcomponent(modules = Module.class)
    public interface Component {
        void inject(GeraltWomanFragment fragment);
    }
}
