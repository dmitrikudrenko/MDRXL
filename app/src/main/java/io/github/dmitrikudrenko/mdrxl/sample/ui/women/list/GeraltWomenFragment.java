package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindColor;
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
import io.github.dmitrikudrenko.mdrxl.sample.ui.navigation.GeraltWomenDetailsNavigation;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.list.adapter.GeraltWomenAdapter;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.list.adapter.GeraltWomenAdapterFactory;

import javax.inject.Inject;
import javax.inject.Provider;

public class GeraltWomenFragment extends RxFragment implements GeraltWomenView {
    @Inject
    Provider<GeraltWomenPresenter> presenterProvider;

    @InjectPresenter
    GeraltWomenPresenter presenter;

    @Inject
    GeraltWomenAdapterFactory adapterFactory;

    @Inject
    Provider<DividerItemDecoration> decorationProvider;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindColor(R.color.list_divider)
    int listDividerColor;

    private GeraltWomenAdapter adapter;

    @ProvidePresenter
    GeraltWomenPresenter providePresenter() {
        if (presenter == null) {
            presenter = presenterProvider.get();
        }
        return presenter;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter == null) {
            presenter = (GeraltWomenPresenter) getLastCustomNonConfigurationInstance();
        }
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        SampleApplication.get().plus(new Module()).inject(this);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_women, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        adapter = adapterFactory.create(id -> presenter.onItemSelected(id), presenter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(decorationProvider.get());
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(() -> presenter.onRefresh());
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
    public void showError(final String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openDataDetails(final long id) {
        final FragmentActivity activity = getActivity();
        if (activity instanceof GeraltWomenDetailsNavigation) {
            ((GeraltWomenDetailsNavigation) activity).navigateToGeraltWomanDetails(id);
        }
    }

    public void onSearchQuerySubmitted(final String query) {
        presenter.onSearchQuerySubmitted(query);
    }

    public void onSearchQueryChanged(final String query) {
        presenter.onSearchQueryChanged(query);
    }

    public void onSearchClosed() {
        presenter.onSearchClosed();
    }

    @dagger.Module
    public class Module {
        @Provides
        RxLoaderManager provideLoaderManager() {
            return new RxLoaderManager(getLoaderManager());
        }

        @Provides
        DividerItemDecoration provideDividerItemDecoration() {
            final DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            itemDecoration.setDrawable(new ColorDrawable(listDividerColor));
            return itemDecoration;
        }
    }

    @Subcomponent(modules = Module.class)
    public interface Component {
        void inject(GeraltWomenFragment activity);
    }
}
