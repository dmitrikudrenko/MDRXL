package io.github.dmitrikudrenko.sample.ui.video.queue.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.utils.ui.images.ImageLoader;

import javax.annotation.Nullable;

@AutoFactory
public class VideoViewHolder extends RecyclerView.ViewHolder implements VideoHolder {
    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.duration)
    TextView duration;
    @BindColor(R.color.videoItemBackground)
    int itemBackgroundColor;
    @BindColor(R.color.selectedVideoItemBackground)
    int selectedItemBackgroundColor;

    private final ImageLoader imageLoader;
    @Nullable
    private View.OnClickListener onOptionsClickListener;

    VideoViewHolder(@Provided final ImageLoader imageLoader,
                    final View itemView) {
        super(itemView);
        this.imageLoader = imageLoader;
        ButterKnife.bind(this, itemView);
    }

    public void setOnOptionsClickListener(final View.OnClickListener onOptionsClickListener) {
        this.onOptionsClickListener = onOptionsClickListener;
    }

    @Override
    public void showName(final String value) {
        name.setText(value);
    }

    @Override
    public void showThumbnail(final String value) {
        imageLoader.loadThumbnailInto(value, thumbnail);
    }

    @Override
    public void showDuration(final String value) {
        duration.setText(value);
    }

    @Override
    public void setSelected(final boolean selected) {
        itemView.setBackgroundColor(selected ? selectedItemBackgroundColor : itemBackgroundColor);
    }

    @OnClick(R.id.options)
    void onOptionsClick(final View view) {
        if (onOptionsClickListener != null) {
            onOptionsClickListener.onClick(view);
        }
    }
}
