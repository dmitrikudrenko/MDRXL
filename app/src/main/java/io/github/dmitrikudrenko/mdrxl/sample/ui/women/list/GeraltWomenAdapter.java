package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenCursor;
import io.github.dmitrikudrenko.mdrxl.sample.utils.CircleTransform;

import java.util.AbstractList;

public class GeraltWomenAdapter extends RecyclerView.Adapter<GeraltWomenAdapter.GeraltWomanViewHolder> {
    private GeraltWomen geraltWomen;
    private final OnItemClickListener itemClickListener;

    GeraltWomenAdapter(final OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
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
        return new GeraltWomanViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.v_woman_item, parent, false));
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
        static final Transformation PHOTO_TRANSFORMATION = new CircleTransform();

        ImageView photo;
        TextView name;
        TextView attributes;

        private final String attributesFormat = "%s, %s";

        GeraltWomanViewHolder(final View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            name = itemView.findViewById(R.id.name);
            attributes = itemView.findViewById(R.id.attributes);
        }

        void bind(final GeraltWomenCursor cursor) {
            Picasso.with(photo.getContext()).load(cursor.getPhoto()).fit().centerCrop()
                    .transform(PHOTO_TRANSFORMATION).into(photo);
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
