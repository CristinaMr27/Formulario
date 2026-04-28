package com.example.formulario.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PastelColorScheme = lightColorScheme(
    primary = Color(0xFFB39DDB),
    onPrimary = Color.White,
    secondary = Color(0xFF80CBC4),
    onSecondary = Color.White,
    tertiary = Color(0xFFFFCCBC),
    background = Color(0xFFF8F6FF),
    surface = Color(0xFFFFFFFF),
    error = Color(0xFFE57373)
)

@Composable
fun FormularioTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PastelColorScheme,
        typography = Typography,
        content = content
    )
}