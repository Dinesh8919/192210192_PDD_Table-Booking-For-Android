package com.example.tablebooking

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tablebooking.databinding.ActivityAvailableTablesBinding

class restaurantadapter : AppCompatActivity() {

    private lateinit var binding: ActivityAvailableTablesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvailableTablesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.availableTableRv.setOnClickListener() {
            val intent = Intent(this, activity_main4::class.java)
            startActivity(intent)
        }
    }
}