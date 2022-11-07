package com.example.stopweapon.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stopweapon.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PendingRequestsRecyclerViewAdapter extends RecyclerView.Adapter<PendingRequestsRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<JSONObject> recordList;

    public PendingRequestsRecyclerViewAdapter(Context context, List<JSONObject> recordList) {
        this.context = context;
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_tile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JSONObject record = recordList.get(position);

        try {
            holder.op_name.setText(record.getString("stu_name"));
            holder.op_roll_no.setText(record.getString("roll_no"));
            holder.op_date.setText(record.getString("out_date"));
            holder.op_outtime.setText(record.getString("approved_time"));
            holder.op_location.setText(record.getString("proceeding_to"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView op_name, op_roll_no, op_date, op_outtime, op_location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            op_name = itemView.findViewById(R.id.textViewName);
            op_roll_no = itemView.findViewById(R.id.textViewRollno);
            op_date = itemView.findViewById(R.id.textViewDate);
            op_outtime = itemView.findViewById(R.id.textViewOuttime);
            op_location = itemView.findViewById(R.id.textViewLocation);
        }

        @Override
        public void onClick(View view) {
            Log.d("Item history","Clicked");
        }
    }
}
