package com.example.pennypincherapplication.activity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.pennypincherapplication.R
import com.example.pennypincherapplication.activity.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set content view to your splash screen layout (or use a drawable directly)
        setContentView(R.layout.activity_splash)

        // Use Handler to delay the transition to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish() // Close SplashActivity
        }, 3000) // 3000 milliseconds = 3 seconds delay
    }
}
