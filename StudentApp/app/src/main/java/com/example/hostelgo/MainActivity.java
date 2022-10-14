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
        CardView cvRecord = findViewById(R.id.cvRecord);
        CardView cvApply = findViewById(R.id.cvApply);
    }
    @Override
    protected void onStart()
    {
        super.onStart();
//        if (currentUser == null)
//        {
            SendUserToLoginActivity();
//        }
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }
}