package io.github.dmitrikudrenko.mdrxl.sample.ui.base;

public interface ViewPagerAdapterController<T> {
    int getCount();
    T getData(int position);
}
