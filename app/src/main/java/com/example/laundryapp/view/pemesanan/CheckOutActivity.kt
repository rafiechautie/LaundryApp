package com.example.laundryapp.view.pemesanan

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.laundryapp.R
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.databinding.ActivityCheckOutBinding
import com.example.laundryapp.helper.DateHelper
import com.example.laundryapp.helper.FunctionHelper
import com.example.laundryapp.helper.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CheckOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckOutBinding
    private var pemesanan: Pemesanan? = null
    private var isEdit = false
    private var statusPembayaran: String? = null

    private lateinit var checkOutViewModel: CheckOutViewModel

    private var selectedDate: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpView()

        checkOutViewModel = obtainViewModel(this@CheckOutActivity)


        val userTypes = resources.getStringArray(R.array.statusPembayaran_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, userTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.SpinnerStatusPembayaran.adapter = adapter

        binding.SpinnerStatusPembayaran.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                statusPembayaran = parent?.getItemAtPosition(position).toString()
//                pemesanan?.status_pembayaran = selectedItem

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        }

        val pakaian = intent.getIntExtra(EXTRA_PAKAIAN, 0)
        val sepatu = intent.getIntExtra(EXTRA_SEPATU, 0)
        val sprei = intent.getIntExtra(EXTRA_SPREI, 0)
        val karpet = intent.getIntExtra(EXTRA_KARPET, 0)
        val totalPrice = intent.getIntExtra(EXTRA_TOTAL_PRICE, 0)

        binding.etPakaian.setText(pakaian.toString())
        binding.etSepatu.setText(sepatu.toString())
        binding.etSprei.setText(sprei.toString())
        binding.etKarpet.setText(karpet.toString())
        binding.etTotal.setText(FunctionHelper.rupiahFormat(totalPrice))


//        binding.btnBatal.setOnClickListener {
//            startActivity(Intent(this@CheckOutActivity, LaundryActivity::class.java))
//        }

        binding.etTanggalAmbil.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnCheckout.setOnClickListener {
            val name = binding.etNamaPelanggan.text.toString().trim()
            val alamatPelanggan = binding.etAlamat.text.toString().trim()
            val tanggalPengambilan = binding.etTanggalAmbil.text.toString().trim()
            when{
                name.isEmpty() -> {
                    binding.etNamaPelanggan.error = getString(R.string.empty)
                }
                alamatPelanggan.isEmpty() -> {
                    binding.etAlamat.error = getString(R.string.empty)
                }
                else -> {
                    pemesanan.let { pemesanan ->
                        pemesanan?.nama_pelanggan = name
                        pemesanan?.alamat_pelanggan = alamatPelanggan
                        pemesanan?.jumlah_pakaian = pakaian
                        pemesanan?.jumlah_sepatu = sepatu
                        pemesanan?.jumlah_bed_cover = sprei
                        pemesanan?.jumlah_karpet = karpet
                        pemesanan?.total_pembayaran = totalPrice

                    }
                    if (isEdit){

                    }else{
                        pemesanan.let { pemesanan ->
                            pemesanan?.date = DateHelper.getCurrentDate()
                        }

                        checkOutViewModel.insert(pemesanan ?: Pemesanan(
                            0,
                            name,
                            alamatPelanggan,
                            pakaian,
                            sepatu,
                            sprei,
                            karpet,
                            totalPrice,
                            statusPembayaran,
                            DateHelper.getCurrentDate(),
                            tanggalPengambilan
                        ))


                        showToast("Pesanan Telah berhasil ditambahkan")
                    }
                    val moveWithDataIntent = Intent(this@CheckOutActivity, CheckOutSuccessActivity::class.java)
                    moveWithDataIntent.putExtra(CheckOutSuccessActivity.EXTRA_NAMA, name)
                    moveWithDataIntent.putExtra(CheckOutSuccessActivity.EXTRA_PAKAIAN, pakaian)
                    moveWithDataIntent.putExtra(CheckOutSuccessActivity.EXTRA_SEPATU, sepatu)
                    moveWithDataIntent.putExtra(CheckOutSuccessActivity.EXTRA_SPREI, sprei)
                    moveWithDataIntent.putExtra(CheckOutSuccessActivity.EXTRA_KARPET, karpet)
                    moveWithDataIntent.putExtra(CheckOutSuccessActivity.EXTRA_TOTAL_PRICE, totalPrice)
                    moveWithDataIntent.putExtra(CheckOutSuccessActivity.EXTRA_DATE, DateHelper.getCurrentDate())
                    moveWithDataIntent.putExtra(CheckOutSuccessActivity.EXTRA_STATUS, statusPembayaran)
                    moveWithDataIntent.putExtra(CheckOutSuccessActivity.EXTRA_ALAMAT, alamatPelanggan)
                    moveWithDataIntent.putExtra(CheckOutSuccessActivity.EXTRA_TANGGAL_PENGAMBILAN, tanggalPengambilan)
                    startActivity(moveWithDataIntent)
                    finish()
                }
            }
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.time)
                onDateSelected(formattedDate)
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun onDateSelected(date: String) {
        // Lakukan operasi dengan tanggal yang dipilih, misalnya:
        binding.etTanggalAmbil.setText(date)
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): CheckOutViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(CheckOutViewModel::class.java)
    }

    companion object{
        private val TAG = "LAUNDRY ACTIVITY"
        const val EXTRA_PAKAIAN = "extra_pakaian"
        const val EXTRA_SEPATU = "extra_sepatu"
        const val EXTRA_SPREI = "extra_sprei"
        const val EXTRA_KARPET = "extra_karpet"
        const val EXTRA_TOTAL_PRICE = "extra_total_price"
    }
}