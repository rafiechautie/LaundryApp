package com.example.laundryapp.view.history.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.laundryapp.R
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.databinding.ActivityDetailHistoryBinding
import com.example.laundryapp.databinding.ActivityHistoryBinding
import com.example.laundryapp.helper.ViewModelFactory
import com.example.laundryapp.view.belumlunas.detail.DetailBelumLunasActivity
import com.example.laundryapp.view.lunas.detail.DetailPemesananViewModel

class DetailHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHistoryBinding

    private var pemesanan: Pemesanan? = null

    private lateinit var detailHistoryViewModel: DetailPemesananViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        detailHistoryViewModel = obtainViewModel(this@DetailHistoryActivity)

        pemesanan = intent.getParcelableExtra(DetailHistoryActivity.EXTRA_HISTORY_PEMESANAN)

        val actionBarTitle: String

        actionBarTitle = "Halaman Detail History"
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
            binding.etStatuPembayaran.setText(pemesanan.status_pembayaran)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_detail_pemesanan, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(DetailBelumLunasActivity.ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(DetailBelumLunasActivity.ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == DetailBelumLunasActivity.ALERT_DIALOG_CLOSE
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
                    detailHistoryViewModel.delete(pemesanan as Pemesanan)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        showAlertDialog(DetailBelumLunasActivity.ALERT_DIALOG_CLOSE)
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailPemesananViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailPemesananViewModel::class.java)
    }

    companion object {
        const val EXTRA_HISTORY_PEMESANAN = "extra_history_pemesanan"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
        private const val PERMISSION_REQUEST_CODE = 123
    }
}