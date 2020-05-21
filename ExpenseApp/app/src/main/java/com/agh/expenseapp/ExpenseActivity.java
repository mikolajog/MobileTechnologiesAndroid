package com.agh.expenseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

public class ExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        String user = getIntent().getStringExtra("user");

        Bundle arguments = new Bundle();
        arguments.putString("user", user);

        MenuFragment menuFragment = new MenuFragment();
        menuFragment.setArguments(arguments);
        StatisticsFragment statisticsFragment = new StatisticsFragment();
        statisticsFragment.setArguments(arguments);
        FragmentManager fm = getSupportFragmentManager();
//        int orientation = getResources().getConfiguration().orientation;
//        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            fm.beginTransaction().add(R.id.fragment_container, menuFragment).commit();
//            fm.beginTransaction().add(R.id.fragment_container2, statisticsFragment).commit();
//        } else {
//            fm.beginTransaction().add(R.id.expenseLayout, menuFragment).commit();
//        }
        fm.beginTransaction().add(R.id.expenseLayout, menuFragment).commit();

    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            setContentView(R.layout.fragment_menu);
//        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            setContentView(R.layout.fragment_menu_vertical);
//        }
//    }

    @Override
    public void onBackPressed(){
        if (!isMyServiceRunning(MusicService.class)) {
            startService(new Intent(ExpenseActivity.this, MusicService.class));
        }
        Intent i = new Intent(ExpenseActivity.this, Login.class);
        Toast.makeText(this, "Logged off", Toast.LENGTH_LONG).show();
        startActivity(i);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
