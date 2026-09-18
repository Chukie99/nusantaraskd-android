package com.nusantaraskd.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.nusantaraskd.BuildConfig.SUPABASE_URL
import com.nusantaraskd.BuildConfig.SUPABASE_ANON_KEY
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.okhttp.OkHttpClientBuilderPlugin
import io.github.jan.supabase.postgrest.postgrest
import com.supabase.okhttp.PostgRestOkHttpClient
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(OkHttpClientBuilderPlugin($SUPABASE_ANON_KEY))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return kotlinx.coroutines.runBlocking {
            com.supabase.gotrue.Config(
                $SUPABASE_URL,
                required = false
            ).let { config ->
                kotlinx.coroutines.runBlocking {
                    com.supabase.gotrue.createClient(
                        $SUPABASE_URL,
                        $SUPABASE_ANON_KEY,
                        config = config
                    )
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideGoTrue(supabaseClient: SupabaseClient): GoTrue auth {
        return supabaseClient.auth
    }

    @Provides
    @Singleton
    fun providePostgRest(supabaseClient: SupabaseClient): Postgrest postgrest {
        return supabaseClient.postgrest
    }
}