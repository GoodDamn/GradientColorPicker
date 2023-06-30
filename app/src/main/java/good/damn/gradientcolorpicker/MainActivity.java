package good.damn.gradientcolorpicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GradientColorPicker gradientColorPicker =
                new GradientColorPicker(this);

        setContentView(gradientColorPicker);
    }
}