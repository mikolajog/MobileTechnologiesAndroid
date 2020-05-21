package com.agh.expenseapp;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SpendFragment extends Fragment {

    private Button b1, b2;
    private EditText et1;
    DatabaseHelper db;
    String[] spinnerTitles;
    int[] spinnerImages;
    private Spinner spinner;
    String selectedText;
    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_spend, container, false);

        db = new DatabaseHelper(getContext());

        Bundle bundle = this.getArguments();
        final String user = bundle.get("user").toString();

        final Bundle arguments = new Bundle();
        arguments.putString("user", user);


        b1 = v.findViewById(R.id.backButton);
        et1 = v.findViewById(R.id.amountCash1);
        et1.addTextChangedListener(new NumberTextWatcher(et1));

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

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_money_notification);
        builder.setContentTitle("MONEY WARNING!");
        builder.setContentText("You're running out of money!");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());


        b2 = v.findViewById(R.id.earnSomeMoneyButton);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flagBalance = true;
                if (Double.parseDouble(db.getBalanceSum(user)) < 0.0) {
                    flagBalance = false;
                }
                db.insertNegativeBalance(user, et1.getText().toString(), selectedText);
                // validating fields and adding to DB

                if (Double.parseDouble(db.getBalanceSum(user)) <= 0 && flagBalance) {
                    notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
                }

                StatisticsFragment statisticsFragment = new StatisticsFragment();
                statisticsFragment.setArguments(arguments);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.expenseLayout, statisticsFragment);
                transaction.commit();
                Toast.makeText(getContext(), "You've just spent money!", Toast.LENGTH_LONG).show();
            }
        });


        spinner = v.findViewById(R.id.spinner);
        spinnerTitles = getResources().getStringArray(R.array.spend_purpose_array);
        spinnerImages = new int[]{
            R.drawable.icon_bills,
            R.drawable.icon_shopping,
            R.drawable.icon_mortgage,
            R.drawable.icon_food,
            R.drawable.icon_party,
            R.drawable.icon_gift,
            R.drawable.icon_others
        };

        SpinnerAdapter customAdapter = new SpinnerAdapter(getContext(), spinnerTitles, spinnerImages);
        spinner.setAdapter(customAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedText = spinnerTitles[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return v;
    }

}
