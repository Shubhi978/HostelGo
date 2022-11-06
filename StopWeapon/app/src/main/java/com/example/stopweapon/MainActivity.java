package com.example.stopweapon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    String currentUser, currentUserFullName;
    private TextView fullnameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        currentUser = sharedPref.getString("currentUser", "");
        currentUserFullName = sharedPref.getString("currentUserFullName", "Name");

//        CHECK LOGGED IN USER
//        if (currentUser.equals("") || currentUserFullName.equals("")){
//            Log.i("From MainActivity", "NO CURRENT USER FOUND!");
//            SendUserToLoginActivity();
//        }

//        SET UI VARIABLES
        fullnameTv = (TextView)findViewById(R.id.fullnameTv);
        fullnameTv.setText(currentUserFullName);

        CardView cvPendingRequests = findViewById(R.id.cvPendingRequests);
        cvPendingRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activePassIntent = new Intent(MainActivity.this, PendingRequestsActivity.class);
                startActivity(activePassIntent);
            }
        });
        CardView cvTodaysOutpasses = findViewById(R.id.cvTodaysOutpasses);
        cvTodaysOutpasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recordIntent = new Intent(MainActivity.this, OutpassHistoryActivity.class);
                startActivity(recordIntent);
            }
        });

        ImageView logout_option = findViewById(R.id.LogoutIv);
        logout_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sharedPref.edit().putString("currentUser", "").commit();
//                sharedPref.edit().putString("currentUserFullName", "").commit();
//                SendUserToLoginActivity();
            }
        });
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }
}