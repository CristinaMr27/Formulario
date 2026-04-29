package com.example.formulario.ui.screens.request_screen.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.formulario.R

@Composable
fun ErrorDialog(
    message: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit
) {
    val simplifiedMessage = when {
        message.contains("timeout", ignoreCase = true) || message.contains("timed out", ignoreCase = true) -> 
            stringResource(R.string.error_timeout)
        message.contains("network", ignoreCase = true) || message.contains("connection", ignoreCase = true) -> 
            stringResource(R.string.error_network)
        message.contains("server", ignoreCase = true) || message.contains("500", ignoreCase = true) -> 
            stringResource(R.string.error_server)
        message.contains("unauthorized", ignoreCase = true) || message.contains("401", ignoreCase = true) -> 
            stringResource(R.string.error_auth)
        message.contains("not found", ignoreCase = true) || message.contains("404", ignoreCase = true) -> 
            stringResource(R.string.error_not_found)
        else -> stringResource(R.string.error_unexpected)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = stringResource(R.string.error_title),
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(text = stringResource(R.string.error_title))
        },
        text = {
            Text(text = simplifiedMessage)
        },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text(stringResource(R.string.error_retry))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.error_close))
            }
        }
    )
}
