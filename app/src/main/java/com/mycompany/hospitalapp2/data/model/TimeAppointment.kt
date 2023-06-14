package com.mycompany.hospitalapp2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimeAppointment(
    var id: String = "",
    var one: String = ""
): Parcelable
