package com.example.laundryapp.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.laundryapp.view.belumlunas.BelumLunasViewModel
import com.example.laundryapp.view.customer.CustomerViewModel
import com.example.laundryapp.view.history.HistoryViewModel
import com.example.laundryapp.view.lunas.LunasViewModel
import com.example.laundryapp.view.lunas.detail.DetailPemesananViewModel
import com.example.laundryapp.view.pemesanan.CheckOutViewModel

class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckOutViewModel::class.java)){
            return CheckOutViewModel(mApplication) as T
        }else if (modelClass.isAssignableFrom(LunasViewModel::class.java)){
            return LunasViewModel(mApplication) as T
        }else if (modelClass.isAssignableFrom(BelumLunasViewModel::class.java)){
            return BelumLunasViewModel(mApplication) as T
        }else if (modelClass.isAssignableFrom(DetailPemesananViewModel::class.java)){
            return DetailPemesananViewModel(mApplication) as T
        }else if(modelClass.isAssignableFrom(HistoryViewModel::class.java)){
            return HistoryViewModel(mApplication) as T
        }else if(modelClass.isAssignableFrom(CustomerViewModel::class.java)){
            return CustomerViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}