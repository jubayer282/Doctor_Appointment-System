package com.jubayer.doctorsappinmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jubayer.doctorsappinmentsystem.databinding.ActivityLiveChatBotBinding;

public class LiveChatBotActivity extends AppCompatActivity {
    ActivityLiveChatBotBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLiveChatBotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}