package com.kawa.em.kawa.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kawa.em.kawa.R;
import com.kawa.em.kawa.ui.map.MapActivity;
import com.kawa.em.kawa.utils.Constant;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Timer myTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {

            @Override
            public void run() {

                    Intent intentMap = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intentMap);
            }
        }, Constant.SPLASH_TIME);
    }
}
