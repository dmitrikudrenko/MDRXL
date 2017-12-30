package io.github.dmitrikudrenko.sample.utils.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import io.github.dmitrikudrenko.sample.R;

@SuppressLint("RestrictedApi")
public class LeftNavigationView extends LinearLayout implements INavigationView {
    private final MenuBuilder menuBuilder;
    private final LayoutInflater layoutInflater;
    private MenuInflater menuInflater;

    @Nullable
    private OnNavigationItemSelectedListener listener;
    @Nullable
    private MenuItemHolder selectedItem;

    private MenuItemHolder[] holders;

    @Nullable
    private ColorStateList itemColor;

    public LeftNavigationView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        setPadding(0, dpToPx(getResources(), 4), 0, 0);

        menuBuilder = new MenuBuilder(context);
        layoutInflater = LayoutInflater.from(context);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LeftNavigationView);
        if (a.hasValue(R.styleable.LeftNavigationView_lnv_menu)) {
            inflateMenu(a.getResourceId(R.styleable.LeftNavigationView_lnv_menu, 0));
        }
        if (a.hasValue(R.styleable.LeftNavigationView_lnv_itemIconTint)) {
            itemColor = a.getColorStateList(R.styleable.LeftNavigationView_lnv_itemIconTint);
        }
        a.recycle();

        buildMenu();
    }

    private void buildMenu() {
        final int itemsCount = menuBuilder.size();
        holders = new MenuItemHolder[itemsCount];
        for (int i = 0; i < itemsCount; i++) {
            final MenuItem menuItem = menuBuilder.getItem(i);
            final View view = layoutInflater.inflate(R.layout.v_navigation_view_item, this, false);

            final MenuItemHolder holder = new MenuItemHolder(view, itemColor);
            holders[i] = holder;
            holder.bind(menuItem);
            if (i == 0) {
                setSelected(menuItem, holder);
            }

            view.setOnClickListener(v -> setSelected(menuItem, holder));
            addView(view);
        }
    }

    private void setSelected(final MenuItem menuItem, final MenuItemHolder holder) {
        setSelected(selectedItem, false);
        selectedItem = holder;
        setSelected(selectedItem, true);
        if (listener != null) {
            listener.onNavigationItemSelected(menuItem);
        }
    }

    private void setSelected(@Nullable final MenuItemHolder item, final boolean selected) {
        if (item != null) {
            item.setSelected(selected);
        }
    }

    private void inflateMenu(final int menuRes) {
        getMenuInflater().inflate(menuRes, menuBuilder);
    }

    private MenuInflater getMenuInflater() {
        if (menuInflater == null) {
            menuInflater = new SupportMenuInflater(getContext());
        }
        return menuInflater;
    }

    private static int dpToPx(final Resources r, final int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    @Override
    public void setOnNavigationItemSelectedListener(final OnNavigationItemSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public void setItemSelected(final int menuItem) {
        final int itemIndex = menuBuilder.findItemIndex(menuItem);
        if (itemIndex >= 0) {
            final MenuItem item = menuBuilder.findItem(menuItem);
            setSelected(item, holders[itemIndex]);
        }
    }
}
