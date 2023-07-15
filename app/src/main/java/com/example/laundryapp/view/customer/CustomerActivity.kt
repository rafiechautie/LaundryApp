package com.example.laundryapp.view.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.laundryapp.R
import com.example.laundryapp.databinding.ActivityCustomerBinding

class CustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}