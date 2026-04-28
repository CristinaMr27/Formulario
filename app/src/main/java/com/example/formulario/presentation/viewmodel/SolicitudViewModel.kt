package com.example.formulario.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.formulario.data.model.FormState
import com.example.formulario.data.model.Solicitud
import com.example.formulario.data.repository.SolicitudRepository
import com.example.formulario.domain.validation.FormValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolicitudViewModel @Inject constructor(
    private val repository: SolicitudRepository
) : ViewModel() {

    private val _formState = MutableStateFlow(FormState())
    val formState: StateFlow<FormState> = _formState

    private val _solicitudes = MutableStateFlow<List<Solicitud>>(emptyList())
    val solicitudes: StateFlow<List<Solicitud>> = _solicitudes

    fun updateTitulo(titulo: String) {
        _formState.value = _formState.value.copy(titulo = titulo, errorMessage = null)
    }

    fun updateDescripcion(descripcion: String) {
        _formState.value = _formState.value.copy(descripcion = descripcion, errorMessage = null)
    }

    fun updateCategoria(categoria: String) {
        _formState.value = _formState.value.copy(categoria = categoria, errorMessage = null)
    }

    fun updatePrioridad(prioridad: Int) {
        _formState.value = _formState.value.copy(prioridad = prioridad, errorMessage = null)
    }

    fun updateEmail(email: String) {
        _formState.value = _formState.value.copy(email = email, errorMessage = null)
    }

    fun enviarSolicitud() {

        val currentState = _formState.value

        if (currentState.isLoading) return

        if (!FormValidator.validateForm(currentState)) {
            _formState.update {
                it.copy(errorMessage = "Por favor completa todos los campos correctamente")
            }
            return
        }

        viewModelScope.launch {

            _formState.update { it.copy(isLoading = true) }

            val solicitud = Solicitud(
                titulo = currentState.titulo,
                descripcion = currentState.descripcion,
                categoria = currentState.categoria,
                prioridad = currentState.prioridad,
                email = currentState.email
            )

            val result = repository.insertarSolicitud(solicitud)

            result.onSuccess {
                _formState.update {
                    it.copy(
                        isLoading = false,
                        successMessage = "Solicitud enviada correctamente"
                    )
                }

                cargarSolicitudes(currentState.email)
            }

            result.onFailure { exception ->
                _formState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error: ${exception.message ?: "Error desconocido"}"
                    )
                }
            }
        }
    }

    fun cargarSolicitudes(email: String) {
        viewModelScope.launch {
            val result = repository.obtenerSolicitudes(email)

            result.onSuccess { solicitudes ->
                _solicitudes.value = solicitudes
                _formState.value = _formState.value.copy(successMessage = null)
            }

            result.onFailure { exception ->
                _formState.value = _formState.value.copy(
                    errorMessage = "Error al cargar solicitudes: ${exception.message}"
                )
            }
        }
    }

    fun limpiarFormulario() {
        _formState.value = FormState(email = _formState.value.email)
    }

    fun reintentar() {
        _formState.value = _formState.value.copy(errorMessage = null)
    }
}
