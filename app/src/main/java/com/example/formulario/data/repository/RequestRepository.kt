package com.example.formulario.data.repository

import com.example.formulario.data.model.Request
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

class RequestRepository(private val supabase: SupabaseClient) {

    suspend fun insertRequest(request: Request): Result<Request> {
        return try {
            val response = supabase
                .from("solicitudes")
                .insert(request){
                    select()
                }
                .decodeAs<List<Request>>()

            if (response.isNotEmpty()) {
                Result.success(response.first())
            } else {
                Result.failure(Exception("No response received from server"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Failed to insert request: ${e.message ?: "Unknown error"}", e))
        }
    }

    suspend fun getRequests(): Result<List<Request>> {
        return try {
            val requests = supabase
                .from("solicitudes")
                .select {
                    order("created_at", Order.DESCENDING)
                }
                .decodeAs<List<Request>>()

            Result.success(requests)
        } catch (e: Exception) {
            Result.failure(Exception("Failed to load requests: ${e.message ?: "Unknown error"}", e))
        }
    }
}
