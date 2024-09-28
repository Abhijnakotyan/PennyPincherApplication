package com.example.pennypincherapplication.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pennypincherapplication.R
import com.example.pennypincherapplication.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText
    private lateinit var dlogin: TextView
    private lateinit var mLoginBtn: Button
    private lateinit var fAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mEmail = findViewById(R.id.et_username_or_email)
        mPassword = findViewById(R.id.et_password)
        mLoginBtn = findViewById(R.id.btn_login)
        dlogin = findViewById(R.id.tv_register_link)

        dlogin.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        fAuth = FirebaseAuth.getInstance()

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)

        // Check if the user is already logged in
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            // User is already logged in, redirect to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        mLoginBtn.setOnClickListener {
            val email = mEmail.text.toString().trim()
            val password = mPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and Password must be provided", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Firebase authentication
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                    // Save login session in SharedPreferences
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.putString("username", email) // Save username or any other user data
                    editor.apply()

                    // Redirect to MainActivity
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}