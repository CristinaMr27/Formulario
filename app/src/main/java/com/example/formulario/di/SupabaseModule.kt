package com.example.formulario.di

import android.content.Context
import com.example.formulario.BuildConfig
import com.example.formulario.data.repository.RequestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.client.engine.okhttp.OkHttp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        // Get configuration from BuildConfig (loaded from local.properties)
        val supabaseUrl = BuildConfig.SUPABASE_URL
        val supabaseKey = BuildConfig.SUPABASE_KEY
        
        return createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            httpEngine = OkHttp.create()

            install(Postgrest)
        }
    }

    @Provides
    @Singleton
    fun provideRequestRepository(
        supabaseClient: SupabaseClient,
        @ApplicationContext context: Context
    ): RequestRepository {
        return RequestRepository(supabaseClient, context)
    }
}
