package com.example.payout

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.payout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCore.setOnClickListener {
            val intent = Intent(this,Core_series_activity::class.java)
            startActivity(intent)
        }
        binding.btnPremium.setOnClickListener {
            val intent = Intent(this, PremiumSeriesActivity::class.java)
            startActivity(intent)
        }

        binding.btnSuperPremium.setOnClickListener {
            val intent = Intent(this, SuperPremiumActivity::class.java)
            startActivity(intent)
        }

        binding.btnVelocity.setOnClickListener {
            val intent = Intent(this, VelocityActivity::class.java)
            startActivity(intent)
        }

        binding.btnUpfrontT2.setOnClickListener {
            val intent = Intent(this, UpfrontT2Activity::class.java)
            startActivity(intent)
        }
    }
}