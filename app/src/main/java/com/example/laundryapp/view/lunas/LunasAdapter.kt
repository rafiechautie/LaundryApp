package com.example.laundryapp.view.lunas

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.databinding.ItemLunasBinding
import com.example.laundryapp.helper.FunctionHelper
import com.example.laundryapp.helper.PemesananDiffCallback
import com.example.laundryapp.view.lunas.detail.DetailLunasActivity

class LunasAdapter: RecyclerView.Adapter<LunasAdapter.PemesananViewHolder>() {

    private val listPemesanan = ArrayList<Pemesanan>()

    fun setListPemesananLunas(listPemesanan: List<Pemesanan>) {
        val diffCallback = PemesananDiffCallback(this.listPemesanan, listPemesanan)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listPemesanan.clear()
        this.listPemesanan.addAll(listPemesanan)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PemesananViewHolder {
        val binding = ItemLunasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PemesananViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PemesananViewHolder, position: Int) {
        holder.bind(listPemesanan[position])
    }
    override fun getItemCount(): Int {
        return listPemesanan.size
    }

    inner class PemesananViewHolder(private val binding: ItemLunasBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pemesanan: Pemesanan) {
            with(binding) {
                tvNamaPelanggan.text = pemesanan.nama_pelanggan
                tvAlamatPelangganPesanan.text = pemesanan.alamat_pelanggan
                tvTotalPricePesanan.text = FunctionHelper.rupiahFormat(pemesanan.total_pembayaran)
                tvStatusPembayaran.text = pemesanan.status_pembayaran
                tvDatePesanan.text = pemesanan.tanggal_pengambilan
                cvItemLunas.setOnClickListener {
                    val intent = Intent(it.context, DetailLunasActivity::class.java)
                    intent.putExtra(DetailLunasActivity.EXTRA_PEMESANAN, pemesanan)
                    it.context.startActivity(intent)
                }

            }
        }
    }

}