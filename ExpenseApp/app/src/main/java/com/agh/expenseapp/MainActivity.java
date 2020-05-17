package com.agh.expenseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    AirplaneReceiver myReceiver = new AirplaneReceiver();
    IntentFilter filter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(MainActivity.this, MusicService.class));

        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        MainActivity.this.registerReceiver(myReceiver,filter);

        Intent i = new Intent(MainActivity.this, Login.class);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.this.unregisterReceiver(myReceiver);
    }

}
