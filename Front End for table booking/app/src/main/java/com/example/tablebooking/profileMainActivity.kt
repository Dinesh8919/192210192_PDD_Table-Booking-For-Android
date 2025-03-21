package com.example.tablebooking

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tablebooking.databinding.ActivityMain3Binding
import com.example.tablebooking.databinding.ActivityProfileMainBinding

class profileMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logout.setOnClickListener {
            val intent = Intent(this, activity_mainnew::class.java)
            startActivity(intent)
        }

        binding.myBookings.setOnClickListener {
            val intent = Intent(this, mybookingsActivity::class.java)
            startActivity(intent)
        }

        binding.yourProfile.setOnClickListener {
            val intent = Intent(this, yourprofileActivity::class.java)
            startActivity(intent)
        }

    }
}