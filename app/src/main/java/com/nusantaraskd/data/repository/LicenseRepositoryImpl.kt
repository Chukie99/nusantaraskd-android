package com.nusantaraskd.data.repository

import com.supabase.gotrue.auth.AuthResult
import com.supabase.postgrest.Postgrest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LicenseRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : LicenseRepository {

    override suspend fun checkLicense(key: String, deviceId: String): LicenseResult {
        // TODO: Implement actual Supabase logic when table is ready
        // For now, simulate as before
        return LicenseResult.Valid
    }
}