package com.example.movies_ch6_binar.ui.SpalashScreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.movies_ch6_binar.MainActivity
import com.example.movies_ch6_binar.R
import com.example.movies_ch6_binar.databinding.ActivitySplashScreenBinding
import com.example.movies_ch6_binar.ui.Login.LoginActivity
import com.example.movies_ch6_binar.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startSplashScreen()
    }

    private fun startSplashScreen() {
        binding.logo.alpha = 0f
        binding.logo.animate().setDuration(2000).alpha(1f).withEndAction {
            checkCredential()
        }
    }

    private fun checkCredential() {
        viewModel.email.observe(this) { email ->
            if (email.isNotEmpty()) {
                Log.d(Constant.TAG, "email -> $email")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}