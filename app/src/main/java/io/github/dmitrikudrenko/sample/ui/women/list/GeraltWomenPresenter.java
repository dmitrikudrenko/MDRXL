package io.github.dmitrikudrenko.sample.ui.women.list;

import android.support.v7.util.DiffUtil;
import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.core.commands.GeraltWomenUpdateCommandRequest;
import io.github.dmitrikudrenko.core.local.cursor.GeraltWomenCursor;
import io.github.dmitrikudrenko.core.local.loader.women.GeraltWomenLoader;
import io.github.dmitrikudrenko.mdrxl.commands.CommandStarter;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxLoaderPresenter;
import io.github.dmitrikudrenko.sample.di.MultiWindow;
import io.github.dmitrikudrenko.sample.ui.navigation.Router;
import io.github.dmitrikudrenko.sample.ui.women.list.adapter.GeraltWomanHolder;
import io.github.dmitrikudrenko.sample.utils.ui.ClickInfo;
import io.github.dmitrikudrenko.sample.utils.ui.messages.MessageFactory;
import io.github.dmitrikudrenko.utils.common.ListCursor;
import io.github.dmitrikudrenko.utils.common.ListDiffUtilCallback;
import io.github.dmitrikudrenko.utils.common.Strings;
import io.github.dmitrikudrenko.utils.ui.RecyclerViewAdapterController;
import rx.subjects.BehaviorSubject;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import java.util.concurrent.TimeUnit;

@InjectViewState
public class GeraltWomenPresenter extends RxLoaderPresenter<GeraltWomenView> implements RecyclerViewAdapterController<GeraltWomanHolder> {
    private static final int LOADER_ID = RxLoaders.generateId();
    private static final String TAG = "GeraltWomenPresenter";

    private final Provider<GeraltWomenLoader> loaderProvider;
    private final CommandStarter commandStarter;
    private final boolean multiWindow;
    private final Router router;
    private final MessageFactory messageFactory;

    private final BehaviorSubject<String> searchQuerySubject = BehaviorSubject.create((String) null);

    private GeraltWomenLoader loader;
    private GeraltWomen data;

    private long selectedItemId = Integer.MIN_VALUE;

    @Inject
    GeraltWomenPresenter(final RxLoaderManager loaderManager,
                         final Provider<GeraltWomenLoader> loaderProvider,
                         final CommandStarter commandStarter,
                         final Router router,
                         final MessageFactory messageFactory,
                         @MultiWindow final boolean multiWindow) {
        super(loaderManager);
        this.loaderProvider = loaderProvider;
        this.commandStarter = commandStarter;
        this.router = router;
        this.messageFactory = messageFactory;
        this.multiWindow = multiWindow;
        add(searchQuerySubject.filter(query -> loader != null)
                .debounce(200, TimeUnit.MILLISECONDS)
                .map(s -> Strings.isBlank(s) ? null : s)
                .distinctUntilChanged()
                .subscribe(query -> loader.setSearchQuery(query), error -> Log.e(TAG, error.getMessage(), error))
        );
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        onRefresh();
    }

    @Override
    public void attachView(final GeraltWomenView view) {
        super.attachView(view);
        loader = (GeraltWomenLoader) getLoaderManager().init(LOADER_ID, null, new LoaderCallbacks());
    }

    void onRefresh() {
        commandStarter.execute(new GeraltWomenUpdateCommandRequest());
    }

    void onItemSelected(final ClickInfo clickInfo) {
        final long itemId = getItemId(clickInfo.getPosition());
        if (multiWindow) {
            if (itemId != selectedItemId) {
                final long oldSelectedItemId = selectedItemId;
                this.selectedItemId = itemId;

                final int oldSelectedItemPosition = data.indexOf(oldSelectedItemId);
                if (oldSelectedItemPosition >= 0) {
                    getViewState().notifyDataChanged(oldSelectedItemPosition);
                }
                getViewState().notifyDataChanged(clickInfo.getPosition());

                router.openGeraltWoman(itemId, clickInfo);
            }
        } else {
            router.openGeraltWoman(itemId, clickInfo);
        }
    }

    void onSearchQuerySubmitted(@SuppressWarnings("unused") final String query) {
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
        holder.showPhoto(cursor.getAvatar(), cursor.getPlaceholder());
        holder.showName(cursor.getName());
        holder.showProfession(cursor.getProfession());
        holder.setSelected(selectedItemId == cursor.getId());
    }

    private void onDataLoaded(final GeraltWomen newData) {
        final GeraltWomen oldData = data != null ? data.copy() : null;
        final GeraltWomenDiffUtilCallback callback = new GeraltWomenDiffUtilCallback(oldData, newData);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);
        this.data = newData;

        getViewState().notifyDataChanged(diffResult);

        openFirstEntityIfShould();
    }

    private void openFirstEntityIfShould() {
        if (multiWindow && selectedItemId < 0 && getItemCount() > 0) {
            runOnUiThread(() -> onItemSelected(ClickInfo.clickInfo(0)));
        }
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
            getViewState().stopLoading();
            messageFactory.showError(error.getMessage());
        }

    }

    private static class GeraltWomen extends ListCursor<GeraltWomenCursor> {
        GeraltWomen(final GeraltWomenCursor cursor) {
            super(cursor);
        }

        GeraltWomen copy() {
            return new GeraltWomen(getCursor());
        }

        int indexOf(final long itemId) {
            int index = 0;
            for (final GeraltWomenCursor cursor : this) {
                if (cursor.getId() == itemId) {
                    return index;
                }
                index++;
            }
            return -1;
        }
    }

    private static class GeraltWomenDiffUtilCallback extends ListDiffUtilCallback<GeraltWomenCursor> {
        GeraltWomenDiffUtilCallback(@Nullable final GeraltWomen oldData, final GeraltWomen newData) {
            super(newData, oldData);
        }

        @Override
        public boolean areItemsTheSame(final GeraltWomenCursor oldItem, final GeraltWomenCursor newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(final GeraltWomenCursor oldItem, final GeraltWomenCursor newItem) {
            return oldItem.areContentsTheSame(newItem);
        }
    }
}
