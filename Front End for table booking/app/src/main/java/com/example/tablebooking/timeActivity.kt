package com.example.tablebooking

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tablebooking.databinding.ActivityTimeBinding
import java.util.*

class timeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimeBinding
    private var tableNo:String = ""
    private var date:String = ""
    private var startingTime:String = ""
    private var endingTime:String = ""
    private var amount:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent!=null) {
            tableNo = intent.getStringExtra("tableNo").toString()
            date = intent.getStringExtra("date").toString()
            amount = intent.getStringExtra("amount").toString()
        }

        // Start Time Picker
        binding.startTime.setOnClickListener {
            showTimePicker { hour, minute ->
                startingTime = String.format("%02d:%02d", hour, minute)
                binding.startTime.setText(String.format("%02d:%02d:%02d", hour, minute, 0))
            }
        }

        // End Time Picker
        binding.endTime.setOnClickListener {
            showTimePicker { hour, minute ->
                endingTime = String.format("%02d:%02d", hour, minute)
                binding.endTime.setText(String.format("%02d:%02d:%2d", hour, minute, 0))
            }
        }

        // Button Click to Next Page
        binding.okc.setOnClickListener {
            val intent = Intent(this, confirmbookingMainActivity::class.java)
            if(startingTime.isEmpty() || endingTime.isEmpty()) {
                Toast.makeText(this, "Select Time", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            intent.putExtra("tableNo", tableNo)
            intent.putExtra("date", date)
            intent.putExtra("startTime", startingTime)
            intent.putExtra("amount", amount)
            intent.putExtra("endTime", endingTime)
            startActivity(intent)
        }
    }

    private fun showTimePicker(onTimeSet: (hour: Int, minute: Int) -> Unit) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            onTimeSet(selectedHour, selectedMinute)
        }, hour, minute, true) // true for 24-hour format

        timePicker.show()
    }
}
