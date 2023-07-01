package good.damn.gradientcolorpicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import good.damn.gradient_color_picker.GradientColorPicker;
import good.damn.gradient_color_picker.OnPickColorListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GradientColorPicker gradientColorPicker =
                new GradientColorPicker(this);

        ColorDrawable colorDrawable = new ColorDrawable();
        ActionBar actionBar = getSupportActionBar();

        gradientColorPicker.setOnPickColorListener(new OnPickColorListener() {
            @Override
            public void onPickColor(int color) {
                if (actionBar == null){
                    return;
                }

                colorDrawable.setColor(color);
                actionBar.setBackgroundDrawable(colorDrawable);
            }
        });

        setContentView(gradientColorPicker);
    }
}