package io.github.dmitrikudrenko.utils.ui;

public interface ViewPagerAdapterController<T> {
    int getCount();
    T getData(int position);
}
