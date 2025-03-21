package com.example.tablebooking

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tablebooking.databinding.ActivityMain3Binding
import com.example.tablebooking.databinding.ActivityMenupageBinding
import com.example.tablebooking.databinding.ActivityRestaurantimageBinding

class activity_main3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding=ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.photosbutton.setOnClickListener {
            val intent = Intent(this, restaurantimageActivity::class.java)
            startActivity(intent)
        }
        binding.backArrow.setOnClickListener {
            finish()
           }

        binding.reviewsButton.setOnClickListener {
            val intent = Intent(this, reviewMainActivity::class.java)
            startActivity(intent)
        }

        binding.aboutButton.setOnClickListener {
            val intent = Intent(this, aboutMainActivity::class.java)
            startActivity(intent)
        }

    }
}

