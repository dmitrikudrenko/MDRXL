package io.github.dmitrikudrenko.sample.utils.ui.view;

import android.content.Context;
import android.util.AttributeSet;

public class BottomNavigationView extends android.support.design.widget.BottomNavigationView implements INavigationView {
    public BottomNavigationView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomNavigationView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnNavigationItemSelectedListener(final io.github.dmitrikudrenko.sample.utils.ui.view.OnNavigationItemSelectedListener listener) {
        setOnNavigationItemSelectedListener((OnNavigationItemSelectedListener) listener::onNavigationItemSelected);
    }

    @Override
    public void setItemSelected(int menuItem) {
        setSelectedItemId(menuItem);
    }
}
