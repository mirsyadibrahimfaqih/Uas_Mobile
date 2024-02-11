package com.irsyad.filmku.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.irsyad.filmku.R
import com.irsyad.filmku.ui.MainActivity

class SplashActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 3000 // Durasi splash screen dalam milidetik (misalnya 3000 ms)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Menggunakan Handler untuk menunda navigasi ke MainActivity
        Handler().postDelayed({
            // Navigasi ke MainActivity setelah splashTimeOut
            startActivity(Intent(this, MainActivity::class.java))
            // Mengakhiri SplashActivity agar tidak bisa dikembalikan dengan tombol back
            finish()
        }, splashTimeOut)
    }
}
