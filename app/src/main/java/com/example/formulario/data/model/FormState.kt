package com.example.formulario.data.model

data class FormState(
    val titulo: String = "",
    val descripcion: String = "",
    val categoria: String = "",
    val prioridad: Int = 3,
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)