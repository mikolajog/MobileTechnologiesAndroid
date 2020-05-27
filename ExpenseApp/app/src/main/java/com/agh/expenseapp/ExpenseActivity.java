package com.agh.expenseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

public class ExpenseActivity extends AppCompatActivity {
    /*
    Expense activity class, in which we handle:
    - creating and initializing menu or/and further fragments
    - changes in orientation screen
    - back button pressing (log off)
    - stopping music service
     */

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // method responsible to stop music service as well as set properly first fragments
        super.onCreate(savedInstanceState);

        // set view
        setContentView(R.layout.activity_expense);

        // get passed username
        user = getIntent().getStringExtra("user");

        // prepare to pass user to fragments
        Bundle arguments = new Bundle();
        arguments.putString("user", user);

        // Init 2 fragments, depending on orientation we will use one or both
        MenuFragment menuFragment = new MenuFragment();
        menuFragment.setArguments(arguments);

        StatisticsFragment statisticsFragment = new StatisticsFragment();
        statisticsFragment.setArguments(arguments);

        // Fragment manager used to add/replace fragments
        FragmentManager fm = getSupportFragmentManager();


        // Handling orientation setup
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fm.beginTransaction().replace(R.id.fragment_container, menuFragment).commit();
            fm.beginTransaction().replace(R.id.fragment_container2, statisticsFragment).commit();
        } else {
            fm.beginTransaction().replace(R.id.fragment_container, menuFragment).commit();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        /* Method responsible to reply to orientation changes. If it happens:
        - portrait to landscape: we set menu fragment in container 1 (left) and statistics on container 2(right)
        - landscape to portrait: we set menu as main fragment
         */
        super.onConfigurationChanged(newConfig);

        // Fragment manager used to change fragments
        FragmentManager fm = getSupportFragmentManager();

        // Prepare to pass argument to fragments
        Bundle arguments = new Bundle();
        arguments.putString("user", user);

        // PReparation of our fragments to set
        MenuFragment menuFragment = new MenuFragment();
        menuFragment.setArguments(arguments);

        StatisticsFragment statisticsFragment = new StatisticsFragment();
        statisticsFragment.setArguments(arguments);

        // Depending on orientation we set proper fragments
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            fm.beginTransaction().replace(R.id.fragment_container, menuFragment).commit();
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fm.beginTransaction().replace(R.id.fragment_container, menuFragment).commit();
            fm.beginTransaction().replace(R.id.fragment_container2, statisticsFragment).commit();
        }
    }

    @Override
    public void onBackPressed(){
        /* When we press back button on expense activity we need to log user off out of application
         We start music and switch to Login Activity.
         */
        if (!isMyServiceRunning(MusicService.class)) {
            startService(new Intent(ExpenseActivity.this, MusicService.class));
        }
        Intent i = new Intent(ExpenseActivity.this, Login.class);
        Toast.makeText(this, "Logged off", Toast.LENGTH_LONG).show();
        startActivity(i);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        // Method to check if Music Service is running
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
