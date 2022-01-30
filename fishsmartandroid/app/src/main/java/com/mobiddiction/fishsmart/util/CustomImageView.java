//package com.mobiddiction.fishsmart.util;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapShader;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.RectF;
//import android.graphics.Shader;
//import android.support.v7.widget.AppCompatImageView;
//import android.util.AttributeSet;
//import android.widget.ImageView;
//
//public class CustomImageView extends AppCompatImageView {
//
//    public static float radius = 18.0f;
//    private final int imageWidth;
//
//    public CustomImageView(Context context) {
//        super(context);
//        imageWidth = (Utils.getScreenWidth()/2)-15;
//    }
//
//    public CustomImageView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        imageWidth = (Utils.getScreenWidth()/2)-15;
//    }
//
//    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        imageWidth = (Utils.getScreenWidth()/2)-15;
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        //float radius = 36.0f;
//        this.buildDrawingCache();
//        Bitmap bmap = this.getDrawingCache();
//
//        Path clipPath = new Path();
//        RectF rect = new RectF(0.0f, 0.0f, imageWidth, ((imageWidth)*bmap.getHeight())/bmap.getWidth());
//        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
//        canvas.clipPath(clipPath);
//        super.onDraw(canvas);
//    }
//}