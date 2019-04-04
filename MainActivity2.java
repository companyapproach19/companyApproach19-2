package com.example.takesignature;
 
import android.app.Activity;
import android.os.Bundle;
 
import com.signature.SignatureMainLayout;
 
public class MainActivity extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(new SignatureMainLayout(this));
    }
}