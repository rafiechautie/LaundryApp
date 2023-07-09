package com.example.laundryapp.view.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.databinding.ItemHistoryBinding
import com.example.laundryapp.databinding.ItemLunasBinding
import com.example.laundryapp.helper.FunctionHelper
import com.example.laundryapp.helper.PemesananDiffCallback
import com.example.laundryapp.view.belumlunas.detail.DetailBelumLunasActivity
import com.example.laundryapp.view.history.detail.DetailHistoryActivity
import com.example.laundryapp.view.lunas.LunasAdapter
import com.example.laundryapp.view.lunas.detail.DetailLunasActivity

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private val listHistory = ArrayList<Pemesanan>()

    fun setListHistory(listHistory: List<Pemesanan>) {
        val diffCallback = PemesananDiffCallback(this.listHistory, listHistory)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listHistory.clear()
        this.listHistory.addAll(listHistory)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }
    override fun getItemCount(): Int {
        return listHistory.size
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pemesanan: Pemesanan) {
            with(binding) {
                textViewTanggal.text = pemesanan.tanggal_pengambilan
                textViewNamaPelanggan.text= pemesanan.nama_pelanggan
                textViewTotalPembayaran.text = FunctionHelper.rupiahFormat(pemesanan.total_pembayaran)
                tvStatusPembayaran.text = pemesanan.status_pembayaran
                cvItemHistory.setOnClickListener {
                    val intent = Intent(it.context, DetailHistoryActivity::class.java)
                    intent.putExtra(DetailHistoryActivity.EXTRA_HISTORY_PEMESANAN, pemesanan)
                    it.context.startActivity(intent)
                }


            }
        }
    }

}