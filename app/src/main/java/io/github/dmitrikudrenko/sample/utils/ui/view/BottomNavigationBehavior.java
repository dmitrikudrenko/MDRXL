package io.github.dmitrikudrenko.sample.utils.ui.view;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {

    public BottomNavigationBehavior() {
        super();
    }

    public BottomNavigationBehavior(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(final CoordinatorLayout parent, final BottomNavigationView child,
                                   final View dependency) {
        return dependency instanceof FrameLayout;
    }

    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout,
                                       final BottomNavigationView child,
                                       final View directTargetChild, final View target,
                                       final int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(final CoordinatorLayout coordinatorLayout,
                                  final BottomNavigationView child, final View target,
                                  final int dx, final int dy, final int[] consumed) {
        if(dy < 0) {
            showBottomNavigationView(child);
        }
        else if(dy > 0) {
            hideBottomNavigationView(child);
        }
    }

    private void hideBottomNavigationView(final BottomNavigationView view) {
        view.animate().translationY(view.getHeight());
    }

    private void showBottomNavigationView(final BottomNavigationView view) {
        view.animate().translationY(0);
    }
}
