package com.example.tablebooking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tablebooking.databinding.ActivityOrderListBinding

class OrderListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOrderListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backArrowIV.setOnClickListener {
            finish()
            }

    }
}
