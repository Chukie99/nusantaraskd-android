package com.nusantaraskd.data.repository

sealed class LicenseResult {
    object Valid : LicenseResult()
    object Invalid : LicenseResult()
    object UsedOnOtherDevice : LicenseResult()
    object NoInternet : LicenseResult()
    data class Error(val message: String) : LicenseResult()
}