package io.github.dmitrikudrenko.mdrxl.sample.ui.women.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import io.github.dmitrikudrenko.mdrxl.sample.R;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomenCursor;
import io.github.dmitrikudrenko.mdrxl.sample.utils.ImageLoader;

@AutoFactory
class GeraltWomanViewHolder extends RecyclerView.ViewHolder {
    private static final String attributesFormat = "%s, %s";

    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.attributes)
    TextView attributes;

    private final ImageLoader imageLoader;

    GeraltWomanViewHolder(final View itemView, @Provided final ImageLoader imageLoader) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.imageLoader = imageLoader;
    }

    void bind(final GeraltWomenCursor cursor) {
        imageLoader.loadPhotoInto(cursor.getPhoto(), photo);
        name.setText(cursor.getName());
        attributes.setText(String.format(attributesFormat, cursor.getProfession(), cursor.getHairColor()));
    }
}
