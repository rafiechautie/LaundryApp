package com.example.laundryapp.view.customer.detail


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.laundryapp.R
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.databinding.ActivityDetailCustomerBinding
import com.example.laundryapp.helper.ViewModelFactory
import com.example.laundryapp.view.lunas.detail.DetailLunasActivity
import com.example.laundryapp.view.lunas.detail.DetailPemesananViewModel
import com.example.laundryapp.view.pemesanan.CheckOutActivity
import com.example.laundryapp.view.pemesanan.LaundryActivity

class DetailCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCustomerBinding

    private var customer: Pemesanan? = null

    private var nama = ""
    private var alamat = ""
    private var noHp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customer= intent.getParcelableExtra(EXTRA_CUSTOMER)

        customer?.let { pemesanan ->
            binding.etNamaPelanggan.setText(pemesanan.nama_pelanggan)
            binding.etAlamat.setText(pemesanan.alamat_pelanggan)
            binding.etNoHp.setText(pemesanan.no_hp)
            nama = pemesanan.nama_pelanggan.toString()
            alamat = pemesanan.alamat_pelanggan.toString()
            noHp = pemesanan.no_hp.toString()
        }

//        Log.d("test", nama)
//        Log.d("test", alamat)

        binding.btnOrder.setOnClickListener {
            val moveWithDataIntent = Intent(this@DetailCustomerActivity, LaundryActivity::class.java)
            moveWithDataIntent.putExtra(LaundryActivity.EXTRA_NAMA, nama)
            moveWithDataIntent.putExtra(LaundryActivity.EXTRA_ALAMAT, alamat)
            moveWithDataIntent.putExtra(LaundryActivity.EXTRA_NO_HP, noHp)
            startActivity(moveWithDataIntent)
            Toast.makeText(this@DetailCustomerActivity, "Pesanan Anda sedang diproses", Toast.LENGTH_SHORT).show()
            finish()
        }

//        detailCustomerViewModel = obtainViewModel(this@DetailCustomerActivity)

    }

//    private fun obtainViewModel(activity: AppCompatActivity): DetailPemesananViewModel {
//        val factory = ViewModelFactory.getInstance(activity.application)
//        return ViewModelProvider(activity, factory).get(DetailPemesananViewModel::class.java)
//    }


//    override fun onBackPressed() {
//        showAlertDialog(ALERT_DIALOG_CLOSE)
//    }

    companion object {
        const val EXTRA_CUSTOMER = "extra_customer"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}