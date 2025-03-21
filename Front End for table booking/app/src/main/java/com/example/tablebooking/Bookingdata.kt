package com.example.tablebooking

data class BookingResponse(
    val bookings: List<Booking>?, val message:String
)

data class Booking(
    val booking_id: Int,
    val table_no: String,
    val starting_time: String,
    val ending_time: String,
    val booking_date: String,
    val amount: Int,
    val transaction_id: String,
    val user_id: Int,
    val name: String,
    val email: String,
    val phone: String?,
    val usertype: Int
)
