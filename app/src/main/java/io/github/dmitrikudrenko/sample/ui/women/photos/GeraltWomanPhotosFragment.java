package io.github.dmitrikudrenko.sample.ui.women.photos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.OnPageChange;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.SampleApplication;
import io.github.dmitrikudrenko.sample.di.FragmentScope;
import io.github.dmitrikudrenko.sample.ui.base.BaseRxFragment;
import io.github.dmitrikudrenko.sample.ui.women.photos.adapter.PhotosAdapter;
import io.github.dmitrikudrenko.sample.ui.women.photos.adapter.PhotosAdapterFactory;
import io.github.dmitrikudrenko.sample.utils.ui.messages.MessageFactory;
import io.github.dmitrikudrenko.sample.utils.ui.messages.ToastFactory;

import javax.inject.Inject;

public class GeraltWomanPhotosFragment extends BaseRxFragment implements GeraltWomanPhotosView {
    static GeraltWomanPhotosFragment create() {
        return new GeraltWomanPhotosFragment();
    }

    @Inject
    @InjectPresenter
    GeraltWomanPhotosPresenter presenter;

    @Inject
    PhotosAdapterFactory adapterFactory;

    @BindView(R.id.pager)
    ViewPager viewPager;

    private PhotosAdapter adapter;

    @ProvidePresenter
    public GeraltWomanPhotosPresenter providePresenter() {
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
        return inflater.inflate(R.layout.f_photos, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = adapterFactory.create(getChildFragmentManager(), presenter);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showPages(final String value) {
        ((GeraltWomanPhotosActivity) getActivity()).showPages(value);
    }

    @OnPageChange(R.id.pager)
    void onGalleryScroll() {
        presenter.onGalleryScroll(viewPager.getCurrentItem());
    }

    @dagger.Module
    public class Module {
        @Provides
        RxLoaderManager provideLoaderManager() {
            return new RxLoaderManager(getLoaderManager());
        }

        @Provides
        MessageFactory provideMessageFactory() {
            return new ToastFactory(getContext());
        }
    }

    @FragmentScope
    @Subcomponent(modules = Module.class)
    public interface Component {
        void inject(GeraltWomanPhotosFragment fragment);
    }
}
