package io.github.dmitrikudrenko.sample.ui.video.queue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.sample.GeraltApplication;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.di.FragmentScope;
import io.github.dmitrikudrenko.sample.ui.base.BaseRxFragment;
import io.github.dmitrikudrenko.sample.ui.video.queue.adapter.VideoQueueAdapter;
import io.github.dmitrikudrenko.sample.ui.video.queue.adapter.VideoQueueAdapterFactory;
import io.github.dmitrikudrenko.sample.utils.ui.ClickInfo;
import io.github.dmitrikudrenko.sample.utils.ui.messages.MessageFactory;
import io.github.dmitrikudrenko.sample.utils.ui.messages.ToastFactory;

import javax.inject.Inject;

public class VideoQueueFragment extends BaseRxFragment implements VideoQueueView {
    @Inject
    @InjectPresenter
    VideoQueuePresenter presenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    VideoQueueAdapterFactory adapterFactory;

    private VideoQueueAdapter adapter;

    @ProvidePresenter
    public VideoQueuePresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        GeraltApplication.get().plus(new Module()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_video_queue, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = adapterFactory.create(presenter, (position, v) ->
                presenter.onQueueItemSelected(ClickInfo.clickInfo(position, v)), presenter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void notifyDataChanged(final DiffUtil.DiffResult result) {
        result.dispatchUpdatesTo(adapter);
    }

    @Override
    public void notifyDataChanged(final int position) {
        adapter.notifyItemChanged(position);
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
        void inject(VideoQueueFragment fragment);
    }
}
