package com.example.tablebooking

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tablebooking.databinding.ActivityAvailableTablesBinding
import com.example.tablebooking.databinding.RvAvailableTablesBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AvailableTablesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAvailableTablesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvailableTablesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backArrowIV.setOnClickListener {
            finish()
        }

        getRestaurant()
    }

    private fun getRestaurant() {
        RetrofitClient.api().tableList().enqueue(object : Callback<restaurantRespone> {
            override fun onResponse(
                call: Call<restaurantRespone>,
                response: Response<restaurantRespone>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.data.isNotEmpty()) {
                        binding.availableTableRv.layoutManager = GridLayoutManager(this@AvailableTablesActivity, 2)
                        binding.availableTableRv.adapter = RestaurantTableAdapter(response.body()!!.data, this@AvailableTablesActivity)
                    } else {
                        Toast.makeText(this@AvailableTablesActivity, "Restaurant Not Found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    try {
                        val data = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(this@AvailableTablesActivity, data.getString("message"), Toast.LENGTH_SHORT).show()
                    } catch (t: Exception) {
                        Toast.makeText(this@AvailableTablesActivity, t.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<restaurantRespone>, t: Throwable) {
                Toast.makeText(this@AvailableTablesActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private class RestaurantTableAdapter(private val list: List<restaurantlist>, val activity: FragmentActivity) :
        RecyclerView.Adapter<RestaurantTableAdapter.MyViewHolder>() {

        class MyViewHolder(val adapterBinding: RvAvailableTablesBinding) : RecyclerView.ViewHolder(adapterBinding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(RvAvailableTablesBinding.inflate(activity.layoutInflater, parent, false))
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data = list[position]
            holder.adapterBinding.tableName.text = "Table Name: ${data.table_name}"
            holder.adapterBinding.tableNumber.text = "Table Number: ${data.table_no}"
            holder.adapterBinding.tableCapacity.text = "Table Capacity: ${data.table_capacity}"

            Glide.with(activity).load(RetrofitClient.BASE_URL + data.table_images).into(holder.adapterBinding.tableImage)

            // Set click listener on the item to pass table_no to the next activity
            holder.itemView.setOnClickListener {
                val intent = Intent(activity, activity_main4::class.java)
                intent.putExtra("tableNo", data.table_no)
                intent.putExtra("amount", data.amount)
                activity.startActivity(intent)
            }
        }
    }
}
