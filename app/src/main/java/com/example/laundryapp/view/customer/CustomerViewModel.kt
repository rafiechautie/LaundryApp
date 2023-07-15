package com.example.laundryapp.view.customer

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.repository.CheckOutRepository

class CustomerViewModel(application: Application): ViewModel() {

    private val mCheckOutRepository: CheckOutRepository = CheckOutRepository(application)

    fun getCustomer(): LiveData<List<Pemesanan>> = mCheckOutRepository.getAllPemesanan()

}