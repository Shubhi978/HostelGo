package com.example.hostelgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    String currentUser;      //Enrolment no

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextView MainScreenText = findViewById(R.id.MainScreenText);
        CardView cvActivePass = findViewById(R.id.cvActivePass);
        cvActivePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activePassIntent = new Intent(MainActivity.this, ActivePassActivity.class);
                startActivity(activePassIntent);
            }
        });
        CardView cvRecord = findViewById(R.id.cvRecord);
        cvRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recordIntent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(recordIntent);
            }
        });
        CardView cvApply = findViewById(R.id.cvApply);
        cvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent applyIntent = new Intent(MainActivity.this, ApplyActivity.class);
                startActivity(applyIntent);
            }
        });
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        currentUser = sharedPref.getString("currentUser", "");
        if (currentUser.equals(""))
        {
            Log.i("From MainActivity", "NO CURRENT USER FOUND!");
            SendUserToLoginActivity();
//            SendUserToApplyActivity();
        }
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }

    private void SendUserToApplyActivity() {
        Intent applyIntent = new Intent(MainActivity.this, ApplyActivity.class);
        applyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(applyIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        sharedPref.edit().putString("currentUser", "").commit();     //Logging out for now
    }
}