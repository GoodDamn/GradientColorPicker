package good.damn.colorpickerdemo;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import good.damn.colorpicker.GradientColorPicker;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final GradientColorPicker gradientColorPicker =
                new GradientColorPicker(this);

        ColorDrawable colorDrawable = new ColorDrawable();
        ActionBar actionBar = getSupportActionBar();

        gradientColorPicker.setOnPickColorListener(color -> {
            if (actionBar == null) {
                return;
            }

            colorDrawable.setColor(
                color
            );

            actionBar.setBackgroundDrawable(
                colorDrawable
            );
        });

        setContentView(
            gradientColorPicker
        );
    }
}