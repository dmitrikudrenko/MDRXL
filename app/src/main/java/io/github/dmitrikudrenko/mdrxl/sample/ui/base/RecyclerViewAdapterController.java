package io.github.dmitrikudrenko.mdrxl.sample.ui.base;

public interface RecyclerViewAdapterController<T> {
    long getItemId(int position);

    int getItemCount();

    void bind(T holder, int position);
}
