package io.github.dmitrikudrenko.mdrxl.sample.ui.women.photos;

import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.di.FragmentScope;
import io.github.dmitrikudrenko.mdrxl.sample.di.woman.WomanId;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands.GeraltWomanPhotosUpdateCommand;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomanPhotoCursor;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomanPhotoLoaderFactory;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.ViewPagerAdapterController;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ui.messages.MessageFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.AbstractList;

@FragmentScope
@InjectViewState
public class GeraltWomanPhotosPresenter extends RxPresenter<GeraltWomanPhotosView>
        implements ViewPagerAdapterController<String> {
    private static final int LOADER_ID = RxLoaders.generateId();

    private final GeraltWomanPhotoLoaderFactory loaderFactory;
    private final Provider<GeraltWomanPhotosUpdateCommand> updateCommandProvider;
    private final MessageFactory messageFactory;
    private final long womanId;

    private Photos photos;

    @Inject
    GeraltWomanPhotosPresenter(final RxLoaderManager loaderManager,
                               final GeraltWomanPhotoLoaderFactory loaderFactory,
                               final Provider<GeraltWomanPhotosUpdateCommand> updateCommandProvider,
                               final MessageFactory messageFactory,
                               @WomanId final long womanId) {
        super(loaderManager);
        this.loaderFactory = loaderFactory;
        this.updateCommandProvider = updateCommandProvider;
        this.messageFactory = messageFactory;
        this.womanId = womanId;
    }

    @Override
    public void attachView(final GeraltWomanPhotosView view) {
        super.attachView(view);
        getLoaderManager().init(LOADER_ID, null, new LoaderCallbacks());
        updateCommandProvider.get().updatePhotos(womanId);
    }

    @Override
    public int getCount() {
        return photos != null ? photos.size() : 0;
    }

    @Override
    public String getData(final int position) {
        return photos.get(position).getUrl();
    }

    private void onDataLoaded(final GeraltWomanPhotoCursor data) {
        this.photos = new Photos(data);
        getViewState().notifyDataChanged();
    }

    private class LoaderCallbacks extends RxLoaderCallbacks<GeraltWomanPhotoCursor> {

        @Override
        protected RxLoader<GeraltWomanPhotoCursor> getLoader(final int id, final RxLoaderArguments args) {
            return loaderFactory.create(womanId);
        }

        @Override
        protected void onSuccess(final int id, final GeraltWomanPhotoCursor data) {
            onDataLoaded(data);
        }
        @Override
        protected void onError(final int id, final Throwable error) {
            messageFactory.showError(error.getMessage());
        }

    }

    static class Photos extends AbstractList<GeraltWomanPhotoCursor> {
        final GeraltWomanPhotoCursor cursor;

        Photos(final GeraltWomanPhotoCursor cursor) {
            this.cursor = cursor;
        }

        @Override
        public GeraltWomanPhotoCursor get(final int index) {
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
