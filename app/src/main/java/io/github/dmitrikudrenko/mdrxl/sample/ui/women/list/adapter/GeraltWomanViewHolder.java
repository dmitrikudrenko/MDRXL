package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list.adapter;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.internal.ForegroundLinearLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ui.ImageLoader;

@AutoFactory
public class GeraltWomanViewHolder extends RecyclerView.ViewHolder implements GeraltWomanHolder {
    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.profession)
    TextView profession;
    @BindColor(R.color.selectedItemColor)
    int selectedItemColor;

    private final ImageLoader imageLoader;
    private final Drawable activatedItemForeground;

    GeraltWomanViewHolder(final View itemView, @Provided final ImageLoader imageLoader) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.imageLoader = imageLoader;
        this.activatedItemForeground = new ColorDrawable(selectedItemColor);
    }

    @Override
    public void showPhoto(final String value) {
        imageLoader.loadPhotoInto(value, photo);
    }

    @Override
    public void showName(final String value) {
        name.setText(value);
    }

    @Override
    public void showProfession(final String value) {
        profession.setText(value);
    }

    @Override
    public void setSelected(final boolean selected) {
        ((ForegroundLinearLayout) itemView).setForeground(selected ? activatedItemForeground : null);
    }
}
