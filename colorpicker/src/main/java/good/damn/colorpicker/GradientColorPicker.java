package good.damn.colorpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GradientColorPicker
extends View {

    private static final String TAG = "GradientColorPicker";

    @Nullable
    private OnPickColorListener mOnPickColorListener;

    private Paint mPaint;
    private Paint mPaintStroke;

    private Paint mPaintFillModel;

    private LinearGradient mGradientColors;
    private LinearGradient mGradientBw;

    private Bitmap mBitmapGradient;

    private float mStickX = 0;
    private float mStickY = 0;
    private float mStickRadius = 32;

    private void init() {
        mPaint = new Paint();
        mPaintStroke = new Paint();
        mPaintFillModel = new Paint();

        mPaintStroke.setColor(0xffffffff);
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintStroke.setStrokeWidth(3);

        mPaintFillModel.setAntiAlias(true);
        mPaintStroke.setAntiAlias(true);
    }

    public GradientColorPicker(Context context) {
        super(context);
        init();
    }

    public GradientColorPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradientColorPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnPickColorListener(OnPickColorListener onPickColorListener) {
        mOnPickColorListener = onPickColorListener;
    }

    public void setStickStrokeColor(@ColorInt int color) {
        mPaintStroke.setColor(color);
    }

    public void setStickWidth(float width) {
        mPaintStroke.setStrokeWidth(width);
    }

    public void setStickRadius(float rad) {
        mStickRadius = rad;
    }

    public int getStickStrokeColor() {
        return mPaintStroke.getColor();
    }

    public float getStickWidth() {
        return mPaintStroke.getStrokeWidth();
    }

    public float getStickRadius() {
        return mStickRadius;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (!(mBitmapGradient == null ||
            mBitmapGradient.isRecycled()
        )) {
            mBitmapGradient.recycle();
        }
    }

    @Override
    protected void onDraw(
        @NonNull Canvas canvas
    ) {
        super.onDraw(canvas);

        mPaint.setShader(
            mGradientColors
        );
        canvas.drawPaint(
            mPaint
        );

        mPaint.setShader(
            mGradientBw
        );

        canvas.drawPaint(
            mPaint
        );

        canvas.drawCircle(
            mStickX,
            mStickY,
            mStickRadius,
            mPaintFillModel
        );

        canvas.drawCircle(
            mStickX,
            mStickY,
            mStickRadius,
            mPaintStroke
        );
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(
        boolean changed,
        int left,
        int top,
        int right,
        int bottom
    ) {
        super.onLayout(
            changed,
            left,
            top,
            right,
            bottom
        );

        float mh = getHeight() * 0.5f;
        float mw = getWidth() * 0.5f;

        mGradientColors = new LinearGradient(
            0,
            mh,
            getWidth(),
            mh,
            new int[] {
                0xffff0000,
                0xffffff00,
                0xff00ff00,
                0xff00ffff,
                0xff0000ff,
                0xffff00ff,
                0xffff0000
            },
            new float[] {
                0.0f,
                0.167f,
                0.333f,
                0.500f,
                0.667f,
                0.834f,
                1.0f
            },
            Shader.TileMode.CLAMP
        );

        mGradientBw = new LinearGradient(
            mw,
            0,
            mw,
            getHeight(),
            new int[] {
              0xff000000,
              0,
              0,
              0xffffffff
            },
            new float[] {
              0.0f,
              0.25f,
              0.75f,
              1.0f
            },
            Shader.TileMode.CLAMP
        );

        post(() -> {
            mBitmapGradient = Bitmap.createBitmap(
                getWidth(),
                getHeight(),
                Bitmap.Config.ARGB_8888
            );
            final Canvas copy = new Canvas(
                mBitmapGradient
            );
            draw(copy);
        });

    }

    @Override
    public boolean onTouchEvent(
        MotionEvent event
    ) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (y < 0) {
            y = 0;
        } else if (y >= mBitmapGradient.getHeight()) {
            y = mBitmapGradient.getHeight()-1;
        }

        if (x < 0) {
            x = 0;
        } else if (x >= mBitmapGradient.getWidth()) {
            x = mBitmapGradient.getWidth()-1;
        }

        mStickX = x;
        mStickY = y;

        mPaintFillModel.setColor(
            mBitmapGradient.getPixel(
                x, y
            )
        );

        if (mOnPickColorListener != null) {
            mOnPickColorListener.onPickColor(
                mPaintFillModel.getColor()
            );
        }

        invalidate();
        return true;
    }
}
