package com.example.legendofbounca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        Button button = (Button)view;
        Intent intent = new Intent(this, StartGameActivity.class);
        Bundle b = new Bundle();
        if(button.getText().toString().equals("Start Gravity"))
            b.putInt("type", Sensor.TYPE_GRAVITY);
        else
            b.putInt("type", Sensor.TYPE_GYROSCOPE);

        intent.putExtras(b);
        startActivity(intent);
    }
}