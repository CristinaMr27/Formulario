package com.example.formulario.presentation.screens.solicitud_screen

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.formulario.presentation.screens.solicitud_screen.components.FormularioSolicitud
import com.example.formulario.presentation.screens.solicitud_screen.components.ListadoSolicitudes
import com.example.formulario.presentation.viewmodel.SolicitudViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitudScreen(viewModel: SolicitudViewModel = hiltViewModel()) {
    val formState by viewModel.formState.collectAsState()
    val solicitudes by viewModel.solicitudes.collectAsState()

    var mostrarListado by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(formState.successMessage) {
        formState.successMessage?.let { mensaje ->
            Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
            viewModel.limpiarFormulario()
        }
    }

    if (mostrarListado && solicitudes.isNotEmpty()) {
        ListadoSolicitudes(
            solicitudes = solicitudes,
            onVolver = { mostrarListado = false }
        )
    } else {
        FormularioSolicitud(
            formState = formState,
            onTituloChange = viewModel::updateTitulo,
            onDescripcionChange = viewModel::updateDescripcion,
            onCategoriaChange = viewModel::updateCategoria,
            onPrioridadChange = viewModel::updatePrioridad,
            onEmailChange = viewModel::updateEmail,
            onEnviar = {
                viewModel.enviarSolicitud()
                viewModel.limpiarFormulario()
            },
            onVerSolicitudes = {
                viewModel.cargarSolicitudes(formState.email)
                mostrarListado = true
            },
            onReintentar = viewModel::reintentar
        )
    }
}