package io.github.dmitrikudrenko.mdrxl.sample.ui.women.details;

import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.model.UpdateModel;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.GeraltWomenStorageCommand;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomanLoader;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenCursor;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.details.di.WomanId;
import io.github.dmitrikudrenko.mdrxl.sample.ui.women.details.di.WomanScope;
import rx.android.schedulers.AndroidSchedulers;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

@WomanScope
@InjectViewState
public class GeraltWomanPresenter extends RxPresenter<GeraltWomanView> {
    private static final String ARG_ID = "id";
    private static final int LOADER_ID = RxLoaders.generateId();

    private final Provider<GeraltWomanLoader> loaderProvider;
    private final Provider<GeraltWomenStorageCommand> storageCommandProvider;
    private final long id;

    @Nullable
    private GeraltWomenCursor data;

    @Inject
    GeraltWomanPresenter(final RxLoaderManager loaderManager,
                         final Provider<GeraltWomanLoader> loaderProvider,
                         final Provider<GeraltWomenStorageCommand> storageCommandProvider,
                         @WomanId final long id) {
        super(loaderManager);
        this.loaderProvider = loaderProvider;
        this.storageCommandProvider = storageCommandProvider;
        this.id = id;
    }

    @Override
    public void attachView(final GeraltWomanView view) {
        super.attachView(view);
        final RxLoaderArguments args = new RxLoaderArguments();
        args.putLong(ARG_ID, id);
        getLoaderManager().init(LOADER_ID, args, new LoaderCallbacks());
    }

    void onRefresh() {
        //???
        getViewState().stopLoading();
        notifyViewState(data);
    }

    void onDataChanged(@GeraltWomanView.Fields final String field, final String value) {
        final GeraltWomenStorageCommand geraltWomenStorageCommand = storageCommandProvider.get();
        geraltWomenStorageCommand.save(createUpdateModel(field, value))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().showMessage("GeraltWoman updated"),
                        error -> {
                            getViewState().showError(error.getMessage());
                            getLoaderManager().getLoader(LOADER_ID).onContentChanged();
                        });
    }

    private UpdateModel createUpdateModel(final String field, final String value) {
        return UpdateModel.create(id, field, value);
    }

    private void onDataLoaded(final GeraltWomenCursor data) {
        this.data = data;
        data.moveToFirst();
        notifyViewState(data);
    }

    private void notifyViewState(@Nullable final GeraltWomenCursor data) {
        if (data == null) {
            return;
        }

        getViewState().showName(data.getName());
        getViewState().showPhoto(data.getPhoto());
        getViewState().showProfession(data.getProfession());
        getViewState().showHairColor(data.getHairColor());
    }

    private class LoaderCallbacks extends RxLoaderCallbacks<GeraltWomenCursor> {

        @Override
        protected RxLoader<GeraltWomenCursor> getLoader(final int id, final RxLoaderArguments args) {
            getViewState().startLoading();
            final GeraltWomanLoader loader = loaderProvider.get();
            loader.setId(args.getLong(ARG_ID));
            return loader;
        }

        @Override
        protected void onSuccess(final int id, final GeraltWomenCursor data) {
            getViewState().stopLoading();
            onDataLoaded(data);
        }

        @Override
        protected void onError(final int id, final Throwable error) {
            getViewState().stopLoading();
            getViewState().showError(error.getMessage());
        }

    }
}
