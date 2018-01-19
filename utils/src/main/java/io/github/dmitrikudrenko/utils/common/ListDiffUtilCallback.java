package io.github.dmitrikudrenko.utils.common;

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

    @Override
    public boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
        if (oldList == null) throw new NullPointerException();

        return areItemsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
        if (oldList == null) throw new NullPointerException();

        return areContentsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }

    public abstract boolean areItemsTheSame(T oldItem, T newItem);

    public abstract boolean areContentsTheSame(T oldItem, T newItem);
}
