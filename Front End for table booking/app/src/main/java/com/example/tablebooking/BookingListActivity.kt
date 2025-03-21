package com.example.tablebooking

import BookingListAdapter
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tablebooking.databinding.ActivityBookingListMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class BookingListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingListMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingListMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent!=null) {
            val date = intent.getStringExtra("date").toString()
            val tableNo = intent.getStringExtra("tableNo").toString()
            getBookings(date = date, table_no = tableNo)
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            Toast.makeText(this, "Selected Date: $selectedDate", Toast.LENGTH_SHORT).show()

            // Fetch bookings for the selected date
            getBookings(selectedDate, "TAB01")
        }, year, month, day)

        datePicker.show()
    }

    private fun getBookings(date: String,table_no:String) {
        RetrofitClient.api().Bookinglist(date, table_no).enqueue(object : Callback<BookingResponse> {
            override fun onResponse(call: Call<BookingResponse>, response: Response<BookingResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    binding.recyclerViewBookings.layoutManager = LinearLayoutManager(this@BookingListActivity)
                        binding.recyclerViewBookings.adapter = response.body()!!.bookings?.let {
                            BookingListAdapter(
                                it
                            )
                        }
                    } else {
                        Toast.makeText(this@BookingListActivity, Static.handleError(response.errorBody()!!.string()), Toast.LENGTH_SHORT).show()
                    }
            }

            override fun onFailure(call: Call<BookingResponse>, t: Throwable) {
                Toast.makeText(this@BookingListActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
