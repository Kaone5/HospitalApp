package com.mycompany.hospitalapp2.data.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Call(
    var id: String = "",
    var patient: String = "",
    var service: String ="",
    var address: String = "",
    var date: String = "",
    var phone: String = "",
    var info: String ="",
    @ServerTimestamp
    val created: Date = Date()
): Parcelable
