package com.example.hostelgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ActivePassActivity extends AppCompatActivity {

    String currentUser;      //Enrolment no
    String op_id = "", roll_no = "", out_date = "", out_time = "", pass_status = "", automated = "", stu_name = "", semester = "", proceeding_to = "", purpose = "", vehicle = "";
    TextView tv_op_id, tv_stu_name, tv_roll_no, tv_semester, tv_proceeding_to, tv_out_date, tv_out_time, tv_vehicle, tv_purpose, tv_pass_status, tv_automated;

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://peanut.db.elephantsql.com:5432/cmyolxsv";
    //  Database credentials
    static final String DB_USER = "cmyolxsv";
    static final String DB_PASSWORD = "kD5aGOde4TalzPSQIUbKnA_8WNLCmeOT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_pass);

        SharedPreferences sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        currentUser = sharedPref.getString("currentUser", "");

        tv_op_id = (TextView)findViewById(R.id.op_id_display);
        tv_stu_name = (TextView)findViewById(R.id.fullname_display);
        tv_roll_no = (TextView)findViewById(R.id.enrolment_display);
        tv_semester = (TextView)findViewById(R.id.semester_display);
        tv_proceeding_to = (TextView)findViewById(R.id.address_display);
        tv_out_date = (TextView)findViewById(R.id.date_display);
        tv_out_time = (TextView)findViewById(R.id.outtime_display);
        tv_vehicle = (TextView)findViewById(R.id.vehicle_display);
        tv_purpose = (TextView)findViewById(R.id.purpose_display);
        tv_pass_status = (TextView)findViewById(R.id.status_display);
        tv_automated = (TextView)findViewById(R.id.automated_display);

        ImageView cvActivePass = (ImageView)findViewById(R.id.back_button_pass);
        cvActivePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pgsqlcon pgcon = new pgsqlcon();
        pgcon.execute();
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
                sql = "SELECT * FROM Outpass_Records WHERE roll_no IN ('" + currentUser.toUpperCase() + "', '" + currentUser.toLowerCase() + "') AND status IN ('Approved') LIMIT 1";
                ResultSet rs = st.executeQuery(sql);
//                Log.i("Type of query result", String.valueOf(rs.getClass()));
//                Log.i("Query Result", String.valueOf(rs));

                //STEP 5: Extract data from result set
                while(rs.next()){
                    //Retrieve by column name
//                    Log.i("QUERY RESULT", rs.toString());
                    op_id = rs.getString("op_id");
                    roll_no = rs.getString("roll_no");
                    out_date = rs.getString("date");
                    out_time = rs.getString("approved_time");
                    pass_status = rs.getString("status");
                    automated = rs.getString("automated");
                    stu_name = rs.getString("name");
                    semester = rs.getString("semester");
                    proceeding_to = rs.getString("proceeding_to");
                    purpose = rs.getString("purpose");
                    vehicle = rs.getString("vehicle");


                    //Display values
                    Log.i("op_id", op_id);
                    Log.i("roll_no" , roll_no);
                    Log.i("out_date" , out_date);
                    Log.i("out_time" , out_time);
                    Log.i("pass_status", pass_status);
                    Log.i("automated" , automated);
                    Log.i("stu_name" , stu_name);
                    Log.i("semester" , semester);
                    Log.i("proceeding_to", proceeding_to);
                    Log.i("purpose" , purpose);
                    Log.i("vehicle" , vehicle);

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
            if (!op_id.equals("")){
                tv_op_id.setText(op_id);
                tv_stu_name.setText(stu_name);
                tv_roll_no.setText(roll_no);
                tv_semester.setText(semester);
                tv_proceeding_to.setText(proceeding_to);
                tv_out_date.setText(out_date);
                tv_out_time.setText(out_time);
                tv_vehicle.setText(vehicle);
                tv_purpose.setText(purpose);
                tv_pass_status.setText(pass_status);
                if(automated.equals("t"))
                    tv_automated.setText("Automated");
                else
                    tv_automated.setText("Non-Automated");
            }else{
                Toast.makeText(ActivePassActivity.this, "No Active Pass Found!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}