package good.damn.gradientcolorpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

public class GradientColorPicker extends View implements View.OnTouchListener{

    private static final String TAG = "GradientColorPicker";

    private OnPickColorListener mOnPickColorListener;

    private Paint mPaint;
    private Paint mPaintStroke;
    private Bitmap mBitmapGradient;

    private float mStickX = 0;
    private float mStickY = 0;
    private float mStickRadius = 32;

    private void init() {
        mBitmapGradient = BitmapFactory.decodeResource(getResources(), R.drawable.grad_pal);
        mPaint = new Paint();
        mPaintStroke = new Paint();

        mPaintStroke.setColor(0xffffffff);
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintStroke.setStrokeWidth(3);

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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmapGradient,0,0,mPaint);
        canvas.drawCircle(mStickX,mStickY, mStickRadius,mPaint);
        canvas.drawCircle(mStickX,mStickY,mStickRadius,mPaintStroke);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        setOnTouchListener(null);
        Log.d(TAG, "onLayout: WIDTH_GRAD: " + mBitmapGradient.getWidth() + " WIDTH: " + getWidth());
        if (mBitmapGradient.getWidth() < getWidth()) {
            mBitmapGradient = Bitmap.createScaledBitmap(mBitmapGradient, getWidth(), getHeight(), false);
        }
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();

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

        mPaint.setColor(mBitmapGradient.getPixel(x, y));

        if (mOnPickColorListener != null) {
            mOnPickColorListener.onPickColor(mPaint.getColor());
        }

        invalidate();
        return true;
    }
}
