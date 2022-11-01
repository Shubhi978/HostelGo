package com.example.hostelgo;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ApplyActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private EditText fullnameTv, enrolmentTv, semesterTv, addressTv, purposeTv, vehicleTv, dateTv, timeTv;
    private AppCompatButton saveButton;

    String currentUser;      //Enrolment no
    String retrieved_roll_no = "", retrieved_name = "";

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://peanut.db.elephantsql.com:5432/cmyolxsv";
    //  Database credentials
    static final String DB_USER = "cmyolxsv";
    static final String DB_PASSWORD = "kD5aGOde4TalzPSQIUbKnA_8WNLCmeOT";

    String fullname, enrolment, semester, address, purpose, vehicle, outdate, outtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        SharedPreferences sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        currentUser = sharedPref.getString("currentUser", "");

        //mToolbar = (Toolbar)findViewById(R.id.apply_toolbar);
        //setSupportActionBar(mToolbar);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        fullnameTv = (EditText)findViewById(R.id.fullname_display);
        enrolmentTv = (EditText)findViewById(R.id.enrolment_display);
        semesterTv = (EditText)findViewById(R.id.semester_display);
        addressTv = (EditText)findViewById(R.id.address_display);
        purposeTv = (EditText)findViewById(R.id.purpose_display);
        vehicleTv = (EditText)findViewById(R.id.vehicle_display);
        dateTv = (EditText)findViewById(R.id.outdate_display);
        timeTv = (EditText)findViewById(R.id.outtime_display);
        saveButton = (AppCompatButton) findViewById(R.id.update_profile_save_button);

        pgsqlcon1 pgcon1 = new pgsqlcon1();
        pgcon1.execute();

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
                System.out.println("APPLY CLICKED!");
                if(validateAccountInfo()){
                    pgsqlcon2 pgcon2 = new pgsqlcon2();
                    pgcon2.execute();
                }

            }
        });

        dateTv.setShowSoftInputOnFocus(false);
        dateTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);

                return false;
            }
        });
        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        ApplyActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                String d = "" + year + "-";
                                if(monthOfYear + 1 < 10)
                                    d += "0" + (monthOfYear + 1) + "-";
                                else
                                    d += (monthOfYear + 1) + "-";
                                if(dayOfMonth < 10)
                                    d += "0" + dayOfMonth;
                                else
                                    d += dayOfMonth;
                                dateTv.setText(d);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        timeTv.setShowSoftInputOnFocus(false);
        timeTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);

                return false;
            }
        });
        timeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ApplyActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                String t = "";
                                if(hourOfDay < 10)
                                    t += "0" + hourOfDay;
                                else
                                    t += hourOfDay;
                                if(minute < 10)
                                    t += ":0" + minute + ":00";
                                else
                                    t += ":" + minute + ":00";
                                timeTv.setText(t);
                            }
                        }, hour, minute, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Connecting to Database via JDBC
    private class pgsqlcon1 extends AsyncTask<Void, Void, String> {

        public pgsqlcon1() {
            super();
        }

        @Override
        protected String doInBackground(Void... voids) {
            Connection conn = null;
            Statement st = null;
            try {
                //STEP 2: Register JDBC driver
                Class.forName(JDBC_DRIVER);

                //STEP 3: Open a connection
                System.out.println("Connecting to database...");
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                //STEP 4: Execute a query
                System.out.println("Creating statement...");
                st = conn.createStatement();
                String sql;
                sql = "SELECT * FROM student_details WHERE roll_no IN ('" + currentUser.toUpperCase() + "', '" + currentUser.toLowerCase() + "')";
                ResultSet rs = st.executeQuery(sql);

                //STEP 5: Extract data from result set
                while(rs.next()){
                    //Retrieve by column name
                    retrieved_roll_no = rs.getString("roll_no");
                    retrieved_name = rs.getString("name");


                    //Display values
                    //Display values
                    Log.i("roll_no", retrieved_roll_no);
                    Log.i("name" , retrieved_name);

                }
                //STEP 6: Clean-up environment
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException se) {
                //Handle errors for JDBC
                se.printStackTrace();
            } catch (Exception e) {
                //Handle errors for Class.forName
                e.printStackTrace();
            } finally {
                //finally block used to close resources
                try {
                    if (st != null)
                        st.close();
                } catch (SQLException se2) {
                }// nothing we can do
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }
            return "Returning from doInBackground()";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!retrieved_name.equals("")){
                fullnameTv.setText(retrieved_name);
                enrolmentTv.setText(retrieved_roll_no);
            }else{
                Toast.makeText(ApplyActivity.this, "User not found!", Toast.LENGTH_LONG).show();
            }
        }
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

    private boolean validateAccountInfo() {
        fullname = fullnameTv.getText().toString();
        enrolment = enrolmentTv.getText().toString();
        semester = semesterTv.getText().toString();
        address = addressTv.getText().toString();
        purpose = purposeTv.getText().toString();
        vehicle = vehicleTv.getText().toString();
        outdate = dateTv.getText().toString();
        outtime = timeTv.getText().toString();

        if(TextUtils.isEmpty(fullname))
            Toast.makeText(this, "fullname field empty", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(semester))
            Toast.makeText(this, "semester field empty", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(address))
            Toast.makeText(this, "address field empty", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(purpose))
            Toast.makeText(this, "purpose field empty", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(vehicle))
            Toast.makeText(this, "vehicle field empty", Toast.LENGTH_SHORT).show();
        else if(enrolment.length() != 10)
            Toast.makeText(this, "Enter a valid Enrollemnt number", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(outdate))
            Toast.makeText(this, "out date field empty", Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(outtime))
            Toast.makeText(this, "out time field empty", Toast.LENGTH_SHORT).show();
        else
            return true;

        return false;
    }

    public boolean isAutoApprovable(String req_time){
        String lower_limit = "06:00:00", upper_limit = "19:00:00";
        String pattern = "HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(req_time);
            Date date2 = sdf.parse(lower_limit);
            Date date3 = sdf.parse(upper_limit);

            if(date1.after(date2) && date1.before(date3)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }

    //Connecting to Database via JDBC
    private class pgsqlcon2 extends AsyncTask<Void, Void, String> {
        int res = 0;
        String pt_status = "", pt_automated = "";

        public pgsqlcon2() {
            super();
        }

        @Override
        protected String doInBackground(Void... voids) {
            Connection conn = null;
            Statement st = null;
            try {
                //STEP 2: Register JDBC driver
                Class.forName(JDBC_DRIVER);

                //STEP 3: Open a connection
                System.out.println("Connecting to database...");
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                //STEP 4: Execute a query
                System.out.println("Creating statement...");
                st = conn.createStatement();

                    //STEP 4.1: Constructing SQL query
                if(isAutoApprovable(outtime)){
                    pt_status = "Approved";
                    pt_automated = "true";
                }
                else{
                    pt_status = "Requested";
                    pt_automated = "false";
                }

                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                String sql = "INSERT INTO outpass_records (roll_no, date, approved_time, status, automated, name, semester, proceeding_to, purpose, vehicle" +
//                        ", issue_time" +
                        ")Values" +
                        "('" + enrolment +
                        "', '" + outdate +
                        "', '" + outtime +
                        "', '" + pt_status +
                        "', " + pt_automated +
                        ", '" + fullname +
                        "', " + semester +
                        ", '" + address +
                        "', '" + purpose +
                        "', '" + vehicle + "')";
//                        "', '" + vehicle +
//                        "', '" + currentTime + "')";

                res = st.executeUpdate(sql);

                if (res==1)
                    System.out.println("inserted successfully : "+sql);
                else
                    System.out.println("insertion failed");

                //STEP 5: Clean-up environment
                st.close();
                conn.close();
            } catch (SQLException se) {
                //Handle errors for JDBC
                se.printStackTrace();
            } catch (Exception e) {
                //Handle errors for Class.forName
                e.printStackTrace();
            } finally {
                //finally block used to close resources
                try {
                    if (st != null)
                        st.close();
                } catch (SQLException se2) {
                }// nothing we can do
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }
            return "Returning from doInBackground()";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (res == 1){
                if(pt_automated.equals("true"))
                    Toast.makeText(ApplyActivity.this, "Outpass has been auto approved.", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ApplyActivity.this, "Outpass has been requested.", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(ApplyActivity.this, "Form submission was unsuccessful!", Toast.LENGTH_LONG).show();
            }
        }
    }

//    private void sendUserToMainActivity() {
//        Intent mainIntent = new Intent(ApplyActivity.this,MainActivity.class);
//        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(mainIntent);
//        finish();
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
}