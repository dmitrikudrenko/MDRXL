package io.github.dmitrikudrenko.sample.utils.ui.images;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import com.squareup.picasso.Transformation;

public class RoundedCornersTransform implements Transformation {
    private final int radius;
    private final int margin;

    private final String key;

    public RoundedCornersTransform(final int radius, final int margin) {
        this.radius = radius;
        this.margin = margin;
        this.key = "Radius:" + radius + ", margin:" + margin;
    }

    @Override
    public Bitmap transform(final Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        final Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final RectF rect = new RectF(margin, margin, source.getWidth() - margin,
                source.getHeight() - margin);
        canvas.drawRoundRect(rect, radius, radius, paint);
        if (source != output) {
            source.recycle();
        }
        return output;
    }

    @Override
    public String key() {
        return key;
    }

}
