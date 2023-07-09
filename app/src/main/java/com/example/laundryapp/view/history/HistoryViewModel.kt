package com.example.laundryapp.view.history

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.repository.CheckOutRepository

class HistoryViewModel(application: Application) : ViewModel() {


    private val mCheckOutRepository: CheckOutRepository = CheckOutRepository(application)

    fun getPemesananByMonth(month: String): LiveData<List<Pemesanan>> {
        return mCheckOutRepository.getPemesananByMonth(month)
    }

    fun getPemesananByYear(year: String): LiveData<List<Pemesanan>> {
        return mCheckOutRepository.getPemesananByYear(year)
    }

    fun getTotalPembayaranByMonth(month: String): LiveData<Int> {
        return mCheckOutRepository.getTotalPembayaranByMonth(month)
    }

    fun getTotalPembayaranByYear(year: String): LiveData<Int> {
        return mCheckOutRepository.getTotalPembayaranByYear(year)
    }

}