package io.github.dmitrikudrenko.sample.ui.women.photos.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dmitrikudrenko.sample.GeraltApplication;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.utils.ui.images.ImageLoader;

import javax.inject.Inject;

public class PhotoFragment extends Fragment {
    private static final String ARG_URL = "arg_url";

    static PhotoFragment create(final String url) {
        final PhotoFragment fragment = new PhotoFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    ImageLoader imageLoader;

    @BindView(R.id.photo)
    ImageView photoView;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeraltApplication.get().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_photo, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        imageLoader.loadFullSizeImageInto(getArguments().getString(ARG_URL), photoView);
    }
}
