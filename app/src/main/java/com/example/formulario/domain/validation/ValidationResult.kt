package com.example.formulario.domain.validation

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}