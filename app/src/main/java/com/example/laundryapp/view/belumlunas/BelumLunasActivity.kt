package com.example.laundryapp.view.belumlunas

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laundryapp.R
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.databinding.ActivityBelumLunasBinding
import com.example.laundryapp.databinding.ActivityDetailLunasBinding
import com.example.laundryapp.helper.ViewModelFactory

class BelumLunasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBelumLunasBinding

    private lateinit var adapter: BelumLunasAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBelumLunasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBarTitle: String

        actionBarTitle = "Halaman Belum Lunas"

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val belumLunasViewModel = obtainViewModel(this@BelumLunasActivity)





        belumLunasViewModel.getAllPemesananBelumLunas().observe(this){ belumLunasList ->
            if (belumLunasList.isNotEmpty()){
                adapter.setListPemesananBelumLunas(belumLunasList)
                binding.empty.visibility = View.GONE
                binding.tvEmpty.visibility = View.GONE
//                showToast("data ada")
            }else{
                adapter.setListPemesananBelumLunas(emptyList())
                binding.empty.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.VISIBLE
                showToast("data tidak ada, silahkan isi data pemesanan")
            }
        }


        adapter = BelumLunasAdapter()


        binding.rvItemBelumLunas.layoutManager = LinearLayoutManager(this)
        binding.rvItemBelumLunas.setHasFixedSize(true)
        binding.rvItemBelumLunas.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): BelumLunasViewModel {

        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(BelumLunasViewModel::class.java)
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
}