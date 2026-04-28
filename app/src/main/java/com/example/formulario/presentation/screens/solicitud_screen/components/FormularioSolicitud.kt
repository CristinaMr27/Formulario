package com.example.formulario.presentation.screens.solicitud_screen.components

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.formulario.data.model.FormState


@Composable
fun FormularioSolicitud(
    formState: FormState,
    onTituloChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onCategoriaChange: (String) -> Unit,
    onPrioridadChange: (Int) -> Unit,
    onEmailChange: (String) -> Unit,
    onEnviar: () -> Unit,
    onVerSolicitudes: () -> Unit,
    onReintentar: () -> Unit
) {
    val isTituloValido = formState.titulo.length in 5..60
    val isDescripcionValida = formState.descripcion.length in 20..500
    val isEmailValido = Patterns.EMAIL_ADDRESS
        .matcher(formState.email)
        .matches()
    val isPrioridadValida = formState.prioridad in 1..5


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        "Nueva Solicitud",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                    OutlinedTextField(
                        value = formState.titulo,
                        onValueChange = onTituloChange,
                        label = { Text("Título") },
                        isError = formState.titulo.isNotEmpty() && !isTituloValido,
                        supportingText = {
                            if (formState.titulo.isNotEmpty() && !isTituloValido) {
                                Text("Debe tener entre 5 y 60 caracteres")

                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )

                    OutlinedTextField(
                        value = formState.descripcion,
                        onValueChange = onDescripcionChange,
                        label = { Text("Descripción") },
                        isError = formState.descripcion.isNotEmpty() && !isDescripcionValida,
                        supportingText = {
                            if (formState.descripcion.isNotEmpty() && !isDescripcionValida) {
                                Text("Debe tener entre 20 y 500 caracteres")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        shape = RoundedCornerShape(16.dp)
                    )

                    CategoriaDropdown(
                        selectedCategoria = formState.categoria,
                        onCategoriaChange = onCategoriaChange
                    )

                    PrioridadSlider(
                        prioridad = formState.prioridad,
                        onPrioridadChange = onPrioridadChange
                    )

                    OutlinedTextField(
                        value = formState.email,
                        onValueChange = onEmailChange,
                        label = { Text("Email") },
                        isError = formState.email.isNotEmpty() && !isEmailValido,
                        supportingText = {
                            if (formState.email.isNotEmpty() && !isEmailValido){
                                Text(
                                    text = "Ingresa un email válido",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        } ,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        shape = RoundedCornerShape(16.dp)
                    )

                    if (formState.errorMessage != null) {
                        ErrorCard(
                            message = formState.errorMessage,
                            onReintentar = onReintentar
                        )
                    }

                    if (formState.successMessage != null) {
                        SuccessCard(message = formState.successMessage)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onEnviar,
                        enabled = !formState.isLoading && isEmailValido,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text("Enviar")
                    }

                    Button(
                        onClick = onVerSolicitudes,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text("Mis solicitudes")
                    }
                }
            }
        }
    }
}