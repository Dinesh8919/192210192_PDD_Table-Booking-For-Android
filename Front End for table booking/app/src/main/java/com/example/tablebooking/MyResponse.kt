package com.example.tablebooking

data class CommonResponse(val status:Int, val message:String, val user:UserData)

data class UserData(val id:Int, val name:String, val email:String, val usertype:Int)

data class MyResponse(val message: String, val status: Int)

data class bookResponse(val message: String)