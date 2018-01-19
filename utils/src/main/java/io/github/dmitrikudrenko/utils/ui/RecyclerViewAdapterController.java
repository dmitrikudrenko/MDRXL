package io.github.dmitrikudrenko.utils.ui;

public interface RecyclerViewAdapterController<T extends ViewHolder> {
    long getItemId(int position);

    int getItemCount();

    void bind(T holder, int position);
}
