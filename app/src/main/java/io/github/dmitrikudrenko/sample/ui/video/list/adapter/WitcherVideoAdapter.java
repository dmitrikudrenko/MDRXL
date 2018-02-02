package io.github.dmitrikudrenko.sample.ui.video.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.utils.ui.OnItemClickListener;
import io.github.dmitrikudrenko.utils.ui.RecyclerViewAdapterController;

@AutoFactory
public class WitcherVideoAdapter extends RecyclerView.Adapter<WitcherVideoViewHolder> {
    private final WitcherVideoViewHolderFactory viewHolderFactory;
    private final OnItemClickListener itemClickListener;
    private final RecyclerViewAdapterController<WitcherVideoHolder> adapterController;

    WitcherVideoAdapter(@Provided final WitcherVideoViewHolderFactory viewHolderFactory,
                        final OnItemClickListener itemClickListener,
                        final RecyclerViewAdapterController<WitcherVideoHolder> adapterController) {
        this.viewHolderFactory = viewHolderFactory;
        this.itemClickListener = itemClickListener;
        this.adapterController = adapterController;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(final int position) {
        return adapterController.getItemId(position);
    }

    @Override
    public WitcherVideoViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.v_video_item, parent, false);
        final WitcherVideoViewHolder holder = viewHolderFactory.create(itemView);
        itemView.setOnClickListener(v -> itemClickListener.onClick(holder.getAdapterPosition(), v));
        return holder;
    }

    @Override
    public void onBindViewHolder(final WitcherVideoViewHolder holder, final int position) {
        adapterController.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return adapterController.getItemCount();
    }
}
