package com.mycompany.hospitalapp2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Service(
    var id: String = "",
    var name: String = ""
): Parcelable