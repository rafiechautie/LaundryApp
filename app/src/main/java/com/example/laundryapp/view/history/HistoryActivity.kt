package com.example.laundryapp.view.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laundryapp.R
import com.example.laundryapp.databinding.ActivityHistoryBinding
import com.example.laundryapp.helper.FunctionHelper
import com.example.laundryapp.helper.ViewModelFactory
import com.example.laundryapp.view.lunas.LunasAdapter
import com.example.laundryapp.view.lunas.LunasViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val historyVieModel = obtainViewModel(this@HistoryActivity)

        // Contoh pemanggilan data pemesanan berdasarkan bulan
//        val month = "06"
        // Contoh pemanggilan data pemesanan berdasarkan tahun
//        val year = "2023" // Ganti dengan tahun yang diinginkan

        val actionBarTitle: String

        actionBarTitle = "Halaman History"

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Tampilkan layoutBulan saat halaman baru dibuka
        binding.layoutBulan.visibility = View.VISIBLE
        binding.layoutTahun.visibility = View.GONE

        binding.radioGroupTransaksi.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.radioButtonBulan-> {
                    binding.layoutBulan.visibility = View.VISIBLE
                    binding.layoutTahun.visibility = View.GONE

                }
                R.id.radioButtonTahun -> {
                    binding.layoutBulan.visibility = View.GONE
                    binding.layoutTahun.visibility = View.VISIBLE
                }

            }
        }

        val bulanAdapter = ArrayAdapter.createFromResource(this, R.array.bulan_array, android.R.layout.simple_spinner_item)
        bulanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerBulan.adapter = bulanAdapter

        val tahunAdapter = ArrayAdapter.createFromResource(this, R.array.tahun_array, android.R.layout.simple_spinner_item)
        tahunAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTahun.adapter = tahunAdapter

        binding.buttonLihatTransaksi.setOnClickListener {
            val selectedRadioButtonId = binding.radioGroupTransaksi.checkedRadioButtonId
            val selectedMonth = binding.spinnerBulan.selectedItem.toString()
            val selectedYear = binding.spinnerTahun.selectedItem.toString()

            if (selectedRadioButtonId == R.id.radioButtonBulan) {
                val monthNumber = convertMonthToNumber(selectedMonth)
                // Tampilkan data transaksi per bulan
                historyVieModel.getTotalPembayaranByMonth(monthNumber).observe(this, Observer { totalPembayaran->
                    // Lakukan apa pun dengan total pembayaran yang diperoleh
                    // Misalnya, tampilkan dalam TextView
                    if (totalPembayaran != null){
                        val total = FunctionHelper.rupiahFormat(totalPembayaran)
                        binding.textViewTotalTransaksi.text = "Total Pembayaran Bulan Ini: $total"
                    }else{
                        // Total pembayaran null, tampilkan pesan atau nilai default
                        binding.textViewTotalTransaksi.text = "Total Pembayaran Bulan Ini: N/A"
                    }


                })
                historyVieModel.getPemesananByMonth(monthNumber).observe(this, Observer { pemesananList->
                    if (pemesananList.isNotEmpty()){
                        adapter.setListHistory(pemesananList)
                        binding.tvEmpty.visibility = View.GONE
                    }else{
                        adapter.setListHistory(emptyList())
                        binding.tvEmpty.visibility = View.VISIBLE
                        showToast("Data transaksi bulan ini kosong")
                    }
                })
//                showToast("Transaksi per bulan: $monthNumber")
            } else if (selectedRadioButtonId == R.id.radioButtonTahun) {

                // Tampilkan data transaksi per tahun
                historyVieModel.getTotalPembayaranByYear(selectedYear).observe(this, Observer { totalPembayaran->
                    if (totalPembayaran != null){
                        val total = FunctionHelper.rupiahFormat(totalPembayaran)
                        binding.textViewTotalTransaksi.text = "Total Pembayaran Tahun Ini: $total"
                    }else{
                        // Total pembayaran null, tampilkan pesan atau nilai default
                        binding.textViewTotalTransaksi.text = "Total Pembayaran Tahun Ini: N/A"
                    }

                })
                historyVieModel.getPemesananByYear(selectedYear).observe(this, Observer { pemesananList->
                    if (pemesananList.isNotEmpty()){
                        adapter.setListHistory(pemesananList)
                        binding.tvEmpty.visibility = View.GONE
                    }else{
                        adapter.setListHistory(emptyList())
                        binding.tvEmpty.visibility = View.VISIBLE
                        showToast("Data transaksi tahun ini kosong")
                    }
                })
//                showToast("Transaksi per tahun: $selectedYear")
            }
        }

        adapter = HistoryAdapter()

        binding.recyclerViewTransaksi.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTransaksi.setHasFixedSize(true)
        binding.recyclerViewTransaksi.adapter = adapter

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

    private fun obtainViewModel(activity: AppCompatActivity): HistoryViewModel {

        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(HistoryViewModel::class.java)
    }

    fun convertMonthToNumber(month: String): String {
        val monthArray = arrayOf(
            "Januari", "Februari", "Maret", "April",
            "Mei", "Juni", "Juli", "Agustus",
            "September", "Oktober", "November", "Desember"
        )
        val monthIndex = monthArray.indexOf(month)
        return (monthIndex + 1).toString().padStart(2, '0') // Mengonversi indeks bulan menjadi angka bulan dengan format dua digit (01-12)
    }
}