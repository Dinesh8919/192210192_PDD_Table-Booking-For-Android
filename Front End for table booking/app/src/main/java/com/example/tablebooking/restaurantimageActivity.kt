package com.example.tablebooking

import android.os.Bundle
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tablebooking.databinding.ActivityRestaurantimageBinding
import com.example.tablebooking.databinding.RvRestorentImagesLayoutBinding

class restaurantimageActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRestaurantimageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantimageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = ArrayList<RestaurantImageData>()
        data.add(RestaurantImageData(0,R.drawable.img_13))
        data.add(RestaurantImageData(1,R.drawable.img_14))
        data.add(RestaurantImageData(2,R.drawable.img_15))
        data.add(RestaurantImageData(3,R.drawable.img_16))
        val adapter = RestaurantImageAdapter(data, this)
        binding.restaurantImageRV.layoutManager = LinearLayoutManager(this)
        binding.restaurantImageRV.adapter = adapter

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    data class RestaurantImageData(val imageId:Int, val image:Int)

    class RestaurantImageAdapter(private val list:List<RestaurantImageData>, val activity:FragmentActivity) : RecyclerView.Adapter<RestaurantImageAdapter.MyViewHolder>() {

        class MyViewHolder(val adapterBinding:RvRestorentImagesLayoutBinding):RecyclerView.ViewHolder(adapterBinding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(RvRestorentImagesLayoutBinding.inflate(activity.layoutInflater, parent, false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data = list[position]
            holder.adapterBinding.image1.setImageResource(data.image)
        }
    }

}