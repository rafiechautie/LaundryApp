package com.example.laundryapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.laundryapp.database.pemesanan.Pemesanan

class PemesananDiffCallback(private val oldNoteList: List<Pemesanan>, private val newNoteList: List<Pemesanan>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldNoteList.size
    override fun getNewListSize(): Int = newNoteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].id_pemesanan == newNoteList[newItemPosition].id_pemesanan
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.nama_pelanggan == newNote.nama_pelanggan && oldNote.alamat_pelanggan == oldNote.alamat_pelanggan && oldNote.total_pembayaran == oldNote.total_pembayaran && oldNote.status_pembayaran == oldNote.status_pembayaran
    }

}