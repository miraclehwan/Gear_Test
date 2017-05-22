package com.example.daehwankim.test_work;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import org.gearvrf.GVRActivity;

public class MainActivity extends GVRActivity {

    Main main = new Main();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMain(main);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        main.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
