package com.example.tablebooking

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tablebooking.databinding.ActivityMain4Binding

class activity_main4 : AppCompatActivity() {

    lateinit var tableNo:String
    var selectedDate:String=""
    var amount:String=""

    private lateinit var binding: ActivityMain4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent!=null) {
            tableNo = intent.getStringExtra("tableNo").toString()
            amount = intent.getStringExtra("amount").toString()
        }

        binding.okButton.setOnClickListener {
            val intent = Intent(this, timeActivity::class.java)
            if(selectedDate.isEmpty()) {
                Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            intent.putExtra("date", selectedDate)
            intent.putExtra("tableNo", tableNo)
            startActivity(intent)
        }

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$year-${month + 1}-$dayOfMonth"
        }

        binding.bookingListButton.setOnClickListener {
            val intent = Intent(this, BookingListActivity::class.java)
            if(selectedDate.isEmpty()) {
                Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            intent.putExtra("date", selectedDate)
            intent.putExtra("tableNo", tableNo)
            startActivity(intent)
        }

    }
}