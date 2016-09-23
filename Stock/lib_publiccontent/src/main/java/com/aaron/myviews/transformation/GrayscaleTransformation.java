package com.aaron.myviews.transformation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

public class GrayscaleTransformation implements Transformation {

  @Override public Bitmap transform(Bitmap source) {

    int width = source.getWidth();
    int height = source.getHeight();

    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

    Canvas canvas = new Canvas(bitmap);
    ColorMatrix saturation = new ColorMatrix();
    saturation.setSaturation(0f);
    Paint paint = new Paint();
    paint.setColorFilter(new ColorMatrixColorFilter(saturation));
    canvas.drawBitmap(source, 0, 0, paint);
    source.recycle();

    return bitmap;
  }

  @Override public String key() {
    return "GrayscaleTransformation()";
  }
}