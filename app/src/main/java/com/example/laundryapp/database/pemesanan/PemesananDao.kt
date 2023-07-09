package com.example.laundryapp.database.pemesanan

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PemesananDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pemesanan: Pemesanan)

    @Update
    fun update(pemesanan: Pemesanan)

    @Delete
    fun delete(pemesanan: Pemesanan)

    @Query("SELECT * from pemesanan ORDER BY id_pemesanan ASC")
    fun getAllPemesanan(): LiveData<List<Pemesanan>>

    @Query("SELECT * from pemesanan WHERE status_pembayaran = 'Lunas'")
    fun getAllPemesananByStatusLunas(): LiveData<List<Pemesanan>>

    @Query("SELECT * from pemesanan WHERE status_pembayaran = 'Belum Lunas'")
    fun getAllPemesananByStatusBelumLunas(): LiveData<List<Pemesanan>>

    @Query("SELECT * FROM pemesanan WHERE SUBSTR(tanggal_pengambilan, 4, 2) = :month")
//    @Query("SELECT * FROM pemesanan WHERE strftime('%m', tanggal_pengambilan) = :month")
    fun getPemesananByMonth(month: String): LiveData<List<Pemesanan>>

    @Query("SELECT * FROM pemesanan WHERE SUBSTR(tanggal_pengambilan, 7) = :year")
    fun getPemesananByYear(year: String): LiveData<List<Pemesanan>>


    @Query("SELECT SUM(total_pembayaran) FROM pemesanan WHERE SUBSTR(tanggal_pengambilan, 4, 2) = :month")
//    @Query("SELECT SUM(total_pembayaran) FROM pemesanan WHERE strftime('%m', tanggal_pengambilan) = :month")
    fun getTotalPembayaranByMonth(month: String): LiveData<Int>

    @Query("SELECT SUM(total_pembayaran) FROM pemesanan WHERE SUBSTR(tanggal_pengambilan, 7) = :year")
    fun getTotalPembayaranByYear(year: String): LiveData<Int>

}