package com.example.laundryapp.view.lunas.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.laundryapp.R
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.databinding.ActivityDetailLunasBinding
import com.example.laundryapp.helper.DateHelper
import com.example.laundryapp.helper.ViewModelFactory

class DetailLunasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailLunasBinding

    private var pemesanan: Pemesanan? = null

    private lateinit var detailLunasViewModel: DetailPemesananViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLunasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailLunasViewModel = obtainViewModel(this@DetailLunasActivity)

        pemesanan = intent.getParcelableExtra(EXTRA_PEMESANAN)

        val actionBarTitle: String

        actionBarTitle = "Ubah"
        pemesanan?.let { pemesanan ->
            binding.etNamaPelanggan.setText(pemesanan.nama_pelanggan)
            binding.etAlamat.setText(pemesanan.alamat_pelanggan)
            binding.etNoHp.setText(pemesanan.no_hp)
            binding.etPakaian.setText(pemesanan.jumlah_pakaian.toString())
            binding.etSepatu.setText(pemesanan.jumlah_sepatu.toString())
            binding.etSprei.setText(pemesanan.jumlah_bed_cover.toString())
            binding.etKarpet.setText(pemesanan.jumlah_karpet.toString())
            binding.etTotal.setText(pemesanan.total_pembayaran.toString())
            binding.etTanggalAmbil.setText(pemesanan.tanggal_pengambilan.toString())
            if (pemesanan.status_pembayaran == "Lunas") {
                binding.SpinnerStatusPembayaran.setSelection(0)
            } else if (pemesanan.status_pembayaran == "Belum Lunas") {
                binding.SpinnerStatusPembayaran.setSelection(1)
            }

        }


        val userTypes = resources.getStringArray(R.array.statusPembayaran_array)
        val adapter = ArrayAdapter(this, R.layout.spinner_item, userTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.SpinnerStatusPembayaran.adapter = adapter

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.SpinnerStatusPembayaran.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                val selectedItem = userTypes[position]
                pemesanan?.status_pembayaran = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.btnUpdate.setOnClickListener {
            pemesanan.let {

                pemesanan?.date = DateHelper.getCurrentDate()
            }
            detailLunasViewModel.update(pemesanan as Pemesanan)
            showToast(getString(R.string.changed))
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailPemesananViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailPemesananViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_detail_pemesanan, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    detailLunasViewModel.delete(pemesanan as Pemesanan)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    companion object {
        const val EXTRA_PEMESANAN = "extra_pemesanan"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
}