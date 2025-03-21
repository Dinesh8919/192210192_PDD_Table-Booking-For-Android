package com.example.tablebooking

import org.json.JSONObject

object Static {

    fun handleError(error:String):String {
        try {
            val json = JSONObject(error)
            return json.getString("message")
        } catch (e:Exception) {
            return e.message.toString()
        }
    }
}