package io.github.dmitrikudrenko.mdrxl.sample.ui.data.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.model.data.local.DataCursor;

import java.util.AbstractList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    private DataList dataList;
    private final OnItemClickListener itemClickListener;

    DataAdapter(final OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        setHasStableIds(true);
    }

    public void set(final DataCursor cursor) {
        dataList = new DataList(cursor);
        notifyDataSetChanged();
    }

    private DataCursor getItem(final int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).getId();
    }

    @Override
    public DataViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new DataViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.v_data_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final DataViewHolder holder, final int position) {
        holder.bind(getItem(position));
        holder.itemView.setOnClickListener(v -> itemClickListener.onClick(holder.getItemId()));
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView name;
        TextView firstAttribute;
        TextView secondAttribute;
        TextView thirdAttribute;

        DataViewHolder(final View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.data_id);
            name = itemView.findViewById(R.id.data_name);
            firstAttribute = itemView.findViewById(R.id.data_first_attribute);
            secondAttribute = itemView.findViewById(R.id.data_second_attribute);
            thirdAttribute = itemView.findViewById(R.id.data_third_attribute);
        }

        void bind(final DataCursor cursor) {
            id.setText(String.valueOf(cursor.getId()));
            name.setText(cursor.getName());
            firstAttribute.setText(cursor.getFirstAttribute());
            secondAttribute.setText(cursor.getSecondAttribute());
            thirdAttribute.setText(cursor.getThirdAttribute());
        }
    }

    static class DataList extends AbstractList<DataCursor> {
        final DataCursor cursor;

        DataList(final DataCursor cursor) {
            this.cursor = cursor;
        }

        @Override
        public DataCursor get(final int index) {
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
