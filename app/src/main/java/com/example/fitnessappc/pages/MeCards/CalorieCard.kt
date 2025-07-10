package com.example.fitnessappc.pages.MeCards
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CaloriesCard(
    modifier: Modifier = Modifier,
    baseGoal: Int = 2000,
    food: Int = 0,
    exercise: Int = 0
) {
    val remaining = baseGoal - food + exercise

    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .padding(18.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text("Calories", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Remaining = Goal - Food + Exercise",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(140.dp)
                ) {
                    PercentageCircle(progress = 1 - remaining.toFloat() / baseGoal.toFloat())
                    Canvas(modifier = Modifier.size(140.dp)) {
                        drawCircle(
                            color = Color.LightGray.copy(alpha = 0.2f),
                            style = Stroke(width = 12f)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            remaining.toString(),
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text("Remaining", style = MaterialTheme.typography.titleMedium)
                    }
                }
                // Right: Info Column
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 16.dp)
                ) {
                    CalorieInfoRow(
                        icon = Icons.Default.Flag,
                        label = "Goal",
                        value = baseGoal
                    )
                    CalorieInfoRow(
                        icon = Icons.Default.Restaurant,
                        label = "Food",
                        value = food,
                        iconTint = Color(0xFF1877F2) // blue
                    )
                    CalorieInfoRow(
                        icon = Icons.Default.LocalFireDepartment,
                        label = "Exercise",
                        value = exercise,
                        iconTint = Color(0xFFFF9800) // orange
                    )
                }
            }
        }
    }
}

@Composable
fun PercentageCircle(
    progress: Float,
    size: Dp = 140.dp,
    strokeWidth: Dp = 12.dp
) {
    val themeColor = MaterialTheme.colorScheme.primary
    Canvas(modifier = Modifier.size(size)) {
        val sweep = progress.coerceIn(0f, 1f) * 360f
        val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)

        // Background track
        drawArc(
            color = Color.LightGray.copy(alpha = 0.2f),
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = stroke
        )
        // Foreground progress
        drawArc(
            color = themeColor.copy(alpha = 0.2f),
            startAngle = -90f,
            sweepAngle = sweep,
            useCenter = false,
            style = stroke
        )
    }
}


@Composable
fun CalorieInfoRow(
    icon: ImageVector,
    label: String,
    value: Int,
    iconTint: Color = Color.Black
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, fontWeight = FontWeight.Medium, modifier = Modifier.width(72.dp))
        }
        Text(value.toString(), fontWeight = FontWeight.Bold)
    }
}
