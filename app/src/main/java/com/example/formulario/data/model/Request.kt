package com.example.formulario.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Request(
    val id: String? = null,
    @SerialName("titulo")
    val title: String,
    @SerialName("descripcion")
    val description: String,
    @SerialName("categoria")
    val category: String,
    @SerialName("prioridad")
    val priority: Int,
    val email: String,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("user_id")
    val userId: String? = null
)
