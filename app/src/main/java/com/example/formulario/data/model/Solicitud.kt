package com.example.formulario.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Solicitud(
    val id: String? = null,
    val titulo: String,
    val descripcion: String,
    val categoria: String,
    val prioridad: Int,
    val email: String,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("user_id")
    val userId: String? = null
)