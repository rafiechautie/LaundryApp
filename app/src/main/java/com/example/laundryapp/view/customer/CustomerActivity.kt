package com.example.laundryapp.view.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laundryapp.R
import com.example.laundryapp.databinding.ActivityCustomerBinding
import com.example.laundryapp.helper.ViewModelFactory
import com.example.laundryapp.view.lunas.LunasAdapter
import com.example.laundryapp.view.lunas.LunasViewModel

class CustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerBinding

    private lateinit var adapter: CustomerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBarTitle: String

        actionBarTitle = "Halaman Customer"

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val customerVieModel = obtainViewModel(this@CustomerActivity)

        customerVieModel.getCustomer().observe(this){customerList->
            if (customerList.isNotEmpty()){
                adapter.setListCustomer(customerList)
                binding.empty.visibility = View.GONE
                binding.tvEmpty.visibility = View.GONE
            }else{
                adapter.setListCustomer(emptyList())
                binding.empty.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.VISIBLE
                showToast("data tidak ada, silahkan isi data pemesanan")
            }

        }

        adapter = CustomerAdapter()

        binding.rvItemCustomer.layoutManager = LinearLayoutManager(this)
        binding.rvItemCustomer.setHasFixedSize(true)
        binding.rvItemCustomer.adapter = adapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Tambahkan kode untuk mengarahkan ke halaman home di sini
                onBackPressed() // Jika Anda ingin menavigasi ke halaman sebelumnya, jika ada
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): CustomerViewModel {

        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(CustomerViewModel::class.java)
    }
}