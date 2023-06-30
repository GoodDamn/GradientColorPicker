package good.damn.gradientcolorpicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

public class GradientColorPicker extends View {

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
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Log.d(TAG, "onLayout: WIDTH_GRAD: " + mBitmapGradient.getWidth() + " WIDTH: " + getWidth());
        if (mBitmapGradient.getWidth() < getWidth()) {
            mBitmapGradient = Bitmap.createScaledBitmap(mBitmapGradient, getWidth(), getHeight(), false);
        }
    }
}
