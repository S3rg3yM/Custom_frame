package com.example.admin.custom_frame;

import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main";

    Button btnAdd, btbDelete;
    FrameParent frameParent;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameParent = (FrameParent) findViewById(R.id.fp);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btbDelete = (Button) findViewById(R.id.btn_del);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameParent.addMobileView(getMobileView());
            }
        });

        btbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameParent.countMobileView()==0) return;
//                frameParent.deleteMobileView(frameParent.countMobileView()-1);
                frameParent.deleteMobileView(frameParent.getMobileViewByIndex(frameParent.countMobileView()-1));
            }
        });
    }

    private MobileView getMobileView() {
        MobileView view = new MobileView(getBaseContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(300,200);
        view.setLayoutParams(params);
        view.setFocusable(false);
        view.setClickable(true);
        Random rand = new Random();
        view.setBackgroundColor(Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
        return view;
    }
}

