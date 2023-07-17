package com.example.laundryapp.view.pemesanan

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.laundryapp.R
import com.example.laundryapp.databinding.ActivityLaundryBinding
import com.example.laundryapp.helper.FunctionHelper

class LaundryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaundryBinding

    private val hargaPakaian = 7000
    private val hargaSepatu = 5000
    private val hargaSprei = 55000
    private val hargaKarpet = 150000

    private var itemCount1 = 0
    private var itemCount2 = 0
    private var itemCount3 = 0
    private var itemCount4 = 0

    private var countPakaian = 0
    private var countSepatu = 0
    private var countSprei = 0
    private var countKarpet = 0
    private var totalItems = 0
    private var totalPrice = 0

    private lateinit var tvPakaian: TextView
    private lateinit var tvSepatu: TextView
    private lateinit var tvSprei: TextView
    private lateinit var tvKarpet: TextView

    private lateinit var tvPricePakaian: TextView
    private lateinit var tvPriceSepatu: TextView
    private lateinit var tvPriceSprei: TextView
    private lateinit var tvPriceKarpet: TextView

    private lateinit var tvJumlahBarang: TextView
    private lateinit var tvTotalPrice: TextView

    private lateinit var imageAdd1 : ImageView
    private lateinit var imageMinus1 : ImageView

    private lateinit var imageAdd2 : ImageView
    private lateinit var imageMinus2 : ImageView

    private lateinit var imageAdd3 : ImageView
    private lateinit var imageMinus3 : ImageView

    private lateinit var imageAdd4 : ImageView
    private lateinit var imageMinus4 : ImageView


    private lateinit var btnCheckout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaundryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBarTitle: String

        actionBarTitle = "Halaman Pemesanan"

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpView()
        setInitLayout()
        setDataPakaian()
        setDataSepatu()
        setDataSprei()
        setDataKarpet()
        setInputData()
    }

    private fun setInputData() {

        val nama = intent.getStringExtra(EXTRA_NAMA)
        val alamat = intent.getStringExtra(EXTRA_ALAMAT)
        val noHp = intent.getStringExtra(EXTRA_NO_HP)

//        if (nama != null) {
//            Log.d("test", nama)
//        }
//        if (alamat != null) {
//            Log.d("test", alamat)
//        }

        btnCheckout.setOnClickListener {
            if (totalItems == 0 || totalPrice == 0){
                Toast.makeText(this@LaundryActivity, "Harap pilih jenis barang!", Toast.LENGTH_SHORT).show()
            }else{
                val moveWithDataIntent = Intent(this@LaundryActivity, CheckOutActivity::class.java)

                if (nama != null && alamat != null && noHp != null) {
                    moveWithDataIntent.putExtra(CheckOutActivity.EXTRA_NAMA, nama)
                    moveWithDataIntent.putExtra(CheckOutActivity.EXTRA_ALAMAT, alamat)
                    moveWithDataIntent.putExtra(CheckOutActivity.EXTRA_NO_HP, noHp)
                }
                moveWithDataIntent.putExtra(CheckOutActivity.EXTRA_PAKAIAN, itemCount1)
                moveWithDataIntent.putExtra(CheckOutActivity.EXTRA_SEPATU, itemCount2)
                moveWithDataIntent.putExtra(CheckOutActivity.EXTRA_SPREI, itemCount3)
                moveWithDataIntent.putExtra(CheckOutActivity.EXTRA_KARPET, itemCount4)
                moveWithDataIntent.putExtra(CheckOutActivity.EXTRA_TOTAL_PRICE, totalPrice)
                startActivity(moveWithDataIntent)
                Toast.makeText(this@LaundryActivity, "Pesanan Anda sedang diproses, cek di menu riwayat", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
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

    private fun setTotalPrice(){
        totalItems = itemCount1 + itemCount2 + itemCount3  + itemCount4
        totalPrice = countPakaian + countSepatu + countSprei + countKarpet

        tvJumlahBarang.text = "$totalItems items"
        tvTotalPrice.text = FunctionHelper.rupiahFormat(totalPrice)

    }

    private fun setDataKarpet() {
        tvKarpet.setText(FunctionHelper.rupiahFormat(hargaKarpet))
        imageAdd4.setOnClickListener{
            itemCount4 += 1
            tvPriceKarpet.setText(itemCount4.toString())
            countKarpet = hargaKarpet * itemCount4
            setTotalPrice();
        }

        imageMinus4.setOnClickListener{
            if (itemCount4 > 0 ){
                itemCount4 = itemCount4 - 1
                tvPriceKarpet.setText(itemCount4.toString())
            }
            countKarpet = hargaKarpet * itemCount4
            setTotalPrice()
        }
    }

    private fun setDataSprei() {
        tvSprei.setText(FunctionHelper.rupiahFormat(hargaSprei))
        imageAdd3.setOnClickListener{
            itemCount3 += 1
            tvPriceSprei.setText(itemCount3.toString())
            countSprei = hargaSprei * itemCount3
            setTotalPrice();
        }

        imageMinus3.setOnClickListener{
            if (itemCount3 > 0 ){
                itemCount3 = itemCount3 - 1
                tvPriceSprei.setText(itemCount3.toString())
            }
            countSprei = hargaSprei * itemCount3
            setTotalPrice()
        }
    }



    private fun setDataSepatu() {
        tvSepatu.setText(FunctionHelper.rupiahFormat(hargaSepatu))
        imageAdd2.setOnClickListener{
            itemCount2 += 1
            tvPriceSepatu.setText(itemCount2.toString())
            countSepatu = hargaSepatu * itemCount2
            setTotalPrice();
        }

        imageMinus2.setOnClickListener{
            if (itemCount2 > 0 ){
                itemCount2 = itemCount2 - 1
                tvPriceSepatu.setText(itemCount2.toString())
            }
            countSepatu = hargaSepatu * itemCount2
            setTotalPrice()
        }
    }

    private fun setDataPakaian() {
        tvPakaian.setText(FunctionHelper.rupiahFormat(hargaPakaian))
        imageAdd1.setOnClickListener{
            itemCount1 += 1
            tvPricePakaian.setText(itemCount1.toString())
            countPakaian = hargaPakaian * itemCount1
            setTotalPrice();
        }

        imageMinus1.setOnClickListener{
            if (itemCount1 > 0 ){
                itemCount1 = itemCount1 - 1
                tvPricePakaian.setText(itemCount1.toString())
            }
            countPakaian = hargaPakaian * itemCount1
            setTotalPrice()
        }

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

    private fun setInitLayout(){

        tvPakaian = findViewById(R.id.tvPakaian)
        tvSepatu = findViewById(R.id.tvSepatu)
        tvSprei = findViewById(R.id.tvSprei)
        tvKarpet = findViewById(R.id.tvKarpet)


        tvJumlahBarang = findViewById(R.id.tvJumlahBarang)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)

        tvPricePakaian = findViewById(R.id.tvPricePakaian)
        tvPriceSepatu = findViewById(R.id.tvPriceSepatu)
        tvPriceKarpet = findViewById(R.id.tvPriceKarpet)
        tvPriceSprei = findViewById(R.id.tvPriceSprei)


        imageAdd1 = findViewById(R.id.imageAdd1)
        imageAdd2 = findViewById(R.id.imageAdd2)
        imageAdd3 = findViewById(R.id.imageAdd3)
        imageAdd4 = findViewById(R.id.imageAdd4)

        imageMinus1 = findViewById(R.id.imageMinus1)
        imageMinus2 = findViewById(R.id.imageMinus2)
        imageMinus3 = findViewById(R.id.imageMinus3)
        imageMinus4 = findViewById(R.id.imageMinus4)

        btnCheckout = findViewById(R.id.btnCheckout)

        binding.tvJumlahBarang.setText("0 items");
        binding.tvTotalPrice.setText("Rp 0");
        binding.tvInfo.setText("Cuci basah merupakan proses pencucian pakaian biasa menggunakan air dan deterjen.");
    }

    companion object{
        const val EXTRA_NAMA = "EXTRA_NAMA"
        const val EXTRA_ALAMAT = "EXTRA_ALAMAT"
        const val EXTRA_NO_HP = "EXTRA_NO_HP"
    }
}