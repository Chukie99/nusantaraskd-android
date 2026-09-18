// Remove Firebase imports (LicenseRepositoryImpl.kt)
// Supabase logic implemented here
package com.nusantaraskd.data.repository

import com.nusantaraskd.BuildConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
data class LicenseRow(
    val license_key: String,
    val status: String,
    val device_id: String? = null
)

@Singleton
class LicenseRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : LicenseRepository {

    override suspend fun checkLicense(key: String, deviceId: String): LicenseResult {
        return try {
            val response = supabaseClient.postgrest["licenses"]
                .select {
                    filter { eq("license_key", key) }
                }.decodeSingle<LicenseRow>()

            when {
                response.status == "unused" -> {
                    supabaseClient.postgrest["licenses"]
                        .update({ set("status", "used"); set("device_id", deviceId) }) {
                            filter { eq("license_key", key) }
                        }
                    LicenseResult.Valid
                }
                response.status == "used" && response.device_id == deviceId -> LicenseResult.Valid
                response.status == "used" && response.device_id != deviceId -> LicenseResult.UsedOnOtherDevice
                else -> LicenseResult.Invalid
            }
        } catch (e: Exception) {
            LicenseResult.Error(e.message ?: "Unknown error")
        }
    }
}