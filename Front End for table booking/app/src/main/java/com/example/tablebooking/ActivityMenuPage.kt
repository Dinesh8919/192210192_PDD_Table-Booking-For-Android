package com.example.tablebooking

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tablebooking.databinding.ActivityMenupageBinding

class ActivityMenuPage :AppCompatActivity() {

    private lateinit var binding: ActivityMenupageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMenupageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.home.setOnClickListener {
            val intent = Intent(this, activity_main2::class.java)
            startActivity(intent)
        }
        binding.contact.setOnClickListener {
            val intent = Intent(this, contactusActivity::class.java)
            startActivity(intent)
        }

        binding.profile.setOnClickListener {
            val intent = Intent(this, profileMainActivity::class.java)
            startActivity(intent)
        }


    }
}