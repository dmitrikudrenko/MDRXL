package io.github.dmitrikudrenko.mdrxl.sample.ui.women.photos.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.google.auto.factory.AutoFactory;
import io.github.dmitrikudrenko.ui.ViewPagerAdapterController;

@AutoFactory
public class PhotosAdapter extends FragmentStatePagerAdapter {
    private final ViewPagerAdapterController<String> adapterController;

    PhotosAdapter(final FragmentManager fm,
                  final ViewPagerAdapterController<String> adapterController) {
        super(fm);
        this.adapterController = adapterController;
    }

    @Override
    public Fragment getItem(final int position) {
        return PhotoFragment.create(adapterController.getData(position));
    }

    @Override
    public int getCount() {
        return adapterController.getCount();
    }
}
