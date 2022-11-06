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
    String currentUser;      //Enrolment no
    String retrieved_roll_no = "", retrieved_name = "", retrieved_semester = "";

    private TextView fullnameTv, enrolmentTv, semesterTv;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://peanut.db.elephantsql.com:5432/cmyolxsv";
    //  Database credentials
    static final String DB_USER = "cmyolxsv";
    static final String DB_PASSWORD = "kD5aGOde4TalzPSQIUbKnA_8WNLCmeOT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        currentUser = sharedPref.getString("currentUser", "");

        TextView MainScreenText = findViewById(R.id.MainScreenText);
        fullnameTv = (TextView)findViewById(R.id.fullnameTv);
        enrolmentTv = (TextView) findViewById(R.id.enrolmentTv);
        semesterTv = (TextView) findViewById(R.id.semesterTv);


        MainActivity.pgsqlcon1 pgcon1 = new MainActivity.pgsqlcon1();
        pgcon1.execute();

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
                //sharedPref.edit().putString("currentUser", "").commit();
//            sharedPref.signOut();
                SendUserToLoginActivity();
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
                    retrieved_semester = rs.getString("semester");


                    //Display values
                    //Display values
                    Log.i("roll_no", retrieved_roll_no);
                    Log.i("name" , retrieved_name);
                    Log.i("semester", retrieved_semester);

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
                semesterTv.setText(retrieved_semester);
            }else{
                Toast.makeText(MainActivity.this, "User not found!", Toast.LENGTH_LONG).show();
            }
        }
    }



}