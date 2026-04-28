package com.example.formulario.domain.validation

import android.util.Patterns
import com.example.formulario.data.model.FormState

object FormValidator {

    fun validateTitle(title: String): ValidationResult {
        return when {
            title.length < 5 -> ValidationResult.Error("Minimum 5 characters")
            title.length > 60 -> ValidationResult.Error("Maximum 60 characters")
            else -> ValidationResult.Valid
        }
    }

    fun validateDescription(description: String): ValidationResult {
        return when {
            description.length < 20 -> ValidationResult.Error("Minimum 20 characters")
            description.length > 500 -> ValidationResult.Error("Maximum 500 characters")
            else -> ValidationResult.Valid
        }
    }

    fun validatePriority(priority: Int): ValidationResult {
        return when {
            priority < 1 || priority > 5 -> ValidationResult.Error("Priority 1-5")
            else -> ValidationResult.Valid
        }
    }

    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isEmpty() -> ValidationResult.Error("Email is required")
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult.Error("Invalid email format")
            else -> ValidationResult.Valid
        }
    }

    fun validateForm(form: FormState): Boolean {
        return validateTitle(form.title) is ValidationResult.Valid &&
                validateDescription(form.description) is ValidationResult.Valid &&
                validatePriority(form.priority) is ValidationResult.Valid &&
                validateEmail(form.email) is ValidationResult.Valid &&
                form.category.isNotEmpty()
    }
}
