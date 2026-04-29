package com.example.formulario.data.repository

import android.content.Context
import com.example.formulario.R
import com.example.formulario.data.model.Request
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

class RequestRepository(
    private val supabase: SupabaseClient,
    private val context: Context
) {

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
                Result.failure(Exception(context.getString(R.string.error_no_response)))
            }
        } catch (e: Exception) {
            Result.failure(Exception(
                context.getString(R.string.error_insert_failed, e.message ?: context.getString(R.string.error_unknown)),
                e
            ))
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
            Result.failure(Exception(
                context.getString(R.string.error_load_failed, e.message ?: context.getString(R.string.error_unknown)),
                e
            ))
        }
    }
}
