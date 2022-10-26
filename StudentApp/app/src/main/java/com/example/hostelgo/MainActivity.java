package com.example.hostelgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextView MainScreenText = findViewById(R.id.MainScreenText);
        CardView cvActivePass = findViewById(R.id.cvActivePass);
        cvActivePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rideIntent = new Intent(MainActivity.this, ActivePassActivity.class);
                startActivity(rideIntent);
            }
        });
        CardView cvRecord = findViewById(R.id.cvRecord);
        cvRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rideIntent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(rideIntent);
            }
        });
        CardView cvApply = findViewById(R.id.cvApply);
        cvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rideIntent = new Intent(MainActivity.this, ApplyActivity.class);
                startActivity(rideIntent);
            }
        });
    }
    @Override
    protected void onStart()
    {
        super.onStart();
//        if (currentUser == null)
//        {
//            SendUserToLoginActivity();
//            SendUserToApplyActivity();
//        }
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
}