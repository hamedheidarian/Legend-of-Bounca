package com.example.legendofbounca;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.view.MenuItemCompat;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Math.abs;


public class StartGameActivity extends AppCompatActivity {

    private ImageView ball;
    private TextView tv;
    float X;
    int Y;
    int screenHeight;
    int screenWidth;
    ScreenHandler screen;
    private SensorManager sensorManager;
    private Sensor sensor;
    boolean jump_x = false;
    boolean jump_y = false;
    boolean jump = false;
    private static final float NS2S = 1.0f / 1000000000.0f;
    Button jump_btn;
    private float timestamp;


    PhysicsAssistant physicsAssistant = null;


    void initScreen(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screen = new ScreenHandler(displayMetrics);
        screenHeight = screen.getScreenHeight();
        screenWidth = screen.getScreenWidth();
    }

    int calculateNavigationBarHeight(){
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int navigationHeight = (int) getResources().getDimensionPixelSize(resourceId);
            return navigationHeight;
        }
        return 0;
    }

    int calculateStatusBarHeight(){
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = (int) getResources().getDimensionPixelSize(resourceId);
            return statusBarHeight;
        }
        return 0;
    }

    void updateScreenHeight(){
        screen.setNavigationHeight(calculateNavigationBarHeight());
        screen.setStatusBarHeight(calculateStatusBarHeight());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem actionViewItem = menu.findItem(R.id.miActionButton);
        View v = MenuItemCompat.getActionView(actionViewItem);
        jump_btn = (Button) v.findViewById(R.id.btnCustomAction);
        jump_btn.setOnClickListener(this::onClick);
        return super.onPrepareOptionsMenu(menu);
    }

    public void onClick(View v){
        Toast.makeText(StartGameActivity.this, jump_btn.getText(), Toast.LENGTH_SHORT).show();
        jump = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        ball = (ImageView) findViewById(R.id.ball);
        initScreen();

        X = (float) (Math.random() * screenWidth);
        Y = (int) (Math.random() * screenHeight);

        updateScreenHeight();
        screenHeight = screen.getScreenHeight();

        ball.setX(X);
        ball.setY(Y);

        thread t1 = new thread();
        t1.start();
    }

    public class thread extends Thread implements SensorEventListener {

        float Xs = 0;
        float Ys = 0;
        Menu menu;
        @Override
        public synchronized void run() {
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            Bundle b = getIntent().getExtras();
            if(b.getInt("type") == Sensor.TYPE_GRAVITY)
                physicsAssistant = new GravityPhysicsAssistant();
            else
                physicsAssistant = new GyroscopePhysicsAssistant();
            sensor = sensorManager.getDefaultSensor(b.getInt("type"));

            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

            physicsAssistant.init(X, Y);

            while (true) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ball.setX(Xs);
                            ball.setY(Ys);
                        }
                    });
                    sleep(100);
                } catch (Exception e) {
                    return;
                }
            }
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float Ax = event.values[0];
            float Ay = event.values[1];

            if (jump==true){
                if(abs(Ax)>abs(Ay))
                    jump_x = true;
                else
                    jump_y = true;
            }
            if (timestamp != 0) {
                final float dT = (event.timestamp - timestamp) * NS2S;
                physicsAssistant.move(Ax, Ay, dT, screenHeight, screenWidth, jump_x, jump_y);
                jump = false;
                jump_x = false;
                jump_y = false;
                Xs = physicsAssistant.getXs();
                Ys = physicsAssistant.getYs();
            }
            timestamp = event.timestamp;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
