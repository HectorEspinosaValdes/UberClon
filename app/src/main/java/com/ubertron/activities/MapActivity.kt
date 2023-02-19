package com.ubertron.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ubertron.R
import com.ubertron.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    lateinit var binding: ActivityMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}