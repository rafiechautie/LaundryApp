package com.example.laundryapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.laundryapp.database.LaundryRoomDatabase
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.database.pemesanan.PemesananDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CheckOutRepository(application: Application) {

    private val mPemesananDao: PemesananDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()


    init {
        val db = LaundryRoomDatabase.getDatabase(application)
        mPemesananDao = db.pemesananDao()
    }

    fun getAllPemesanan(): LiveData<List<Pemesanan>> = mPemesananDao.getAllPemesanan()

    fun getAllPemesananByStatusLunas(): LiveData<List<Pemesanan>> {
        return mPemesananDao.getAllPemesananByStatusLunas()
    }

    fun getAllPemesananByStatusBelumLunas(): LiveData<List<Pemesanan>> {
        return mPemesananDao.getAllPemesananByStatusBelumLunas()
    }

    fun getPemesananByMonth(month: String): LiveData<List<Pemesanan>> {
        return mPemesananDao.getPemesananByMonth(month)
    }

    fun getPemesananByYear(year: String): LiveData<List<Pemesanan>> {
        return mPemesananDao.getPemesananByYear(year)
    }

    fun getTotalPembayaranByMonth(month: String): LiveData<Int> {
        return mPemesananDao.getTotalPembayaranByMonth(month)
    }

    fun getTotalPembayaranByYear(year: String): LiveData<Int> {
        return mPemesananDao.getTotalPembayaranByYear(year)
    }


    fun insertPemesanan(pemesanan: Pemesanan){
        executorService.execute{ mPemesananDao.insert(pemesanan) }
    }

    fun deletePemesanan(pemesanan: Pemesanan){
        executorService.execute{ mPemesananDao.delete(pemesanan) }
    }

    fun updatePemesanan(pemesanan: Pemesanan){
        executorService.execute{mPemesananDao.update(pemesanan)}
    }

}