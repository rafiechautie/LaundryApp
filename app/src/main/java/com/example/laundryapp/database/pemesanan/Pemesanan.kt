package com.example.laundryapp.database.pemesanan

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Date

@Entity
@Parcelize
data class Pemesanan (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_pemesanan")
    var id_pemesanan: Int = 0,

    @ColumnInfo(name = "name_pelanggan")
    var nama_pelanggan: String? = null,

    @ColumnInfo(name = "alamat_pelanggan")
    var alamat_pelanggan: String? = null,

    @ColumnInfo(name = "no_hp")
    var no_hp: String? = null,

    @ColumnInfo(name = "jumlah_pakaian")
    var jumlah_pakaian: Int,

    @ColumnInfo(name = "jumlah_sepatu")
    var jumlah_sepatu: Int,

    @ColumnInfo(name = "jumlah_bed_cover")
    var jumlah_bed_cover: Int,

    @ColumnInfo(name = "jumlah_karpet")
    var jumlah_karpet: Int,

    @ColumnInfo(name = "total_pembayaran")
    var total_pembayaran: Int,

    @ColumnInfo(name = "status_pembayaran")
    var status_pembayaran: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null,

    @ColumnInfo(name = "tanggal_pengambilan")
    var tanggal_pengambilan: String? = null,


    ): Parcelable