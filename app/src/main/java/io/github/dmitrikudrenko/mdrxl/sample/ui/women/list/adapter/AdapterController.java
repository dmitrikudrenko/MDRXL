package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list.adapter;

public interface AdapterController<T> {
    long getItemId(int position);

    int getItemCount();

    void bind(T holder, int position);
}
