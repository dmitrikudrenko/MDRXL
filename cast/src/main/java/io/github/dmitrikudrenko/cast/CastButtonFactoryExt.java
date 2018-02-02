package io.github.dmitrikudrenko.cast;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastState;

public class CastButtonFactoryExt {
    public static void setUpCastDependentButton(final Context context, final Menu menu,
                                                final int menuItemId) {
        new CastButtonFactoryExt().doSetUpCastDependentButton(context, menu, menuItemId);
    }

    private CastButtonFactoryExt() {
    }

    private void doSetUpCastDependentButton(final Context context, final Menu menu, final int menuItemId) {
        final MenuItem menuItem = menu.findItem(menuItemId);
        if (menuItem == null) {
            return;
        }
        final CastContext castContext = CastContext.getSharedInstance(context);
        menuItem.setVisible(castContext.getCastState() == CastState.CONNECTED);
        castContext.addCastStateListener(state -> menuItem.setVisible(state == CastState.CONNECTED));
    }
}
