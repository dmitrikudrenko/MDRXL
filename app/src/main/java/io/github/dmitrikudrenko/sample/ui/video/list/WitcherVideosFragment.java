package io.github.dmitrikudrenko.sample.ui.video.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.cast.CastButtonFactoryExt;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.sample.GeraltApplication;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.di.OptionsMenu;
import io.github.dmitrikudrenko.sample.ui.base.BaseRxFragment;
import io.github.dmitrikudrenko.sample.ui.navigation.Router;
import io.github.dmitrikudrenko.sample.ui.video.list.adapter.WitcherVideoAdapter;
import io.github.dmitrikudrenko.sample.ui.video.list.adapter.WitcherVideoAdapterFactory;
import io.github.dmitrikudrenko.sample.utils.ui.ClickInfo;
import io.github.dmitrikudrenko.sample.utils.ui.DividerItemDecoration;
import io.github.dmitrikudrenko.sample.utils.ui.messages.MessageFactory;
import io.github.dmitrikudrenko.sample.utils.ui.messages.ToastFactory;

import javax.inject.Inject;
import javax.inject.Provider;

public class WitcherVideosFragment extends BaseRxFragment implements WitcherVideosView,
        CastingDialogFragment.OnActionSelectorListener {
    @Inject
    @InjectPresenter
    WitcherVideosPresenter presenter;

    @Inject
    Provider<DividerItemDecoration> decorationProvider;

    @Inject
    WitcherVideoAdapterFactory adapterFactory;

    @Inject
    @OptionsMenu
    int optionsMenu;

    @Inject
    Router router;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private WitcherVideoAdapter adapter;

    @ProvidePresenter
    public WitcherVideosPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        GeraltApplication.get().plus(new Module()).inject(this);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_videos, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = adapterFactory.create(
                (position, v) ->
                        presenter.onItemSelected(ClickInfo.clickInfo(position, v)),
                presenter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(decorationProvider.get());
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(() -> presenter.onRefresh());
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(optionsMenu, menu);
        CastButtonFactoryExt.setUpCastDependentButton(getContext(), menu, R.id.queue);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.queue) {
            router.openVideoQueue();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void notifyDataSetChanged(final DiffUtil.DiffResult result) {
        result.dispatchUpdatesTo(adapter);
    }

    @Override
    public void notifyDataChanged(final int position) {
        adapter.notifyItemChanged(position);
    }

    @Override
    public void showCastingDialog(final String thumbnail) {
        CastingDialogFragment.create(thumbnail).show(getChildFragmentManager(), "casting");
    }

    @Override
    public void onPlaySelected() {
        presenter.play();
    }

    @Override
    public void onQueueSelected() {
        presenter.addToQueue();
    }

    @Override
    public void startLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void stopLoading() {
        refreshLayout.setRefreshing(false);
    }

    @dagger.Module
    public class Module {
        @Provides
        RxLoaderManager provideLoaderManager() {
            return new RxLoaderManager(getLoaderManager());
        }

        @Provides
        DividerItemDecoration provideDividerItemDecoration() {
            final DividerItemDecoration d = new DividerItemDecoration(DividerItemDecoration.VERTICAL);
            d.setDividerSize((int) getResources().getDimension(R.dimen.video_divider_height));
            return d;
        }

        @Provides
        MessageFactory provideMessageFactory() {
            return new ToastFactory(getContext());
        }

        @OptionsMenu
        @Provides
        int provideOptionsMenu() {
            return R.menu.m_queue;
        }
    }

    @Subcomponent(modules = Module.class)
    public interface Component {
        void inject(WitcherVideosFragment fragment);
    }
}
