package com.jubayer.doctorsappinmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jubayer.doctorsappinmentsystem.databinding.ActivityDoctorDetailsBinding;
import com.jubayer.doctorsappinmentsystem.models.Doctor;
import com.jubayer.doctorsappinmentsystem.room.DoctorRepository;
import com.jubayer.doctorsappinmentsystem.room.FavouriteDoctor;


public class DoctorDetailsActivity extends AppCompatActivity {
    ActivityDoctorDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        Doctor doctor = (Doctor) getIntent().getSerializableExtra("doctor");
        binding.tvName.setText(doctor.getName());
        binding.tcCategory.setText(doctor.getCategory());
        binding.tvDescription.setText(doctor.getDescription());
        binding.tvTime.setText("Time: " + doctor.getTime());
        binding.tvDate.setText("Schedule: " + doctor.getDate());

        /*appoinment button*/
        binding.appoinmentIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDetailsActivity.this, GetAppoinmentActivity.class));
            }
        });
/*
        binding.tvTime.setText(recipe.getTime());
*/

        Glide
                .with(DoctorDetailsActivity.this)
                .load(doctor.getImage())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.imgDoctor);

        if (doctor.getAuthorId().equalsIgnoreCase(FirebaseAuth.getInstance().getUid())) {
            binding.imgEdit.setVisibility(View.VISIBLE);
        } else {
            binding.imgEdit.setVisibility(View.GONE);
        }
        binding.imgEdit.setOnClickListener(view -> {
            Intent intent = new Intent(binding.getRoot().getContext(), AddDoctorActivity.class);
            intent.putExtra("doctor", doctor);
            intent.putExtra("isEdit", true);
            binding.getRoot().getContext().startActivity(intent);
        });
        checkFavourite(doctor);
        binding.imgFvrt.setOnClickListener(view -> {
            favouriteDoctor(doctor);
        });
        updateDateWithFireBase(doctor.getId());
    }

    private void checkFavourite(Doctor doctor) {
        DoctorRepository repository = new DoctorRepository(getApplication());
        boolean isFavourite = repository.isFavourite(doctor.getId());
        if (isFavourite) {
            binding.imgFvrt.setColorFilter(getResources().getColor(R.color.accent));
        } else {
            binding.imgFvrt.setColorFilter(getResources().getColor(R.color.black));
        }
    }

    private void favouriteDoctor(Doctor doctor) {
        DoctorRepository repository = new DoctorRepository(getApplication());
        boolean isFavourite = repository.isFavourite(doctor.getId());
        if (isFavourite) {
            repository.delete(new FavouriteDoctor(doctor.getId()));
            binding.imgFvrt.setColorFilter(getResources().getColor(R.color.black));
        } else {
            repository.insert(new FavouriteDoctor(doctor.getId()));
            binding.imgFvrt.setColorFilter(getResources().getColor(R.color.accent));
        }
    }

    private void updateDateWithFireBase(String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctors");
        reference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Doctor doctor = snapshot.getValue(Doctor.class);
                binding.tvName.setText(doctor.getName());
                binding.tcCategory.setText(doctor.getCategory());
                binding.tvDescription.setText(doctor.getDescription());
                binding.tvDate.setText("Schedule: " + doctor.getDate());

                Glide
                        .with(DoctorDetailsActivity.this)
                        .load(doctor.getImage())
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(binding.imgDoctor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled: ", error.toException());
            }
        });
    }

}