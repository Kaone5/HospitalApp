package com.mycompany.hospitalapp2.util

object FirestoreTables{
    val APPOINTMENT = "appointment"
    val CALL = "call"
    val DOCTOR = "doctor"
    val SERVICE = "service"
    val USER = "user"
    val CALLSERVICE = "callservice"
}

object FireStoreCollection{
    val USER = "user"
}

object SharedPrefConstants {
    val LOCAL_SHARED_PREF = "local_shared_pref"
    val USER_SESSION = "user_session"
}

enum class HomeTabs(val index: Int, val key: String) {
    APPOINTMENTS(0, "Записи"),
    CALLS(1, "Вызовы"),
    ACCOUNT(2, "Личный кабинет")
}