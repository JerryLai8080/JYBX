package com.bx.jz.jy.jybx.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class GlideRoundTransform extends BitmapTransformation {

    private static int radius;
    private static int diameter;

    public GlideRoundTransform(Context context, int dp) {
        super(context);
        radius = (int) (Resources.getSystem().getDisplayMetrics().density * dp);
        diameter = radius * 2;
    }

    @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();

        Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setHasAlpha(true);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        drawBottomRoundRect(canvas, paint, width, height);
        return bitmap;
    }

    @Override public String getId() {
        return getClass().getName() + Math.round(radius);
    }

    private static void drawBottomRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(0, bottom - diameter, right, bottom), radius, radius,
                paint);
        canvas.drawRect(new RectF(0, 0, right, bottom - radius), paint);
    }

}