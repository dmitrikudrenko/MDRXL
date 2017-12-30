package io.github.dmitrikudrenko.mdrxl.sample.ui.women.details;

import com.arellomobile.mvp.InjectViewState;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoader;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderArguments;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderCallbacks;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaderManager;
import io.github.dmitrikudrenko.mdrxl.loader.RxLoaders;
import io.github.dmitrikudrenko.mdrxl.mvp.RxLoaderPresenter;
import io.github.dmitrikudrenko.mdrxl.sample.di.FragmentScope;
import io.github.dmitrikudrenko.mdrxl.sample.di.woman.WomanId;
import io.github.dmitrikudrenko.mdrxl.sample.model.UpdateModel;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.commands.GeraltWomenStorageCommand;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomanLoaderFactory;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenCursor;
import io.github.dmitrikudrenko.mdrxl.sample.ui.navigation.Router;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ui.messages.MessageFactory;
import rx.android.schedulers.AndroidSchedulers;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

@FragmentScope
@InjectViewState
public class GeraltWomanPresenter extends RxLoaderPresenter<GeraltWomanView> {
    private static final String ARG_ID = "id";
    private static final int LOADER_ID = RxLoaders.generateId();

    private final GeraltWomanLoaderFactory loaderFactory;
    private final Provider<GeraltWomenStorageCommand> storageCommandProvider;
    private final Router router;
    private final MessageFactory messageFactory;
    private final long id;

    @Nullable
    private GeraltWomenCursor data;

    @Inject
    GeraltWomanPresenter(final RxLoaderManager loaderManager,
                         final GeraltWomanLoaderFactory loaderFactory,
                         final Provider<GeraltWomenStorageCommand> storageCommandProvider,
                         final Router router,
                         final MessageFactory messageFactory,
                         @WomanId final long id) {
        super(loaderManager);
        this.loaderFactory = loaderFactory;
        this.storageCommandProvider = storageCommandProvider;
        this.router = router;
        this.messageFactory = messageFactory;
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
        add(geraltWomenStorageCommand.save(createUpdateModel(field, value))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> messageFactory.showMessage("GeraltWoman updated"),
                        error -> {
                            messageFactory.showError(error.getMessage());
                            getLoaderManager().getLoader(LOADER_ID).onContentChanged();
                        })
        );
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

    void onPhotoClicked() {
        router.openGeraltWomanPhotos();
    }

    private class LoaderCallbacks extends RxLoaderCallbacks<GeraltWomenCursor> {

        @Override
        protected RxLoader<GeraltWomenCursor> getLoader(final int id, final RxLoaderArguments args) {
            getViewState().startLoading();
            return loaderFactory.create(args.getLong(ARG_ID));
        }

        @Override
        protected void onSuccess(final int id, final GeraltWomenCursor data) {
            getViewState().stopLoading();
            onDataLoaded(data);
        }

        @Override
        protected void onError(final int id, final Throwable error) {
            getViewState().stopLoading();
            messageFactory.showError(error.getMessage());
        }

    }
}
