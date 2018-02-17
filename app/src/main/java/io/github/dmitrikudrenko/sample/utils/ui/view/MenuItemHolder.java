package io.github.dmitrikudrenko.sample.utils.ui.view;

import android.content.res.ColorStateList;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dmitrikudrenko.sample.R;

public class MenuItemHolder {
    @BindView(R.id.item)
    ImageView imageView;

    public MenuItemHolder(final View view, final ColorStateList itemTint) {
        ButterKnife.bind(this, view);
        imageView.setImageTintList(itemTint);
    }

    public void bind(final MenuItem menuItem) {
        imageView.setImageDrawable(menuItem.getIcon());
    }

    public void setSelected(final boolean selected) {
        imageView.setSelected(selected);
    }
}
