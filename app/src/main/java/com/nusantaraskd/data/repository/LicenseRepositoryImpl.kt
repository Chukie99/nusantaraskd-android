package com.nusantaraskd.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LicenseRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : LicenseRepository {

    override suspend fun checkLicense(key: String, deviceId: String): LicenseResult {
        return try {
            val docRef = firestore.collection("licenses").document(key)
            val snapshot = docRef.get().await()

            if (!snapshot.exists()) {
                return LicenseResult.Invalid
            }

            val status = snapshot.getString("status") ?: "unused"
            val savedDeviceId = snapshot.getString("device_id")

            when {
                status == "unused" -> {
                    // Claim license for this device
                    docRef.update(
                        mapOf(
                            "status" to "used",
                            "device_id" to deviceId
                        )
                    ).await()
                    LicenseResult.Valid
                }
                status == "used" && savedDeviceId == deviceId -> {
                    LicenseResult.Valid
                }
                status == "used" && savedDeviceId != deviceId -> {
                    LicenseResult.UsedOnOtherDevice
                }
                else -> LicenseResult.Invalid
            }
        } catch (e: Exception) {
            val message = e.message ?: ""
            if (message.contains("unavailable", ignoreCase = true) || 
                message.contains("network", ignoreCase = true) ||
                message.contains("connection", ignoreCase = true)) {
                LicenseResult.NoInternet
            } else {
                LicenseResult.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
