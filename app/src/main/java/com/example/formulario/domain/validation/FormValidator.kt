package com.example.formulario.domain.validation

import android.content.Context
import android.util.Patterns
import com.example.formulario.R
import com.example.formulario.data.model.FormState

object FormValidator {

    fun validateTitle(title: String, context: Context): ValidationResult {
        return when {
            title.length < 5 -> ValidationResult.Error(context.getString(R.string.title_min_chars))
            title.length > 60 -> ValidationResult.Error(context.getString(R.string.title_max_chars))
            else -> ValidationResult.Valid
        }
    }

    fun validateDescription(description: String, context: Context): ValidationResult {
        return when {
            description.length < 20 -> ValidationResult.Error(context.getString(R.string.description_min_chars))
            description.length > 500 -> ValidationResult.Error(context.getString(R.string.description_max_chars))
            else -> ValidationResult.Valid
        }
    }

    fun validatePriority(priority: Int, context: Context): ValidationResult {
        return when {
            priority < 1 || priority > 5 -> ValidationResult.Error(context.getString(R.string.priority_range))
            else -> ValidationResult.Valid
        }
    }

    fun validateEmail(email: String, context: Context): ValidationResult {
        return when {
            email.isEmpty() -> ValidationResult.Error(context.getString(R.string.email_required))
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult.Error(context.getString(R.string.email_invalid_format))
            else -> ValidationResult.Valid
        }
    }

    fun validateForm(form: FormState, context: Context): Boolean {
        return validateTitle(form.title, context) is ValidationResult.Valid &&
                validateDescription(form.description, context) is ValidationResult.Valid &&
                validatePriority(form.priority, context) is ValidationResult.Valid &&
                validateEmail(form.email, context) is ValidationResult.Valid &&
                form.category.isNotEmpty()
    }
}
