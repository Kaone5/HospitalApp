package com.mycompany.hospitalapp2.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*
import com.google.firebase.firestore.ServerTimestamp
@Parcelize
data class Appointment(
    var id: String = "",
    var patient: String = "",
    var service: String ="",
    var cabinet: String ="",
    var doctor: String = "",
    var date: String = "",
    @ServerTimestamp
    val created: Date = Date()
): Parcelable