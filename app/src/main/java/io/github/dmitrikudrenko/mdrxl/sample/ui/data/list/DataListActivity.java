package io.github.dmitrikudrenko.mdrxl.sample.ui.data.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.mvp.RxActivity;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.SampleApplication;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.DataCursor;

import javax.inject.Inject;
import javax.inject.Provider;

public class DataListActivity extends RxActivity implements DataListView {

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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_list);

        refreshLayout = findViewById(R.id.refresh_layout);
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new DataAdapter(id -> presenter.onItemSelected(id));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showData(final DataCursor cursor) {
        adapter.set(cursor);
    }

    @Override
    protected void beforeOnCreate(final Bundle savedInstanceState) {
        SampleApplication.get().plus(new DataListModule()).inject(this);
    }

    @Module
    public class DataListModule {
        @Provides
        RxLoaderManager provideLoaderManager() {
            return new RxLoaderManager(getSupportLoaderManager());
        }
    }

    @Subcomponent(modules = DataListModule.class)
    public interface DataListComponent {
        void inject(DataListActivity activity);
    }
}
