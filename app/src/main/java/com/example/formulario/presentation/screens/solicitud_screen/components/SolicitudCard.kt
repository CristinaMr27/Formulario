package com.example.formulario.presentation.screens.solicitud_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.formulario.data.model.Solicitud


@Composable
fun SolicitudCard(solicitud: Solicitud) {

    val prioridadColor = when (solicitud.prioridad) {
        1 -> MaterialTheme.colorScheme.tertiary
        2 -> MaterialTheme.colorScheme.primary
        3 -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.secondary
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // TÍTULO
            Text(
                text = solicitud.titulo,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Divider(color = MaterialTheme.colorScheme.outlineVariant)

            // INFORMACIÓN PRINCIPAL
            InfoRow("Descripción", solicitud.descripcion)
            InfoRow("Categoría", solicitud.categoria)
            InfoRow("Fecha", formatearFecha(solicitud.createdAt))

            // PRIORIDAD DESTACADA
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Prioridad",
                    style = MaterialTheme.typography.labelLarge
                )

                Surface(
                    shape = RoundedCornerShape(50),
                    color = prioridadColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "Nivel ${solicitud.prioridad}",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        color = prioridadColor,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String?) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value ?: "No especificado",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
