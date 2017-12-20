package io.github.dmitrikudrenko.mdrxl.sample.ui.data.list;

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
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxFragment;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.SampleApplication;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.DataCursor;
import io.github.dmitrikudrenko.mdrxl.sample.ui.navigation.DataDetailsNavigation;

import javax.inject.Inject;
import javax.inject.Provider;

public class DataListFragment extends RxFragment implements DataListView {
    @InjectPresenter
    DataListPresenter presenter;

    @Inject
    Provider<DataListPresenter> presenterProvider;

    private SwipeRefreshLayout refreshLayout;

    private DataAdapter adapter;

    @ProvidePresenter
    DataListPresenter providePresenter() {
        if (presenter == null) {
            presenter = presenterProvider.get();
        }
        return presenter;
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        SampleApplication.get().plus(new Module()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_list, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        refreshLayout = view.findViewById(R.id.refresh_layout);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new DataAdapter(id -> presenter.onItemSelected(id));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
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
    public void showData(final DataCursor cursor) {
        adapter.set(cursor);
    }

    @Override
    public void openDataDetails(final long id) {
        final FragmentActivity activity = getActivity();
        if (activity instanceof DataDetailsNavigation) {
            ((DataDetailsNavigation) activity).navigateToDataDetails(id);
        }
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
        void inject(DataListFragment activity);
    }
}
