package io.github.dmitrikudrenko.sample.ui.women.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxActivity;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.SampleApplication;
import io.github.dmitrikudrenko.sample.di.FragmentScope;
import io.github.dmitrikudrenko.sample.ui.base.BaseRxFragment;
import io.github.dmitrikudrenko.sample.utils.ui.images.ImageLoader;
import io.github.dmitrikudrenko.sample.utils.ui.messages.MessageFactory;
import io.github.dmitrikudrenko.sample.utils.ui.messages.ToastFactory;

import javax.inject.Inject;

public class GeraltWomanFragment extends BaseRxFragment implements GeraltWomanView {
    @Inject
    @InjectPresenter
    GeraltWomanPresenter presenter;

    @Inject
    ImageLoader imageLoader;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @Nullable
    @BindView(R.id.name)
    EditText nameView;
    @BindView(R.id.profession)
    EditText professionView;
    @BindView(R.id.hair_color)
    EditText hairColorView;
    @Nullable
    @BindView(R.id.gallery)
    View galleryView;

    public static GeraltWomanFragment create() {
        return new GeraltWomanFragment();
    }

    @ProvidePresenter
    public GeraltWomanPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        SampleApplication.getWomanComponent().plus(new Module()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_woman, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout.setOnRefreshListener(() -> presenter.onRefresh());
        setupInputView(nameView, Fields.NAME);
        setupInputView(professionView, Fields.PROFESSION);
        setupInputView(hairColorView, Fields.HAIR_COLOR);
    }

    private void setupInputView(@Nullable final EditText inputView, final String tag) {
        if (inputView == null) {
            return;
        }

        inputView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.onDataChanged(tag, v.getText().toString());
                v.clearFocus();
            }
            return false;
        });
    }

    @Optional
    @OnClick(R.id.gallery)
    void onPhotoClicked() {
        presenter.onPhotoClicked();
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
        if (nameView != null) {
            nameView.setText(value);
        } else {
            ((GeraltWomanActivity) getActivity()).showTitle(value);
        }
    }

    @Override
    public void showPhoto(final String value) {
        if (galleryView == null) {
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

    @dagger.Module
    public class Module {
        @Provides
        RxLoaderManager provideLoaderManager() {
            return new RxLoaderManager(getLoaderManager());
        }

        @Provides
        RxActivity provideActivity() {
            return (RxActivity) getActivity();
        }

        @Provides
        MessageFactory provideMessageFactory() {
            return new ToastFactory(getContext());
        }
    }

    @FragmentScope
    @Subcomponent(modules = Module.class)
    public interface Component {
        void inject(GeraltWomanFragment fragment);
    }
}
