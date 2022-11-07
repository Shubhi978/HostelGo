package com.example.hostelgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    String currentUser, currentUserFullName, currentUserSemester;      //Enrolment no

    private TextView fullnameTv, enrolmentTv, semesterTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        currentUser = sharedPref.getString("currentUser", "");
        currentUserFullName = sharedPref.getString("currentUserFullName", "");
        currentUserSemester = sharedPref.getString("currentUserSemester", "");

//        CHECK LOGGED IN USER
        if (currentUser.equals("") || currentUserFullName.equals("") || currentUserSemester.equals("")){
            Log.i("From MainActivity", "NO CURRENT USER FOUND!");
            SendUserToLoginActivity();
        }

//        SET UI VARIABLES
        TextView MainScreenText = findViewById(R.id.MainScreenText);
        fullnameTv = (TextView)findViewById(R.id.fullnameTv);
        enrolmentTv = (TextView) findViewById(R.id.enrolmentTv);
        semesterTv = (TextView) findViewById(R.id.semesterTv);

        fullnameTv.setText(currentUserFullName);
        enrolmentTv.setText(currentUser);
        semesterTv.setText(currentUserSemester);

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

        ImageView logout_option = findViewById(R.id.LogoutIv);
        logout_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPref.edit().putString("currentUser", "").commit();
                sharedPref.edit().putString("currentUserFullName", "").commit();
                sharedPref.edit().putString("currentUserSemester", "").commit();
//            sharedPref.signOut();
                SendUserToLoginActivity();
            }
        });
    }

//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//        sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//        currentUser = sharedPref.getString("currentUser", "");
//        currentUserFullName = sharedPref.getString("currentUserFullName", "");
//        currentUserSemester = sharedPref.getString("currentUserSemester", "");
//        if (currentUser.equals("") || currentUserFullName.equals("") || currentUserSemester.equals(""))
//        {
//            Log.i("From MainActivity", "NO CURRENT USER FOUND!");
//            SendUserToLoginActivity();
//        }
//    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }
}