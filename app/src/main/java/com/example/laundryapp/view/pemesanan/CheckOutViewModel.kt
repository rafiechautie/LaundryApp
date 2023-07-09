package com.example.laundryapp.view.pemesanan

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.repository.CheckOutRepository

class CheckOutViewModel(application: Application): ViewModel() {

    private val mPemesananRepository: CheckOutRepository = CheckOutRepository(application)

    fun insert(pemesanan: Pemesanan) {
        mPemesananRepository.insertPemesanan(pemesanan)
    }

}