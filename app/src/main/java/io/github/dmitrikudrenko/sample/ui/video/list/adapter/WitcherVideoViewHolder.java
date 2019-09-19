package io.github.dmitrikudrenko.sample.ui.video.list.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.utils.ui.images.ImageLoader;

@AutoFactory
public class WitcherVideoViewHolder extends RecyclerView.ViewHolder implements WitcherVideoHolder {
    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.duration)
    TextView duration;
    @BindColor(R.color.selectedItemColor)
    int selectedItemColor;

    private final ImageLoader imageLoader;
    private final Drawable activatedItemForeground;

    WitcherVideoViewHolder(final View itemView, @Provided final ImageLoader imageLoader) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.imageLoader = imageLoader;
        this.activatedItemForeground = new ColorDrawable(selectedItemColor);
    }

    @Override
    public void showName(final String value) {
        name.setText(value);
    }

    @Override
    public void showThumbnail(final String value) {
        imageLoader.loadFitCroppedImageInto(value, null, thumbnail);
    }

    @Override
    public void showDuration(final String value) {
        duration.setText(value);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setSelected(final boolean selected) {
        ((CardView) itemView).setForeground(selected ? activatedItemForeground : null);
    }
}
