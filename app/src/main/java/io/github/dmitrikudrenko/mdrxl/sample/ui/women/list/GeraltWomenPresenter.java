package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.di.Configuration;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenCursor;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenLoader;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.list.adapter.AdapterController;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.list.adapter.GeraltWomanHolder;
import io.github.dmitrikudrenko.mdrxl.sample.utils.Strings;
import rx.subjects.BehaviorSubject;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.AbstractList;
import java.util.concurrent.TimeUnit;

@InjectViewState
public class GeraltWomenPresenter extends RxPresenter<GeraltWomenView> implements AdapterController<GeraltWomanHolder> {
    private static final int LOADER_ID = RxLoaders.generateId();

    private final Provider<GeraltWomenLoader> loaderProvider;
    private final boolean multiWindow;
    private final BehaviorSubject<String> searchQuerySubject = BehaviorSubject.create((String) null);

    private GeraltWomenLoader loader;
    private GeraltWomen data;

    private int selectedItemPosition = Integer.MIN_VALUE;

    @Inject
    GeraltWomenPresenter(final RxLoaderManager loaderManager,
                         final Provider<GeraltWomenLoader> loaderProvider,
                         @Configuration final boolean multiWindow) {
        super(loaderManager);
        this.loaderProvider = loaderProvider;
        this.multiWindow = multiWindow;
        searchQuerySubject.filter(query -> loader != null)
                .debounce(200, TimeUnit.MILLISECONDS)
                .map(s -> Strings.isBlank(s) ? null : s)
                .distinctUntilChanged()
                .subscribe(query -> loader.setSearchQuery(query));
    }

    @Override
    public void attachView(final GeraltWomenView view) {
        super.attachView(view);
        loader = (GeraltWomenLoader) getLoaderManager().init(LOADER_ID, null, new LoaderCallbacks());
    }

    void onRefresh() {
        //???
        getViewState().stopLoading();
    }

    void onItemSelected(final int position) {
        final long itemId = getItemId(position);
        if (multiWindow) {
            if (position != selectedItemPosition) {
                final int oldSelectedItemPosition = selectedItemPosition;
                this.selectedItemPosition = position;

                getViewState().notifyDataChanged(oldSelectedItemPosition);
                getViewState().notifyDataChanged(position);

                getViewState().openDataDetails(itemId);
            }
        } else {
            getViewState().openDataDetails(itemId);
        }
    }

    void onSearchQuerySubmitted(final String query) {
        //do nothing
    }

    void onSearchQueryChanged(final String query) {
        searchQuerySubject.onNext(query);
    }

    void onSearchClosed() {
        loader.flushSearch();
    }

    @Override
    public long getItemId(final int position) {
        return data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public void bind(final GeraltWomanHolder holder, final int position) {
        final GeraltWomenCursor cursor = data.get(position);
        holder.showPhoto(cursor.getPhoto());
        holder.showName(cursor.getName());
        holder.showProfession(cursor.getProfession());
        holder.setSelected(selectedItemPosition == position);
    }

    private void onDataLoaded(final GeraltWomen data) {
        this.data = data;
        getViewState().notifyDataChanged();
    }

    void onOptionsMenuPrepared() {
        final String searchQuery = searchQuerySubject.getValue();
        if (Strings.isNotBlank(searchQuery)) {
            getViewState().showSearchQuery(searchQuery);
        }
    }

    private class LoaderCallbacks extends RxLoaderCallbacks<GeraltWomenCursor> {

        @Override
        protected RxLoader<GeraltWomenCursor> getLoader(final int id, final RxLoaderArguments args) {
            getViewState().startLoading();
            return loaderProvider.get();
        }

        @Override
        protected void onSuccess(final int id, final GeraltWomenCursor data) {
            getViewState().stopLoading();
            onDataLoaded(new GeraltWomen(data));
        }

        @Override
        protected void onError(final int id, final Throwable error) {
            getViewState().showError(error.getMessage());
        }

    }

    private static class GeraltWomen extends AbstractList<GeraltWomenCursor> {
        final GeraltWomenCursor cursor;

        GeraltWomen(final GeraltWomenCursor cursor) {
            this.cursor = cursor;
        }

        @Override
        public GeraltWomenCursor get(final int index) {
            if (cursor.isClosed()) {
                Log.d("GeraltWomenAdapter", "Cursor is closed: " + cursor.toString());
            }
            if (cursor.moveToPosition(index)) {
                return cursor;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int size() {
            return cursor.getCount();
        }
    }
}
