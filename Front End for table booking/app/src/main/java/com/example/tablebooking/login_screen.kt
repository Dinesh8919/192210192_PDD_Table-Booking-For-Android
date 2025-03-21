package com.example.tablebooking

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import com.example.tablebooking.databinding.ActivityLoginScreenBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInPassword
import com.google.android.gms.common.api.ApiException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class login_screen : AppCompatActivity() {
    private lateinit var binding: ActivityLoginScreenBinding
    private lateinit var credentialManager: CredentialManager
    private lateinit var oneTapClient: SignInClient

    private lateinit var sp:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Ensure this is at the beginning
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        credentialManager = CredentialManager.create(this)
        oneTapClient = Identity.getSignInClient(this)
        sp = getSharedPreferences("LOGIN", Context.MODE_PRIVATE)

        // Handle Login Button Click
        binding.btnLogin.setOnClickListener {
            val email = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill All The Fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            login(email, password)
        }

        // Handle Register Button Click
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this@login_screen, activity_signup_screen::class.java)
            startActivity(intent)
        }

        // Handle Google Sign-In Button Click
        binding.continueWithGoogleB.setOnClickListener {
            signInWithGoogle()
        }
    }

    fun login(email: String, password: String) {
        RetrofitClient.api().Login(email = email, password = password).enqueue(object:Callback<CommonResponse>{
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                if(response.isSuccessful) {
                    val intent:Intent?
                    if(response.body()!!.user.usertype==111) {
                        intent = Intent(this@login_screen, adminMainActivity::class.java)
                    } else {
                        intent = Intent(this@login_screen, activity_main2::class.java)
                    }
                    val editor = sp.edit()

//                    Toast.makeText(this@login_screen, "hjfijv ${response.body()!!.user.usertype}", Toast.LENGTH_SHORT).show()
                    editor.putInt("usertype", response.body()!!.user.usertype)
                    editor.putInt("id", response.body()!!.user.id)
                    editor.putString("email", response.body()!!.user.email)
                    editor.putString("name", response.body()!!.user.name)
                    editor.apply()
                    Toast.makeText(this@login_screen, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                }
                else {
                    try {
                        val data = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(this@login_screen, data.getString("message"), Toast.LENGTH_SHORT).show()
                    }catch (t:Exception) {
                        Toast.makeText(this@login_screen, t.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Toast.makeText(this@login_screen, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun signInWithGoogle() {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId("1062686754208-0oumjmaf09tttshh7h275udupu0qbs75.apps.googleusercontent.com") // Replace with your Web Client ID
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                startIntentSenderForResult(
                    result.pendingIntent.intentSender, 100,
                    null, 0, 0, 0, null
                )
            }
            .addOnFailureListener { e ->
                // Handle failure
                Toast.makeText(this, "failure ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("login_screen", "failure ${e.message}")
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(data)
                val idToken = credential.googleIdToken
                if (idToken != null) {
                    // Use the ID token to authenticate with your backend
                    authenticateWithBackend(idToken)
                }
            } catch (e: ApiException) {
                // Handle error
                Log.e("login_screen", "ApiException ${e.message}")
                Toast.makeText(this, "ApiException ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun authenticateWithBackend(idToken: String) {
        // Send the ID token to your backend for authentication
        Log.e("login_screen", "token $idToken")
        

        RetrofitClient.api().loginWithGoogle(idToken).enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                if(response.isSuccessful) {
                    val intent = Intent(this@login_screen, ActivityMenuPage::class.java)
                    startActivity(intent)
                    Toast.makeText(this@login_screen, response.body()!!.message, Toast.LENGTH_SHORT).show()
                } else {
                    try {
                        val obj = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(
                            this@login_screen,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }catch (e:Exception ) {
                        Toast.makeText(
                            this@login_screen,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Toast.makeText(this@login_screen, t.message, Toast.LENGTH_SHORT).show()
            }

        })


//        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
    }
}