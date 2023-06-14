package com.mycompany.hospitalapp2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    var id: String = "",
    var email: String = "",
    var role: String = "",
    var name: String = "",
    var phone: String = "",
    var birth_date: String = "",
    var passport: String = "",
    var policy: String = ""
): Parcelable