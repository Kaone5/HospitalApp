package com.mycompany.hospitalapp2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CallService(
    var id: String = "",
    var name: String = ""
): Parcelable
