package com.example.formulario.presentation.screens.solicitud_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PrioridadSlider(
    prioridad: Int,
    onPrioridadChange: (Int) -> Unit
) {
    Column {
        Text("Prioridad: $prioridad")
        Slider(
            value = prioridad.toFloat(),
            onValueChange = { onPrioridadChange(it.toInt()) },
            valueRange = 1f..5f,
            steps = 3,
            modifier = Modifier.fillMaxWidth()
        )
    }
}