package com.example.tablebooking // **Correct this to your actual package name**

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class activity_mainnew : AppCompatActivity() { // Or your actual activity class name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainnew) // Correct layout file name

        val dinneXLogo: ImageView = findViewById(R.id.dinneX_logo)
        // val dinneXText: TextView = findViewById(R.id.dinneX_text)  // Remove if not used
        val getStartedButton: Button = findViewById(R.id.get_started_button)

        dinneXLogo.setImageResource(R.drawable.img) // Correct image name


        extracted(getStartedButton)

        getStartedButton.setOnClickListener {
            val intent = Intent(this, log
                    in_screen::class.java)
            intent.putExtra("USERNAME", "John Doe")
            startActivity(intent)

            // Your click handling code here
        }
    }

    private fun extracted(getStartedButton: Button) {
        getStartedButton.background = ContextCompat.getDrawable(this, R.drawable.rounded_button)
    }
}