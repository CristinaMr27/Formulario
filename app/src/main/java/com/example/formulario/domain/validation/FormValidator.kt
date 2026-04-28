package com.example.formulario.domain.validation

import com.example.formulario.data.model.FormState

object FormValidator {

    fun validateTitulo(titulo: String): ValidationResult {
        return when {
            titulo.length < 5 -> ValidationResult.Error("Mínimo 5 caracteres")
            titulo.length > 60 -> ValidationResult.Error("Máximo 60 caracteres")
            else -> ValidationResult.Valid
        }
    }

    fun validateDescripcion(descripcion: String): ValidationResult {
        return when {
            descripcion.length < 20 -> ValidationResult.Error("Mínimo 20 caracteres")
            descripcion.length > 500 -> ValidationResult.Error("Máximo 500 caracteres")
            else -> ValidationResult.Valid
        }
    }

    fun validateEmail(email: String): ValidationResult {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        return when {
            email.isEmpty() -> ValidationResult.Error("Email requerido")
            !emailRegex.matches(email) -> ValidationResult.Error("Email inválido")
            else -> ValidationResult.Valid
        }
    }

    fun validatePrioridad(prioridad: Int): ValidationResult {
        return when {
            prioridad < 1 || prioridad > 5 -> ValidationResult.Error("Prioridad 1-5")
            else -> ValidationResult.Valid
        }
    }

    fun validateForm(form: FormState): Boolean {
        return validateTitulo(form.titulo) is ValidationResult.Valid &&
                validateDescripcion(form.descripcion) is ValidationResult.Valid &&
                validateEmail(form.email) is ValidationResult.Valid &&
                validatePrioridad(form.prioridad) is ValidationResult.Valid &&
                form.categoria.isNotEmpty()
    }
}

