package io.github.dmitrikudrenko.mdrxl.sample.ui.data.list;

import io.github.dmitrikudrenko.mdrxl.mvp.RxView;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.DataCursor;

public interface DataListView extends RxView {
    void startLoading();

    void stopLoading();

    void showError(String message);

    void showData(DataCursor cursor);
}
