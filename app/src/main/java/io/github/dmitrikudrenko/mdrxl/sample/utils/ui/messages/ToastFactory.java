package io.github.dmitrikudrenko.mdrxl.sample.utils.ui.messages;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastFactory implements MessageFactory {
    private final Context context;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public ToastFactory(final Context context) {
        this.context = context;
    }

    @Override
    public void showError(final String message) {
        runOnUiThread(() -> Toast.makeText(context, message, Toast.LENGTH_LONG).show());
    }

    @Override
    public void showMessage(final String message) {
        runOnUiThread(() -> Toast.makeText(context, message, Toast.LENGTH_LONG).show());
    }

    private void runOnUiThread(final Runnable runnable) {
        handler.post(runnable);
    }
}
