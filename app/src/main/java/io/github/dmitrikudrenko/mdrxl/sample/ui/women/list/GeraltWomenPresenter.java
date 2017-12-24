package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list;

import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenCursor;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenLoader;
import io.github.dmitrikudrenko.mdrxl.sample.utils.Strings;
import rx.subjects.BehaviorSubject;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.concurrent.TimeUnit;

@InjectViewState
public class GeraltWomenPresenter extends RxPresenter<GeraltWomenView> {
    private static final int LOADER_ID = RxLoaders.generateId();

    private final Provider<GeraltWomenLoader> loaderProvider;
    private final BehaviorSubject<String> searchQuerySubject = BehaviorSubject.create((String) null);

    private GeraltWomenLoader loader;

    @Inject
    GeraltWomenPresenter(final RxLoaderManager loaderManager,
                         final Provider<GeraltWomenLoader> loaderProvider) {
        super(loaderManager);
        this.loaderProvider = loaderProvider;
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

    void onItemSelected(final long id) {
        getViewState().openDataDetails(id);
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

    private class LoaderCallbacks extends RxLoaderCallbacks<GeraltWomenCursor> {
        @Override
        protected RxLoader<GeraltWomenCursor> getLoader(final int id, final RxLoaderArguments args) {
            getViewState().startLoading();
            return loaderProvider.get();
        }

        @Override
        protected void onSuccess(final int id, final GeraltWomenCursor data) {
            getViewState().stopLoading();
            getViewState().showData(data);
        }

        @Override
        protected void onError(final int id, final Throwable error) {
            getViewState().showError(error.getMessage());
        }
    }
}
