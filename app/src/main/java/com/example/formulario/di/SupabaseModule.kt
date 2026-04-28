package com.example.formulario.di

import com.example.formulario.data.repository.SolicitudRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
        return createSupabaseClient(
            supabaseUrl = "https://jgndwpyaptgrwlbtkwhe.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImpnbmR3cHlhcHRncndsYnRrd2hlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzcyNDY0MTEsImV4cCI6MjA5MjgyMjQxMX0.XdxDi8fECXQehXjBkHUmxaL7iEE280UCFRV61aetamA"
        ) {
            httpEngine = OkHttp.create()

            install(Postgrest)
        }
    }

    @Provides
    @Singleton
    fun provideSolicitudRepository(
        supabaseClient: SupabaseClient
    ): SolicitudRepository {
        return SolicitudRepository(supabaseClient)
    }
}


