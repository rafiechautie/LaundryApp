package com.example.laundryapp.view.lunas.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.repository.CheckOutRepository

class DetailPemesananViewModel(application: Application): ViewModel() {

    private val mCheckOutRepository: CheckOutRepository = CheckOutRepository(application)

    fun update(pemesanan: Pemesanan) {
        mCheckOutRepository.updatePemesanan(pemesanan)
    }

    fun delete(pemesanan: Pemesanan){
        mCheckOutRepository.deletePemesanan(pemesanan)
    }

}