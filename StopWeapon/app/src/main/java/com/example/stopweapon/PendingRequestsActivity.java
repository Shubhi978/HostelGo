package com.example.stopweapon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.stopweapon.adapter.PendingRequestsRecyclerViewAdapter;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PendingRequestsActivity extends AppCompatActivity {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://peanut.db.elephantsql.com:5432/cmyolxsv";
    //  Database credentials
    static final String DB_USER = "cmyolxsv";
    static final String DB_PASSWORD = "kD5aGOde4TalzPSQIUbKnA_8WNLCmeOT";

    private RecyclerView recyclerView;
    private PendingRequestsRecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<JSONObject> recordArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_requests);

        System.out.println("ENTERED PENDING REQUESTS ACTIVITY");

        recyclerView = findViewById(R.id.rvPending);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recordArrayList = new ArrayList<>();
        recyclerViewAdapter = new PendingRequestsRecyclerViewAdapter(PendingRequestsActivity.this, recordArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);

        ImageView backRecord = findViewById(R.id.backButtonRecord);
        backRecord.setOnClickListener(new View.OnClickListener() {
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

                sql = "SELECT * FROM Outpass_Records WHERE status = 'Requested'";
                ResultSet rs = st.executeQuery(sql);

                //STEP 5: Extract data from result set
                while(rs.next()){
                    //Retrieve by column name
//                    Log.i("QUERY RESULT", rs.toString());
                    String op_id = rs.getString("op_id");
                    String roll_no = rs.getString("roll_no");
                    String out_date = rs.getString("date");
                    String pass_status = rs.getString("status");
                    String automated = rs.getString("automated");
                    String stu_name = rs.getString("name");
                    String semester = rs.getString("semester");
                    String proceeding_to = rs.getString("proceeding_to");
                    String purpose = rs.getString("purpose");
                    String vehicle = rs.getString("vehicle");
                    String issue_time = rs.getString("issue_time");
                    String out_time = rs.getString("out_time");
                    String in_time = rs.getString("in_time");
                    String approved_time = rs.getString("approved_time");


                    //Display values
                    Log.i("op_id", op_id);
                    Log.i("roll_no" , roll_no);
                    Log.i("out_date" , out_date);
//                    Log.i("out_time" , out_time);
                    Log.i("pass_status", pass_status);
                    Log.i("automated" , automated);
                    Log.i("stu_name" , stu_name);
                    Log.i("semester" , semester);
                    Log.i("proceeding_to", proceeding_to);
                    Log.i("purpose" , purpose);
                    Log.i("vehicle" , vehicle);
//                    Log.i("issue_time", issue_time);
//                    Log.i("in_time" , in_time);
                    Log.i("approved_time" , approved_time);

                    String json_string = "{\n" +
                            "  \"op_id\":\"" + op_id +"\",\n" +
                            "  \"roll_no\":\"" + roll_no +"\",\n" +
                            "  \"out_date\":\"" + out_date +"\",\n" +
                            "  \"pass_status\":\"" + pass_status +"\",\n" +
                            "  \"automated\":\"" + automated +"\",\n" +
                            "  \"stu_name\":\"" + stu_name +"\",\n" +
                            "  \"semester\":\"" + semester +"\",\n" +
                            "  \"proceeding_to\":\"" + proceeding_to +"\",\n" +
                            "  \"purpose\":\"" + purpose +"\",\n" +
                            "  \"vehicle\":\"" + vehicle +"\",\n" +
                            "  \"out_time\":\"" + out_time +"\",\n" +
                            "  \"in_time\":\"" + in_time +"\",\n" +
                            "  \"approved_time\":\"" + approved_time +"\",\n" +
                            "  \"issue_time\":\"" + issue_time +"\"\n" +
                            "}";

                    recordArrayList.add(new JSONObject(json_string));
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
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }
}