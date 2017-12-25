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
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.squareup.picasso.Picasso;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxFragment;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.SampleApplication;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ImageTransformer;

import javax.inject.Inject;
import javax.inject.Provider;

public class GeraltWomanFragment extends RxFragment implements GeraltWomanView {
    private static final String ARG_ID = "arg_id";

    @Inject
    Provider<GeraltWomanPresenter> samplePresenterProvider;

    @Inject
    Picasso picasso;

    @Inject
    ImageTransformer imageTransformer;

    @InjectPresenter
    GeraltWomanPresenter geraltWomanPresenter;

    private SwipeRefreshLayout refreshLayout;

    private ImageView photoView;
    private EditText nameView;
    private EditText professionView;
    private EditText hairColorView;

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
            geraltWomanPresenter.setId(getArguments().getLong(ARG_ID));
        }
        return geraltWomanPresenter;
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        SampleApplication.get().plus(new Module()).inject(this);
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
        refreshLayout = view.findViewById(R.id.refresh_layout);
        photoView = view.findViewById(R.id.photo);
        nameView = view.findViewById(R.id.name);
        professionView = view.findViewById(R.id.hair_color);
        hairColorView = view.findViewById(R.id.profession);

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
        imageTransformer.photoTransform(picasso.load(value)).into(photoView);
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
        @Provides
        RxLoaderManager provideLoaderManager() {
            return new RxLoaderManager(getLoaderManager());
        }
    }

    @Subcomponent(modules = Module.class)
    public interface Component {
        void inject(GeraltWomanFragment activity);
    }
}
