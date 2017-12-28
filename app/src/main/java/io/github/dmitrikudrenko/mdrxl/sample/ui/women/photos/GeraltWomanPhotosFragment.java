package io.github.dmitrikudrenko.mdrxl.sample.ui.women.photos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import io.github.dmitrikudrenko.mdrxl.sample.di.FragmentScope;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomanPhotoCursor;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.photos.adapter.PhotosAdapter;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.photos.adapter.PhotosAdapterFactory;

import javax.inject.Inject;
import javax.inject.Provider;

public class GeraltWomanPhotosFragment extends RxFragment implements GeraltWomanPhotosView {
    static GeraltWomanPhotosFragment create() {
        return new GeraltWomanPhotosFragment();
    }

    @Inject
    Provider<GeraltWomanPhotosPresenter> presenterProvider;

    @InjectPresenter
    GeraltWomanPhotosPresenter presenter;

    @Inject
    PhotosAdapterFactory adapterFactory;

    @BindView(R.id.pager)
    ViewPager viewPager;

    private PhotosAdapter adapter;

    @ProvidePresenter
    public GeraltWomanPhotosPresenter providePresenter() {
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
            presenter = (GeraltWomanPhotosPresenter) getLastCustomNonConfigurationInstance();
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_photos, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        adapter = adapterFactory.create(getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void showError(final String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showData(final GeraltWomanPhotoCursor cursor) {
        adapter.setPhotos(cursor);
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
        void inject(GeraltWomanPhotosFragment fragment);
    }
}
