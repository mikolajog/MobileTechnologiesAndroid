package com.agh.expenseapp;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class StatisticsFragment extends Fragment {

    Button b1;
    TextView tv1;
    TextView tv2;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //view
        View v = inflater.inflate(R.layout.fragment_statistics, container, false);

        //get user name
        Bundle bundle = this.getArguments();
        String user = bundle.get("user").toString();

        //set user name for next fragment
        final Bundle arguments = new Bundle();
        arguments.putString("user", user);

        // initialization
        b1  = v.findViewById(R.id.backButton);
        tv1 = v.findViewById(R.id.textView2);
        tv2 = v.findViewById(R.id.textSummary);

        //gathering data from balance for particular user
        db = new DatabaseHelper(getContext());
        Cursor c = db.getBalance(user);
        c.moveToFirst();
        String result = "";
        String summary="";

        if (c.moveToFirst()){
            do {
                // Passing values
                result += c.getString(0) + " ";
                result += c.getString(1) + " ";
                result += c.getString(2) + " ";
                result += c.getString(3) + "\n";
                // Do something Here with values
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        tv1.setText(result);
        summary = db.getBalanceSum(user);
        tv2.setText("PLN " + summary);
        if(Double.parseDouble(summary) <= 0)
            tv2.setTextColor(Color.RED);
        else tv2.setTextColor(Color.GREEN);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuFragment menuFragment = new MenuFragment();
                menuFragment.setArguments(arguments);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.expenseLayout, menuFragment);
                transaction.commit();
            }
        });

        return v;
    }

}
