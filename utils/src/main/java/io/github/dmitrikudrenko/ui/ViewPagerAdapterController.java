package io.github.dmitrikudrenko.ui;

public interface ViewPagerAdapterController<T> {
    int getCount();
    T getData(int position);
}
