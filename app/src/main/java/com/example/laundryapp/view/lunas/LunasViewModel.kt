package com.example.laundryapp.view.lunas

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.repository.CheckOutRepository

class LunasViewModel(application: Application) : ViewModel() {

    private val mCheckOutRepository: CheckOutRepository = CheckOutRepository(application)

    fun getAllPemesananLunas(): LiveData<List<Pemesanan>> = mCheckOutRepository.getAllPemesananByStatusLunas()


}