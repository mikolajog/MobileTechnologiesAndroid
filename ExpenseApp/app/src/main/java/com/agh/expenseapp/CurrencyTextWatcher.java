package com.agh.expenseapp;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class CurrencyTextWatcher implements TextWatcher {
    private final EditText et;
    private String current;

    public CurrencyTextWatcher(EditText editText) {
        this.current="";
        this.et = editText;
    }


    @Override
    public void afterTextChanged(Editable s) {
        if (!s.toString().equals(current)) {
            et.removeTextChangedListener(this);

            String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
            String cleanString = s.toString().replaceAll(replaceable, "");

            double parsed;
            try {
                parsed = Double.parseDouble(cleanString);
            } catch (NumberFormatException e) {
                parsed = 0.00;
            }
            String formatted = NumberFormat.getCurrencyInstance().format((parsed/100)).replace("$", "").replace(",","");
            current = formatted;
            et.setText(formatted);
            et.setSelection(formatted.length());
            et.addTextChangedListener(this);
        }
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub
    }
}