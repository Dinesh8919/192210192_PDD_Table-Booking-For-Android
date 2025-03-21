package com.example.tablebooking

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tablebooking.databinding.ActivityMain2Binding
import com.example.tablebooking.databinding.ActivityMenupageBinding

class activity_main2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRestaurant.setOnClickListener {
            val intent = Intent(this, activity_main3::class.java)
            startActivity(intent)
        }

        binding.btnBookTable.setOnClickListener {
            val intent = Intent(this, AvailableTablesActivity::class.java)
            startActivity(intent)
        }


    }
}