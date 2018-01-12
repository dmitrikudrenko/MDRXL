package io.github.dmitrikudrenko.ui;

public interface RecyclerViewAdapterController<T extends ViewHolder> {
    long getItemId(int position);

    int getItemCount();

    void bind(T holder, int position);
}
