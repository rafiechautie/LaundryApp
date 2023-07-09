package com.example.laundryapp.view.lunas

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laundryapp.R
import com.example.laundryapp.databinding.ActivityLunasBinding
import com.example.laundryapp.helper.ViewModelFactory

class LunasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLunasBinding
    private lateinit var adapter: LunasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLunasBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setUpView()

        val actionBarTitle: String

        actionBarTitle = "Halaman Pelunasan"

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val lunasVieModel = obtainViewModel(this@LunasActivity)


        lunasVieModel.getAllPemesananLunas().observe(this){lunasList->
            if (lunasList.isNotEmpty()){
                adapter.setListPemesananLunas(lunasList)
                binding.empty.visibility = View.GONE
                binding.tvEmpty.visibility = View.GONE
            }else{
                adapter.setListPemesananLunas(emptyList())
                binding.empty.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.VISIBLE
                showToast("data tidak ada, silahkan isi data pemesanan")
            }
        }

        adapter = LunasAdapter()

        binding.rvItemLunas.layoutManager = LinearLayoutManager(this)
        binding.rvItemLunas.setHasFixedSize(true)
        binding.rvItemLunas.adapter = adapter
    }

    private fun setUpView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
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

    private fun obtainViewModel(activity: AppCompatActivity): LunasViewModel {

        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(LunasViewModel::class.java)
    }
}