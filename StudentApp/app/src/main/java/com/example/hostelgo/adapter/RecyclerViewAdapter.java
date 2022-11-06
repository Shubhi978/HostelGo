package com.example.hostelgo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostelgo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<JSONObject> recordList;

    public RecyclerViewAdapter(Context context, List<JSONObject> recordList) {
        this.context = context;
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JSONObject record = recordList.get(position);

        try {
            holder.op_status.setText(record.getString("pass_status"));
            holder.op_date.setText(record.getString("out_date"));
            holder.op_location.setText(record.getString("proceeding_to"));
            holder.op_outtime.setText(record.getString("out_time"));
//            holder.op_intime.setText(record.getString("in_time"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView op_status;
        public TextView op_date;
        public TextView op_location;
        public TextView op_outtime;
        public TextView op_intime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            op_status = itemView.findViewById(R.id.textViewstatus);
            op_date = itemView.findViewById(R.id.textViewdate);
            op_location = itemView.findViewById(R.id.textViewlocation);
            op_outtime = itemView.findViewById(R.id.textViewOuttime);
            op_intime = itemView.findViewById(R.id.textViewIntime);
        }

        @Override
        public void onClick(View view) {
            Log.d("Item history","Clicked");
        }
    }
}
