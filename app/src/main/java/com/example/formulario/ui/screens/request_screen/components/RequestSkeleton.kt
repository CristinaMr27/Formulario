package com.example.formulario.ui.screens.request_screen.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp


@Composable
fun RequestCardSkeleton() {
    val infiniteTransition = rememberInfiniteTransition(label = "skeletonTransition")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "skeletonAlpha"
    )

    val baseColor = MaterialTheme.colorScheme.surfaceVariant

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
            SkeletonLine(
                widthFraction = 0.6f,
                height = 24.dp,
                baseColor = baseColor,
                alpha = alpha
            )

            SkeletonLine(
                widthFraction = 1f,
                height = 1.dp,
                baseColor = baseColor,
                alpha = alpha
            )

            repeat(4) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    SkeletonLine(
                        widthFraction = 0.3f,
                        height = 12.dp,
                        baseColor = baseColor,
                        alpha = alpha
                    )
                    SkeletonLine(
                        widthFraction = 0.8f,
                        height = 16.dp,
                        baseColor = baseColor,
                        alpha = alpha
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SkeletonLine(
                    widthFraction = 0.2f,
                    height = 14.dp,
                    baseColor = baseColor,
                    alpha = alpha
                )

                Box(
                    modifier = Modifier
                        .height(26.dp)
                        .fillMaxWidth(0.3f)
                        .background(
                            color = baseColor.copy(alpha = alpha),
                            shape = RoundedCornerShape(50)
                        )
                )
            }
        }
    }
}

@Composable
private fun SkeletonLine(
    widthFraction: Float,
    height: androidx.compose.ui.unit.Dp,
    baseColor: androidx.compose.ui.graphics.Color,
    alpha: Float
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth(widthFraction)
            .height(height)
            .alpha(alpha)
            .background(
                color = baseColor,
                shape = RoundedCornerShape(8.dp)
            )
    )
}
