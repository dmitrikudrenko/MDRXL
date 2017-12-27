package io.github.dmitrikudrenko.mdrxl.sample.ui.women.photos.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.google.auto.factory.AutoFactory;
import io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.local.GeraltWomanPhotoCursor;

import java.util.AbstractList;

@AutoFactory
public class PhotosAdapter extends FragmentStatePagerAdapter {
    private Photos photos;

    PhotosAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(final int position) {
        return PhotoFragment.create(photos.get(position).getUrl());
    }

    @Override
    public int getCount() {
        return photos != null ? photos.size() : 0;
    }

    public void setPhotos(final GeraltWomanPhotoCursor cursor) {
        this.photos = new Photos(cursor);
        notifyDataSetChanged();
    }

    static class Photos extends AbstractList<GeraltWomanPhotoCursor> {
        final GeraltWomanPhotoCursor cursor;

        Photos(final GeraltWomanPhotoCursor cursor) {
            this.cursor = cursor;
        }

        @Override
        public GeraltWomanPhotoCursor get(final int index) {
            if (cursor.moveToPosition(index)) {
                return cursor;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int size() {
            return cursor.getCount();
        }
    }
}
