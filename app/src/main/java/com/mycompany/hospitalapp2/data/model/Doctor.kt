package com.mycompany.hospitalapp2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Doctor(
    var id: String = "",
    var name: String = "",
    var role: String ="",
    var cabinet: String = "",
    var time_false: String = "",
    var time_true: String = ""
): Parcelable
