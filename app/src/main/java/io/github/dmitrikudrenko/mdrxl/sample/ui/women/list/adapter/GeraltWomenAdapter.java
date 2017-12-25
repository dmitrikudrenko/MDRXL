package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenCursor;

import java.util.AbstractList;

@AutoFactory
public class GeraltWomenAdapter extends RecyclerView.Adapter<GeraltWomanViewHolder> {
    private final GeraltWomanViewHolderFactory viewHolderFactory;
    private final OnItemClickListener itemClickListener;

    private GeraltWomen geraltWomen;

    GeraltWomenAdapter(final OnItemClickListener itemClickListener,
                       @Provided final GeraltWomanViewHolderFactory viewHolderFactory) {
        this.itemClickListener = itemClickListener;
        this.viewHolderFactory = viewHolderFactory;
        setHasStableIds(true);
    }

    public void set(final GeraltWomenCursor cursor) {
        geraltWomen = new GeraltWomen(cursor);
        notifyDataSetChanged();
    }

    private GeraltWomenCursor getItem(final int position) {
        return geraltWomen.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).getId();
    }

    @Override
    public GeraltWomanViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.v_woman_item, parent, false);
        final GeraltWomanViewHolder holder = viewHolderFactory.create(itemView);
        itemView.setOnClickListener(v -> itemClickListener.onClick(holder.getItemId()));
        return holder;
    }

    @Override
    public void onBindViewHolder(final GeraltWomanViewHolder holder, final int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return geraltWomen != null ? geraltWomen.size() : 0;
    }

    static class GeraltWomen extends AbstractList<GeraltWomenCursor> {
        final GeraltWomenCursor cursor;

        GeraltWomen(final GeraltWomenCursor cursor) {
            this.cursor = cursor;
        }

        @Override
        public GeraltWomenCursor get(final int index) {
            if (cursor.isClosed()) {
                Log.d("GeraltWomenAdapter", "Cursor is closed: " + cursor.toString());
            }
            if (cursor.moveToPosition(index)) {
                return cursor;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int size() {
            return cursor.getCount();
        }
    }

    public interface OnItemClickListener {
        void onClick(long id);
    }
}
