package com.example.hostelgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class ApplyActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private EditText fullnameTv, enrolmentTv, semesterTv, addressTv, responsibilityTv, vehicleTv;
    private AppCompatButton saveButton;

    private String currentUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

//        currentUserID = mAuth.getCurrentUser().getUid();

        //mToolbar = (Toolbar)findViewById(R.id.apply_toolbar);
        //setSupportActionBar(mToolbar);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        fullnameTv = (EditText)findViewById(R.id.fullname_display);
        enrolmentTv = (EditText)findViewById(R.id.enrolment_display);
        semesterTv = (EditText)findViewById(R.id.semester_display);
        addressTv = (EditText)findViewById(R.id.address_display);
        responsibilityTv = (EditText)findViewById(R.id.responsibility_display);
        vehicleTv = (EditText)findViewById(R.id.vehicle_display);
        saveButton = (AppCompatButton) findViewById(R.id.update_profile_save_button);

        ImageView cvActivePass = (ImageView)findViewById(R.id.back_button_apply);
        cvActivePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAccountInfo();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

     */

    private void validateAccountInfo() {
        String fullname = fullnameTv.getText().toString();
        String enrolment = enrolmentTv.getText().toString();
        String semester = semesterTv.getText().toString();
        String address = addressTv.getText().toString();
        String responsibility = responsibilityTv.getText().toString();
        String vehicle = vehicleTv.getText().toString();

        if(TextUtils.isEmpty(fullname))
            Toast.makeText(this, "fullname field empty", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(semester))
            Toast.makeText(this, "semester field empty", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(address))
            Toast.makeText(this, "address field empty", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(responsibility))
            Toast.makeText(this, "responsibility field empty", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(vehicle))
            Toast.makeText(this, "vehicle field empty", Toast.LENGTH_SHORT).show();
        else if(enrolment.length() != 10)
            Toast.makeText(this, "Enter a valid Enrollemnt number", Toast.LENGTH_SHORT).show();
        else{
            HashMap userDetails = new HashMap();
            userDetails.put("fullname", fullname);
            userDetails.put("enrolment", enrolment);
            userDetails.put("semester", semester);
            userDetails.put("address", address);
            userDetails.put("responsibility", responsibility);
            userDetails.put("vehicle", vehicle);

        }
    }

    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(ApplyActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}