package software.engineering.upm.es;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.content.Context;
import android.util.AttributeSet;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Firma extends AppCompatActivity {
    private DrawingView drawView;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_firma);
        drawView = (DrawingView) findViewById(R.id.drawing);

    }
}
