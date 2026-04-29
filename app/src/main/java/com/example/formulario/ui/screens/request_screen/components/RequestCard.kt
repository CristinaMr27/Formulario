package com.example.formulario.ui.screens.request_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.formulario.R
import com.example.formulario.data.model.Request


@Composable
fun RequestCard(request: Request) {


    val priorityColor = when (request.priority) {
        1 -> Color(0xFFA5D6A7)
        2 -> Color(0xFFFFF59D)
        3 -> Color(0xFFCE93D8)
        4 -> Color(0xFFFFAB91)
        5 -> Color(0xFFEF9A9A)
        else -> Color(0xFFB0BEC5)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = request.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            HorizontalDivider(
                Modifier,
                DividerDefaults.Thickness,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            InfoRow(stringResource(R.string.card_description), request.description)
            InfoRow(stringResource(R.string.card_category), request.category)
            InfoRow(stringResource(R.string.card_email), request.email)
            InfoRow(stringResource(R.string.card_date), formatDate(request.createdAt))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.card_priority),
                    style = MaterialTheme.typography.labelLarge
                )

                Surface(
                    shape = RoundedCornerShape(50),
                    color = priorityColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = stringResource(R.string.card_priority_level, request.priority),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        color = priorityColor,
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
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value ?: stringResource(R.string.not_specified),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
