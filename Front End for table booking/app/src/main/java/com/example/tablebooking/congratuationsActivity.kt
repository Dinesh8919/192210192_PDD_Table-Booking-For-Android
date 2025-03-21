package com.example.tablebooking

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tablebooking.databinding.ActivityCongratuationsBinding
import com.example.tablebooking.databinding.ActivityMenupageBinding

class congratuationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCongratuationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding= ActivityCongratuationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goToBookingsButton.setOnClickListener {
            val intent = Intent(this, confirmbookingMainActivity::class.java)
            startActivity(intent)
        }

    }
}