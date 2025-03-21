package com.example.tablebooking

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tablebooking.databinding.ActivityAdminMainBinding
import com.example.tablebooking.databinding.ActivityConfirmbookingMainBinding
import com.example.tablebooking.databinding.ActivityMenupageBinding

class adminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_admin_main)

        binding= ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addTables.setOnClickListener {
            val intent = Intent(this, AvailableTablesActivity::class.java)
            startActivity(intent)
        }

        binding.orders.setOnClickListener {
            val intent = Intent(this, OrderListActivity::class.java)
            startActivity(intent)
        }

        binding.deleteTable.setOnClickListener {
            val intent = Intent(this, AvailableTablesActivity::class.java)
            startActivity(intent)
        }

    }
}