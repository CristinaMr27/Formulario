package com.example.formulario.ui.screens.request_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.formulario.data.model.FormState
import com.example.formulario.data.model.Request
import com.example.formulario.data.repository.RequestRepository
import com.example.formulario.domain.validation.FormValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val repository: RequestRepository
) : ViewModel() {

    private val _formState = MutableStateFlow(FormState())
    val formState: StateFlow<FormState> = _formState

    private val _requests = MutableStateFlow<List<Request>>(emptyList())
    val requests: StateFlow<List<Request>> = _requests

    fun updateTitle(title: String) {
        _formState.value = _formState.value.copy(title = title, errorMessage = null)
    }

    fun updateDescription(description: String) {
        _formState.value = _formState.value.copy(description = description, errorMessage = null)
    }

    fun updateCategory(category: String) {
        _formState.value = _formState.value.copy(category = category, errorMessage = null)
    }

    fun updatePriority(priority: Int) {
        _formState.value = _formState.value.copy(priority = priority, errorMessage = null)
    }

    fun updateEmail(email: String) {
        _formState.value = _formState.value.copy(email = email, errorMessage = null)
    }

    fun submitRequest() {

        val currentState = _formState.value

        if (currentState.isLoading) return

        if (!FormValidator.validateForm(currentState)) {
            _formState.update {
                it.copy(errorMessage = "Please complete all fields correctly")
            }
            return
        }

        viewModelScope.launch {

            _formState.update { it.copy(isLoading = true) }

            val request = Request(
                title = currentState.title,
                description = currentState.description,
                category = currentState.category,
                priority = currentState.priority,
                email = currentState.email
            )

            val result = repository.insertRequest(request)

            result.onSuccess {
                _formState.update {
                    it.copy(
                        isLoading = false,
                        successMessage = "Request submitted successfully"
                    )
                }

                loadRequests()
            }

            result.onFailure { exception ->
                _formState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Unknown error occurred"
                    )
                }
            }
        }
    }

    fun loadRequests() {
        viewModelScope.launch {
            _formState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = repository.getRequests()

            result.onSuccess { requests ->
                _requests.value = requests
                _formState.update { it.copy(isLoading = false, successMessage = null) }
            }

            result.onFailure { exception ->
                _requests.value = emptyList()
                _formState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Failed to load requests"
                    )
                }
            }
        }
    }

    fun clearForm() {
        _formState.value = FormState()
    }

    fun retry() {
        _formState.value = _formState.value.copy(errorMessage = null)
    }
}
