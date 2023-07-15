package com.example.laundryapp.view.customer.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.laundryapp.R
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.databinding.ActivityDetailCustomerBinding
import com.example.laundryapp.view.lunas.detail.DetailPemesananViewModel

class DetailCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCustomerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}