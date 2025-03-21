package com.example.tablebooking
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tablebooking.databinding.ActivitySignupScreenBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Adjust package name accordingly


class activity_signup_screen : AppCompatActivity() {
    private lateinit var binding:ActivitySignupScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener {
            val username = binding.username.text.toString()
            val email = binding.emailET.text.toString()
            val phone = binding.phoneET.text.toString()
            val password = binding.password.text.toString()
            val reenteredPassword = binding.reenterPassword.text.toString()

            if(username.isEmpty() || email.isEmpty() || phone.isEmpty()
                ||password.isEmpty() || reenteredPassword.isEmpty()) {
                Toast.makeText(this, "Fill All The Fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password == reenteredPassword) {
                createAccount(username, email, phone, password)
            } else {
                binding.reenterPassword.error = "Passwords do not match!"
            }
        }
    }

    fun createAccount(username:String, email:String, phone:String, password:String) {
        RetrofitClient.api().createAccount(userEmail = email, name = username, password = password,
            phone = phone).enqueue(object:Callback<CommonResponse>{
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                if(response.isSuccessful) {
                    Toast.makeText(this@activity_signup_screen, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    try {
                        val data = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(this@activity_signup_screen, data.getString("message"), Toast.LENGTH_SHORT).show()
                    }catch (t:Exception) {
                        Toast.makeText(this@activity_signup_screen, t.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Toast.makeText(this@activity_signup_screen, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}
