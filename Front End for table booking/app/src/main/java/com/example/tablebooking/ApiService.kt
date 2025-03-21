package com.example.tablebooking

import android.provider.ContactsContract.CommonDataKinds.Email
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import javax.security.auth.login.LoginException

interface ApiService {

    @FormUrlEncoded
    @POST("tableBooking/loggin_with_google.php")
    fun loginWithGoogle(@Field("token") token:String): Call<CommonResponse>

    @FormUrlEncoded
    @POST("tablebooking/signup.php")
    fun createAccount(@Field("name")name:String, @Field("email") userEmail:String,
                      @Field("password")password:String, @Field("phone")phone:String): Call<CommonResponse>

    @FormUrlEncoded
    @POST("tableBooking/login.php")
    fun Login(@Field("email")email:String, @Field("password")password: String): Call<CommonResponse>

    @GET("tableBooking/get_restaurants.php")
    fun tableList():Call<restaurantRespone>

    @GET("tableBooking/show_booking_details_to_customer.php")
    fun Bookinglist(@Query("booking_date")date:String,@Query("table_no")table_no:String):Call<BookingResponse>

    @FormUrlEncoded
    @POST("tableBooking/book_table.php")
    fun booktable(@Field("user_id")user_id:Int,@Field("table_no")table_no: String,
                        @Field("starting_time")starting_time:String,@Field("ending_time")ending_time:String,
                        @Field("booking_date")booking:String,@Field("amount")amount:Int,
                        @Field("transaction_id")transaction:String): Call<bookResponse>


}