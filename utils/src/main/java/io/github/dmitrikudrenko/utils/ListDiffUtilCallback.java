package io.github.dmitrikudrenko.utils;

import android.support.v7.util.DiffUtil;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ListDiffUtilCallback<T> extends DiffUtil.Callback {
    private final List<T> newList;
    @Nullable
    private final List<T> oldList;

    public ListDiffUtilCallback(final List<T> newList, @Nullable final List<T> oldList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    protected T getOldItem(final int position) {
        return oldList.get(position);
    }

    protected T getNewItem(final int position) {
        return newList.get(position);
    }
}
