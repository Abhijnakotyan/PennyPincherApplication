package com.example.pennypincherapplication.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pennypincherapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {
    companion object {
        const val TAG = "UserRegisterTAG"
    }

    private lateinit var mFullName: EditText
    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText
    private lateinit var mRegisterBtn: Button
    private lateinit var mLoginBtn: TextView
    private lateinit var fAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth and Firestore
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        // Check if the user is already logged in
        if (fAuth.currentUser != null) {
            // User is signed in, redirect to the main activity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Initialize views
        mFullName = findViewById(R.id.et_username)
        mEmail = findViewById(R.id.et_email)
        mPassword = findViewById(R.id.et_password)
        mRegisterBtn = findViewById(R.id.btn_register)
        mLoginBtn = findViewById(R.id.tv_login_link)

        // Register button click listener
        mRegisterBtn.setOnClickListener {
            registerUser()
        }

        // Login button click listener
        mLoginBtn.setOnClickListener {
            // Check if the user is already logged in before proceeding
            if (fAuth.currentUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    private fun registerUser() {
        val name = mFullName.text.toString().trim()
        val email = mEmail.text.toString().trim()
        val password = mPassword.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            mEmail.error = "Email is Required."
            return
        }

        if (TextUtils.isEmpty(password)) {
            mPassword.error = "Password is Required."
            return
        }

        if (password.length < 6) {
            mPassword.error = "Password Must be >= 6 Characters"
            return
        }

        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "User Created.", Toast.LENGTH_SHORT).show()
                userID = fAuth.currentUser!!.uid
                saveUserData(name, email)
            } else {
                Toast.makeText(this, "Error! ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserData(name: String, email: String) {
        val documentReference: DocumentReference = fStore.collection("newsapp").document(userID)
        val user: MutableMap<String, Any> = HashMap()
        user["name"] = name
        user["email"] = email

        documentReference.set(user)
            .addOnSuccessListener {
                Log.d(TAG, "User Profile created for user ID: $userID")
                // Redirect to LoginActivity after successfully saving user data
                startActivity(Intent(this, LoginActivity::class.java))
                finish() // Optionally finish the current activity
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                Toast.makeText(this, "Failed to create user profile.", Toast.LENGTH_SHORT).show()
            }
    }
}
