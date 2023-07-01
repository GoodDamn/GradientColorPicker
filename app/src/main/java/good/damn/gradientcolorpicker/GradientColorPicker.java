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

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

public class GradientColorPicker extends View implements View.OnTouchListener{

    private static final String TAG = "GradientColorPicker";

    private Paint mPaint;

    private Bitmap mBitmapGradient;

    private void init() {
        mBitmapGradient = BitmapFactory.decodeResource(getResources(), R.drawable.grad_pal);
        mPaint = new Paint();
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmapGradient,0,0,mPaint);
        canvas.drawRect(0,0,50,50,mPaint);
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

        if (y < 0 || y >= mBitmapGradient.getHeight())
            return true;

        mPaint.setColor(mBitmapGradient.getPixel(x, y));

        invalidate();
        return true;
    }
}
