package io.github.dmitrikudrenko.mdrxl.sample.utils.ui.messages;

import android.content.Context;
import android.widget.Toast;

public class ToastFactory implements MessageFactory {
    private final Context context;

    public ToastFactory(final Context context) {
        this.context = context;
    }

    @Override
    public void showError(final String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(final String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
