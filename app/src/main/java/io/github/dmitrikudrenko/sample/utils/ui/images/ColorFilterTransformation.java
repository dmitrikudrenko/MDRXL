package io.github.dmitrikudrenko.sample.utils.ui.images;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import com.squareup.picasso.Transformation;

public class ColorFilterTransformation implements Transformation {
    private final int color;
    private final String key;

    public ColorFilterTransformation(final int color) {
        this.color = color;
        this.key = "ColorFilterTransformation, color = " + color;
    }

    @Override
    public Bitmap transform(final Bitmap source) {
        final int width = source.getWidth();
        final int height = source.getHeight();

        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        final Canvas canvas = new Canvas(bitmap);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(source, 0, 0, paint);
        source.recycle();

        return bitmap;
    }

    @Override
    public String key() {
        return key;
    }
}
