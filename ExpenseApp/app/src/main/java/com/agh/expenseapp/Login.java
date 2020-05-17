package com.agh.expenseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText e1, e2;
    Button b1, b2;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        e1 = (EditText)findViewById(R.id.login);
        e2 = (EditText)findViewById(R.id.password);
        b1 = (Button) findViewById(R.id.loginButton);
        b2 = (Button) findViewById(R.id.registerButton);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = e1.getText().toString();
                String password = e2.getText().toString();
                Boolean chkemailpass = db.emailpassword(email, password);
                if(chkemailpass){
                    Toast.makeText(getApplicationContext(), "Successfulll login", Toast.LENGTH_SHORT).show();
                    stopService(new Intent(Login.this, MusicService.class));
                    Intent i = new Intent(Login.this, ExpenseActivity.class);
                    i.putExtra("user", e1.getText().toString());
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
