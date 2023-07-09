package com.example.laundryapp.view.belumlunas

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.repository.CheckOutRepository
import kotlinx.coroutines.launch

class BelumLunasViewModel(application: Application) : ViewModel() {

    private val mCheckOutRepository: CheckOutRepository = CheckOutRepository(application)

    fun getAllPemesananBelumLunas(): LiveData<List<Pemesanan>> = mCheckOutRepository.getAllPemesananByStatusBelumLunas()


}

