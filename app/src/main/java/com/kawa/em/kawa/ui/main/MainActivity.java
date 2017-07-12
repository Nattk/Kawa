package com.kawa.em.kawa.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kawa.em.kawa.R;
import com.kawa.em.kawa.ui.Home.HomeActivity;
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

            //TODO: Faire apppli
            @Override
            public void run() {

                Intent intentHome = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intentHome);
            }
        }, Constant.SPLASH_TIME);
    }
}
