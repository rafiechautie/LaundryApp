package com.example.laundryapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.laundryapp.database.pemesanan.Pemesanan
import com.example.laundryapp.database.pemesanan.PemesananDao

@Database(entities = [Pemesanan::class], version = 1)
abstract class LaundryRoomDatabase: RoomDatabase() {

    abstract fun pemesananDao(): PemesananDao

    companion object {
        @Volatile
        private var INSTANCE: LaundryRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): LaundryRoomDatabase {
            if (INSTANCE == null) {
                synchronized(LaundryRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        LaundryRoomDatabase::class.java, "laundry_app")
                        .build()
                }
            }
            return INSTANCE as LaundryRoomDatabase
        }
    }

}