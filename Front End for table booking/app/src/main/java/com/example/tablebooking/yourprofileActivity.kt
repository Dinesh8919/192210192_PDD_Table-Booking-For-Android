package com.example.tablebooking

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tablebooking.databinding.ActivityLoginScreenBinding
import com.example.tablebooking.databinding.ActivityMybookingsBinding
import com.example.tablebooking.databinding.ActivityYourprofileBinding

class yourprofileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYourprofileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_yourprofile)
        binding = ActivityYourprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}