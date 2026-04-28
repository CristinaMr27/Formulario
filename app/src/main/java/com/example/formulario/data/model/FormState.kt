package com.example.formulario.data.model

data class FormState(
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val priority: Int = 3,
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
