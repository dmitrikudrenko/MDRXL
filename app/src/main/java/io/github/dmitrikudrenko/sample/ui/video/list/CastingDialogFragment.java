package io.github.dmitrikudrenko.sample.ui.video.list;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import io.github.dmitrikudrenko.sample.GeraltApplication;
import io.github.dmitrikudrenko.sample.R;
import io.github.dmitrikudrenko.sample.utils.ui.images.ImageLoader;

import javax.inject.Inject;

public class CastingDialogFragment extends DialogFragment {
    private static final String ARG_THUMBNAIL = "arg_thumbnail";

    public static CastingDialogFragment create(final String thumbnail) {
        final CastingDialogFragment fragment = new CastingDialogFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_THUMBNAIL, thumbnail);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    ImageLoader imageLoader;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        GeraltApplication.get().inject(this);

        final View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.d_video_actions, null, false);
        dialogView.findViewById(R.id.play).setOnClickListener(view -> {
            ((OnActionSelectorListener) getParentFragment()).onPlaySelected();
            dismiss();
        });
        dialogView.findViewById(R.id.add_queue).setOnClickListener(view -> {
            ((OnActionSelectorListener) getParentFragment()).onQueueSelected();
            dismiss();
        });
        imageLoader.loadDimThumbnailInto(getArguments().getString(ARG_THUMBNAIL),
                dialogView.findViewById(R.id.thumbnail));

        return new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .create();
    }

    public interface OnActionSelectorListener {
        void onPlaySelected();

        void onQueueSelected();
    }
}
