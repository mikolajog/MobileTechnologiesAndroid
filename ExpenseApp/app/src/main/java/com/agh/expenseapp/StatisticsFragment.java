package com.agh.expenseapp;

import android.content.res.Configuration;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {
    /*
    Class responisble for statistics
     */

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
        tv1 = v.findViewById(R.id.textSummary);
        tv2 = v.findViewById(R.id.balance);

        //gathering data from balance for particular user
        db = new DatabaseHelper(getContext());
        Cursor c = db.getBalance(user);
        String nodata = "NO DATA";

        if(!c.moveToFirst()){
            tv2.setText(nodata);
            tv1.setVisibility(View.INVISIBLE);
        }
        else {
            String summary = db.getBalanceSum(user);

            List<SummaryListData> summaryListData = new ArrayList();
            if (c.moveToFirst()) {
                do {
                    summaryListData.add(new SummaryListData(c.getString(2), c.getString(3), getProperCategoryIcon(c.getString(3))));
                } while (c.moveToNext());
            }
            c.close();
            db.close();

            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
            SummaryListAdapter adapter = new SummaryListAdapter(summaryListData);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);


              tv1.setText("PLN " + summary);

            if (Double.parseDouble(summary) <= 0)
                tv1.setTextColor(Color.RED);
            else tv1.setTextColor(Color.GREEN);

        }

        // Dealing with orientation and setting fragments accordingly
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            b1 = v.findViewById(R.id.backButton);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuFragment menuFragment = new MenuFragment();
                    menuFragment.setArguments(arguments);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, menuFragment);
                    transaction.commit();
                }
            });
        }

        return v;
    }

    private int getProperCategoryIcon(String name) {
        switch (name) {
            case "Bills":
                return R.drawable.icon_bills;
            case "Shopping":
                return R.drawable.icon_shopping;
            case "Mortgage Rate":
                return R.drawable.icon_mortgage;
            case "Food":
                return R.drawable.icon_food;
            case "Party":
                return R.drawable.icon_party;
            case "Gifts":
                return R.drawable.icon_gift;
            case "Others":
                return R.drawable.icon_others;
            case "Incoming":
                return R.drawable.icon_income;
            default:
                return R.drawable.icon_question;
        }
    }
}
