package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.ui.RecyclerViewAdapterController;

@AutoFactory
public class GeraltWomenAdapter extends RecyclerView.Adapter<GeraltWomanViewHolder> {
    private final GeraltWomanViewHolderFactory viewHolderFactory;
    private final OnItemClickListener itemClickListener;

    private final RecyclerViewAdapterController<GeraltWomanHolder> adapterController;

    GeraltWomenAdapter(@Provided final GeraltWomanViewHolderFactory viewHolderFactory,
                       final OnItemClickListener itemClickListener,
                       final RecyclerViewAdapterController<GeraltWomanHolder> adapterController) {
        this.itemClickListener = itemClickListener;
        this.viewHolderFactory = viewHolderFactory;
        this.adapterController = adapterController;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(final int position) {
        return adapterController.getItemId(position);
    }

    @Override
    public GeraltWomanViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.v_woman_item, parent, false);
        final GeraltWomanViewHolder holder = viewHolderFactory.create(itemView);
        itemView.setOnClickListener(v -> itemClickListener.onClick(holder.getAdapterPosition()));
        return holder;
    }

    @Override
    public void onBindViewHolder(final GeraltWomanViewHolder holder, final int position) {
        adapterController.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return adapterController.getItemCount();
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
