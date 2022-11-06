package com.example.hostelgo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername,etPassword;
    private ProgressDialog loadingBar;
    String username, password;
    SharedPreferences sharedPref;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://peanut.db.elephantsql.com:5432/cmyolxsv";
    //  Database credentials
    static final String DB_USER = "cmyolxsv";
    static final String DB_PASSWORD = "kD5aGOde4TalzPSQIUbKnA_8WNLCmeOT";

    String retrieved_roll_no, retrieved_name, retrieved_ldap_password, retrieved_semester;
    boolean userExistsFlag = false;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_HostelGo);
        setContentView(R.layout.activity_login);

        sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        etUsername = findViewById(R.id.tietUsername);
        etPassword = findViewById(R.id.tiePassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if (!validInputs(username,password)) return;

                else {
//                    loadingBar.setTitle("Logging in");
//                    loadingBar.setMessage("Please wait while we are logging into you account...");
//                    loadingBar.show();
//                    loadingBar.setCanceledOnTouchOutside(true);

                    pgsqlcon pgcon = new pgsqlcon();
                    pgcon.execute();        //Connecting to database - async task
                }

            }
        });
    }

    private boolean validInputs(String username, String password) {

        if (username.isEmpty()){
            Toast.makeText(this, getString(R.string.username_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()){
            Toast.makeText(this, getString(R.string.password_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    //Connecting to Database via JDBC
    private class pgsqlcon extends AsyncTask<Void, Void, String> {

        public pgsqlcon() {
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
                sql = "SELECT * FROM student_details WHERE roll_no IN ('" + username.toUpperCase() + "', '" + username.toLowerCase() + "')";
                ResultSet rs = st.executeQuery(sql);

                //STEP 5: Extract data from result set
                while(rs.next()){
                    userExistsFlag = true;
                    //Retrieve by column name
//                    Log.i("QUERY RESULT", rs.toString());
                    retrieved_roll_no = rs.getString("roll_no");
                    retrieved_name = rs.getString("name");
                    retrieved_ldap_password = rs.getString("ldap_password");
                    retrieved_semester = rs.getString("semester");


                    //Display values
                    Log.i("roll_no", retrieved_roll_no);
                    Log.i("name" , retrieved_name);
                    Log.i("ldap_password" , retrieved_ldap_password);
                    Log.i("semester", retrieved_semester);
                }
                //STEP 6: Clean-up environment
                rs.close();
                st.close();
                conn.close();

            } catch (SQLException se) {
                //Handle errors for JDBC
                Log.i("printing from", "sqlexception");
                se.printStackTrace();
            } catch (Exception e) {
                //Handle errors for Class.forName
                e.printStackTrace();
            } finally {
//                userExistsFlag = false;
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
            if(!userExistsFlag)
                Toast.makeText(LoginActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
            else if(password.equals(retrieved_ldap_password)){
                sharedPref.edit().putString("currentUser", username).commit();     //Logging in
                SendUserToMainActivity();
            }else{
                Toast.makeText(LoginActivity.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}


