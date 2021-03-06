package com.agh.expenseapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AirplaneReceiver extends BroadcastReceiver {
    /*
    Class responsible for handling Airplane mode status changed.
    Registers as Broadcast Receiver
     */

    @Override
    public void onReceive(Context context, Intent intent) {
        /*
        Method makes a toast informing user to mind currency changes while abroad
         */
        String intentAction = intent.getAction();
        if (intentAction == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            Toast.makeText(context, "Please remember to convert a currency into PLN while abroad", Toast.LENGTH_LONG).show();
        }
    }
}
