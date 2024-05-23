package com.example.davidretakeexam.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Person(
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
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(gender)
        parcel.writeString(birthday)
        parcel.writeInt(age)
        parcel.writeString(emailAddress)
        parcel.writeString(mobileNumber)
        parcel.writeString(address)
        parcel.writeString(profileImg)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}
