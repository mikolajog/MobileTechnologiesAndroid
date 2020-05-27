package com.agh.expenseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    /*
    Main class:
    - airplane receiver start
    - set notification channels
    - start music service
     */

    AirplaneReceiver myReceiver = new AirplaneReceiver();
    IntentFilter filter = new IntentFilter();
    private final String CHANNEL_ID = "personal_notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Start music serivce and register receiver
        super.onCreate(savedInstanceState);
        // Set view
        setContentView(R.layout.activity_main);
        // Start music service

        startService(new Intent(MainActivity.this, MusicService.class));
        // Notification channel init
        createNotificationChannel();
        // Action of airplane mode changes and receiver to it
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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Name";
            String description = "This is channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
