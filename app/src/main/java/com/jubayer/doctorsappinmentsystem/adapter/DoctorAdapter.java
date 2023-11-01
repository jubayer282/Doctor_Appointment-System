package com.jubayer.doctorsappinmentsystem.adapter;

import static com.jubayer.doctorsappinmentsystem.databinding.ItemDoctorBinding.inflate;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jubayer.doctorsappinmentsystem.R;
import com.jubayer.doctorsappinmentsystem.DoctorDetailsActivity;
import com.jubayer.doctorsappinmentsystem.databinding.ItemDoctorBinding;
import com.jubayer.doctorsappinmentsystem.models.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorHolder> {
    List<Doctor> doctorList = new ArrayList<>();

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList = doctorList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoctorHolder(inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.onBind(doctor);
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class DoctorHolder extends RecyclerView.ViewHolder {
        ItemDoctorBinding binding;
        public DoctorHolder(@NonNull ItemDoctorBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void onBind(Doctor doctor) {
            // here perform all our doctor binding operation datastore
            Glide
                    .with(binding.getRoot().getContext())
                    .load(doctor.getImage())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(binding.bgImgDoctor);
            binding.tvDoctorName.setText(doctor.getName());

            binding.getRoot().setOnClickListener(view -> {
                Intent intent = new Intent(binding.getRoot().getContext(), DoctorDetailsActivity.class);
                intent.putExtra("doctor", doctor);
                binding.getRoot().getContext().startActivity(intent);
            });
        }
    }
}
