package com.example.fitnessappc.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfile(
    val height: Int = 0,
    val weight: Int = 0,
    val email: String = ""          // optional, if you want to store it too
) : Parcelable