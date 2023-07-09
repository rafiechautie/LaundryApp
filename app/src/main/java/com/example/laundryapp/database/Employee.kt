package com.example.laundryapp.database

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties

data class Employee(
    val nama: String ?="",
    val username: String ?="",
    val password: String ?="",
    var userType: String ?=""
){
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}
