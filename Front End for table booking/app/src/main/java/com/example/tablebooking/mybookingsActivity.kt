package com.example.tablebooking

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tablebooking.databinding.ActivityMain3Binding
import com.example.tablebooking.databinding.ActivityMybookingsBinding

class mybookingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMybookingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mybookings)

        binding= ActivityMybookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cancleg.setOnClickListener {
            val intent = Intent(this, canclebookingActivity::class.java)
            startActivity(intent)
        }

    }
}