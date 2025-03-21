package com.example.tablebooking


import  java.util.ArrayList
 class  restaurantRespone(val status : Boolean ,val data:ArrayList<restaurantlist>)

  data class restaurantlist(val table_no: String, val amount:String, val table_name:String,val table_description : String,
      val table_images : String,val table_status : String,val   table_capacity:String)