package io.github.dmitrikudrenko.mdrxl.sample.utils;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ToastFactory {
    private final Context context;

    @Inject
    public ToastFactory(final Context context) {
        this.context = context;
    }

    public void showLong(final String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void showShort(final String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
