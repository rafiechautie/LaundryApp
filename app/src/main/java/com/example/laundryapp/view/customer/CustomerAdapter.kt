package com.example.laundryapp.view.customer

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.databinding.ItemCustomerBinding
import com.example.laundryapp.databinding.ItemLunasBinding
import com.example.laundryapp.helper.FunctionHelper
import com.example.laundryapp.helper.PemesananDiffCallback
import com.example.laundryapp.view.lunas.LunasAdapter
import com.example.laundryapp.view.lunas.detail.DetailLunasActivity

class CustomerAdapter: RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    private val listCustomer = ArrayList<Pemesanan>()

    fun setListCustomer(listCustomer: List<Pemesanan>) {
        val diffCallback = PemesananDiffCallback(this.listCustomer, listCustomer)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listCustomer.clear()
        this.listCustomer.addAll(listCustomer)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(binding)
    }
    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.bind(listCustomer[position])
    }
    override fun getItemCount(): Int {
        return listCustomer.size
    }

    inner class CustomerViewHolder(private val binding: ItemCustomerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pemesanan: Pemesanan) {
            with(binding) {
                tvNamaPelanggan.text = pemesanan.nama_pelanggan
                tvAlamatPelangganPesanan.text = pemesanan.alamat_pelanggan
                cvItemCustomer.setOnClickListener {
                    val intent = Intent(it.context, DetailLunasActivity::class.java)
                    intent.putExtra(DetailLunasActivity.EXTRA_PEMESANAN, pemesanan)
                    it.context.startActivity(intent)
                }

            }
        }
    }

}