package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenCursor;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ImageTransformer;

import javax.inject.Inject;
import java.util.AbstractList;

public class GeraltWomenAdapter extends RecyclerView.Adapter<GeraltWomenAdapter.GeraltWomanViewHolder> {
    private final Picasso picasso;
    private final ImageTransformer imageTransformer;

    private GeraltWomen geraltWomen;
    private OnItemClickListener itemClickListener;

    @Inject
    GeraltWomenAdapter(final Picasso picasso, final ImageTransformer imageTransformer) {
        this.picasso = picasso;
        this.imageTransformer = imageTransformer;
        setHasStableIds(true);
    }

    void setItemClickListener(final OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
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
        return new GeraltWomanViewHolder(itemView, picasso, imageTransformer);
    }

    @Override
    public void onBindViewHolder(final GeraltWomanViewHolder holder, final int position) {
        holder.bind(getItem(position));
        holder.itemView.setOnClickListener(v -> itemClickListener.onClick(holder.getItemId()));
    }

    @Override
    public int getItemCount() {
        return geraltWomen != null ? geraltWomen.size() : 0;
    }

    static class GeraltWomanViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name;
        TextView attributes;

        private final String attributesFormat = "%s, %s";

        private final Picasso picasso;
        private final ImageTransformer imageTransformer;

        GeraltWomanViewHolder(final View itemView, final Picasso picasso, final ImageTransformer imageTransformer) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            name = itemView.findViewById(R.id.name);
            attributes = itemView.findViewById(R.id.attributes);

            this.picasso = picasso;
            this.imageTransformer = imageTransformer;
        }

        void bind(final GeraltWomenCursor cursor) {
            imageTransformer.photoTransform(picasso.load(cursor.getPhoto())).into(photo);
            name.setText(cursor.getName());
            attributes.setText(String.format(attributesFormat, cursor.getProfession(), cursor.getHairColor()));
        }
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

    interface OnItemClickListener {
        void onClick(long id);
    }
}
