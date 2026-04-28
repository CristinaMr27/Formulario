package com.example.formulario.ui.screens.request_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PrioritySlider(
    priority: Int,
    onPriorityChange: (Int) -> Unit
) {
    Column {
        Text("Priority: $priority")
        Slider(
            value = priority.toFloat(),
            onValueChange = { onPriorityChange(it.toInt()) },
            valueRange = 1f..5f,
            steps = 3,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
