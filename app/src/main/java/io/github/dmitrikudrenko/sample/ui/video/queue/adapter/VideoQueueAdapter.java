package io.github.dmitrikudrenko.sample.ui.video.queue.adapter;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.ui.video.queue.VideoQueueItemOptionsListener;
import io.github.dmitrikudrenko.sample.utils.ui.OnItemClickListener;
import io.github.dmitrikudrenko.utils.ui.RecyclerViewAdapterController;

@AutoFactory
public class VideoQueueAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    private final VideoViewHolderFactory holderFactory;
    private final RecyclerViewAdapterController<VideoHolder> adapterController;
    private final OnItemClickListener itemClickListener;
    private final VideoQueueItemOptionsListener videoQueueItemOptionsListener;

    VideoQueueAdapter(@Provided final VideoViewHolderFactory holderFactory,
                      final RecyclerViewAdapterController<VideoHolder> adapterController,
                      final OnItemClickListener itemClickListener,
                      final VideoQueueItemOptionsListener videoQueueItemOptionsListener) {
        this.holderFactory = holderFactory;
        this.adapterController = adapterController;
        this.itemClickListener = itemClickListener;
        this.videoQueueItemOptionsListener = videoQueueItemOptionsListener;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(final int position) {
        return adapterController.getItemId(position);
    }

    @Override
    public VideoViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.v_video_queue_item, parent, false);
        final VideoViewHolder holder = holderFactory.create(itemView);
        holder.setOnOptionsClickListener(v -> showOptionsMenu(v, holder.getAdapterPosition()));
        itemView.setOnClickListener(view -> itemClickListener.onClick(holder.getAdapterPosition(), view));
        return holder;
    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, final int position) {
        adapterController.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return adapterController.getItemCount();
    }

    private void showOptionsMenu(final View v, final int position) {
        final PopupMenu optionsMenu = new PopupMenu(v.getContext(), v);
        optionsMenu.inflate(R.menu.m_video_queue_options);
        optionsMenu.setOnMenuItemClickListener(item -> {
            final int itemId = item.getItemId();
            switch (itemId) {
                case R.id.play_next:
                    onPlayNextOptionSelected(position);
                    break;
                case R.id.delete:
                    onDeleteOptionSelected(position);
                    break;
            }
            return false;
        });
        optionsMenu.show();
    }

    private void onPlayNextOptionSelected(final int position) {
        videoQueueItemOptionsListener.onPlayNext(position);
    }

    private void onDeleteOptionSelected(final int position) {
        videoQueueItemOptionsListener.onDeleteFromQueue(position);
    }
}
