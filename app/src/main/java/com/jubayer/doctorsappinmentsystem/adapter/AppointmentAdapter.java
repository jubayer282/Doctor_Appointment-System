package com.jubayer.doctorsappinmentsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jubayer.doctorsappinmentsystem.R;
import com.jubayer.doctorsappinmentsystem.models.AppoinmentData;

import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyHolder> {

    ArrayList<AppoinmentData> list;
    Context context;

    public AppointmentAdapter(ArrayList<AppoinmentData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AppointmentAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.details_list_item, parent, false);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.MyHolder holder, int position) {

        AppoinmentData data = list.get(position);

        holder.nameTv.setText("Name: " + data.getName());
        holder.moblieTv.setText("Phone Number: " + data.getMobile());
        holder.timeTv.setText("Appointment Time: " + data.getTime());
        holder.dayTv.setText("Day of Week: " + data.getDay());
        holder.statusTv.setText("Status: " + data.getStatus());
        holder.drnameTv.setText("Dr. Name: " + data.getDrname());
        holder.uidTv.setText("user_id: " + data.getUid());

         String statu = data.getStatus();

        holder.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String statusTv = "approved";

                if (statu.equals("unapproved")) {

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appointment").child(data.getUid());

                    AppoinmentData appoinmentData = new AppoinmentData(statusTv);
                    databaseReference.child(data.getStatus()).setValue(appoinmentData);
                    Toast.makeText(context, "approved", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Not approved", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView nameTv, moblieTv, timeTv, dayTv, statusTv, drnameTv, uidTv;
        Button approveBtn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.nameTv);
            moblieTv = itemView.findViewById(R.id.mobileTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            dayTv = itemView.findViewById(R.id.dayTv);
            statusTv = itemView.findViewById(R.id.statusTv);
            approveBtn = itemView.findViewById(R.id.approveBtn);
            drnameTv = itemView.findViewById(R.id.drnameTv);
            uidTv = itemView.findViewById(R.id.uidTv);


        }
    }
}
