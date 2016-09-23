package com.aaron.myviews.transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * Created by Administrator on 2015/10/22.
 */
public class RoundedCornersTransformation implements Transformation {

    private int radius;
    private int margin;

    public RoundedCornersTransformation(int radius, int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    @Override public Bitmap transform(Bitmap source) {

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect(new RectF(margin, margin, width - margin, height - margin), radius, radius,
                paint);
        source.recycle();

        return bitmap;
    }

    @Override public String key() {
        return "RoundedTransformation(radius=" + radius + ", margin=" + margin + ")";
    }
}

