package io.github.dmitrikudrenko.mdrxl.sample.ui.women.photos;

import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.commands.CommandStarter;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxLoaderPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.di.FragmentScope;
import io.github.dmitrikudrenko.mdrxl.sample.di.woman.WomanId;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands.GeraltWomanPhotosUpdateCommandRequest;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.Gallery;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomanPhotoCursor;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomanPhotoLoaderFactory;
import io.github.dmitrikudrenko.mdrxl.sample.ui.base.ViewPagerAdapterController;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ui.ResourcesManager;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ui.messages.MessageFactory;

import javax.inject.Inject;
import java.util.AbstractList;

@FragmentScope
@InjectViewState
public class GeraltWomanPhotosPresenter extends RxLoaderPresenter<GeraltWomanPhotosView>
        implements ViewPagerAdapterController<String> {
    private static final int LOADER_ID = RxLoaders.generateId();
    private static final String TAG = "GWomanPhotosPresenter";

    private final GeraltWomanPhotoLoaderFactory loaderFactory;
    private final CommandStarter commandStarter;
    private final MessageFactory messageFactory;
    private final ResourcesManager resourcesManager;
    private final long womanId;

    private Photos photos;
    private int photosCount;

    @Inject
    GeraltWomanPhotosPresenter(final RxLoaderManager loaderManager,
                               final GeraltWomanPhotoLoaderFactory loaderFactory,
                               final CommandStarter commandStarter,
                               final MessageFactory messageFactory,
                               final ResourcesManager resourcesManager,
                               @WomanId final long womanId) {
        super(loaderManager);
        this.loaderFactory = loaderFactory;
        this.commandStarter = commandStarter;
        this.messageFactory = messageFactory;
        this.resourcesManager = resourcesManager;
        this.womanId = womanId;
    }

    @Override
    public void attachView(final GeraltWomanPhotosView view) {
        super.attachView(view);
        getLoaderManager().init(LOADER_ID, null, new LoaderCallbacks());
        commandStarter.execute(new GeraltWomanPhotosUpdateCommandRequest(womanId));
    }

    @Override
    public int getCount() {
        return photos != null ? photos.size() : 0;
    }

    @Override
    public String getData(final int position) {
        return photos.get(position).getUrl();
    }

    private void onDataLoaded(final Gallery data) {
        this.photos = new Photos(data.getCursor());
        this.photosCount = data.getCount();
        getViewState().notifyDataChanged();
        onGalleryScroll(0);
    }

    void onGalleryScroll(final int current) {
        getViewState().showPages(resourcesManager.getString(R.string.gallery_counter,
                current + 1, photosCount));
    }

    private class LoaderCallbacks extends RxLoaderCallbacks<Gallery> {

        @Override
        protected RxLoader<Gallery> getLoader(final int id, final RxLoaderArguments args) {
            return loaderFactory.create(womanId);
        }

        @Override
        protected void onSuccess(final int id, final Gallery data) {
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
