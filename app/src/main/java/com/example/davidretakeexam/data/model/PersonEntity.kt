package com.example.davidretakeexam.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val birthday: String,
    val age: Int,
    val emailAddress: String,
    val mobileNumber: String,
    val address: String,
    val profileImg: String
)
