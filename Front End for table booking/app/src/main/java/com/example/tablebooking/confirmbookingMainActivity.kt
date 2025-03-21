package com.example.tablebooking

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tablebooking.databinding.ActivityConfirmbookingMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class confirmbookingMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmbookingMainBinding

    var tableNo:String = ""
    var amount:String = ""
    var date:String = ""
    var endTime:String = ""
    var startTime:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmbookingMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent!=null) {
            tableNo = intent.getStringExtra("tableNo").toString()
            amount = intent.getStringExtra("amount").toString()
            date = intent.getStringExtra("date").toString()
            startTime = intent.getStringExtra("startTime").toString()
            endTime = intent.getStringExtra("endTime").toString()
        }

        binding.bookingDateTV.text = date
        binding.startTimeTV.text = startTime
        binding.endTimeTV.text = endTime
        binding.tableNoTV.text = tableNo
        binding.amountTV.text = amount

        val sf  = getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
        val usreId = sf.getInt("id", 0)
        binding.confirmBtn.setOnClickListener {
            bookTable(userId = usreId, tableNo = tableNo, amount = amount.toInt(), startTime = startTime,
                endtime = endTime, tId = "", date = date)
        }
        binding.confirmBtn.setOnClickListener {
            val intent = Intent(this, congratuationsActivity::class.java)
            startActivity(intent)
        }

    }

    fun bookTable(userId:Int, tableNo:String, amount:Int, startTime:String,
                  endtime:String, tId:String, date:String) {
        RetrofitClient.api().booktable(
            user_id = userId, table_no = tableNo, amount = amount, starting_time = startTime,
            ending_time = endtime, transaction = tId, booking = date
        ).enqueue(object: Callback<bookResponse> {
            override fun onResponse(call: Call<bookResponse>, response: Response<bookResponse>) {
                if(response.isSuccessful) {
                    val intent = Intent(this@confirmbookingMainActivity, congratuationsActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@confirmbookingMainActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@confirmbookingMainActivity, Static.handleError(response.errorBody()!!.string()), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<bookResponse>, t: Throwable) {
                Toast.makeText(this@confirmbookingMainActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

}