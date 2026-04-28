package com.example.formulario.data.repository

import com.example.formulario.data.model.Solicitud
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

class SolicitudRepository(private val supabase: SupabaseClient) {

    suspend fun insertarSolicitud(solicitud: Solicitud): Result<Solicitud> {
        return try {
            val response = supabase
                .from("solicitudes")
                .insert(solicitud){
                    select()
                }
                .decodeAs<List<Solicitud>>()

            if (response.isNotEmpty()) {
                Result.success(response.first())
            } else {
                Result.failure(Exception("No se recibió respuesta del servidor"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obtenerSolicitudes(email: String): Result<List<Solicitud>> {
        return try {
            val solicitudes = supabase
                .from("solicitudes")
                .select {
                    filter {
                        eq("email", email)
                    }
                    order("created_at", Order.DESCENDING)
                }
                .decodeAs<List<Solicitud>>()

            Result.success(solicitudes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}