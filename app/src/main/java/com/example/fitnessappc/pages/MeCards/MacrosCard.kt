package com.example.fitnessappc.pages.MeCards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MacrosProgressCard(
    modifier: Modifier = Modifier,
    fat: Float, fatGoal: Float,
    protein: Float, proteinGoal: Float,
    carbs: Float, carbsGoal: Float,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Daily Macros",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(12.dp))

            MacroProgressItem(
                label = "Fat",
                amount = fat,
                goal = fatGoal,
                indicatorColor = MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.height(8.dp))

            MacroProgressItem(
                label = "Protein",
                amount = protein,
                goal = proteinGoal,
                indicatorColor = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))

            MacroProgressItem(
                label = "Carbs",
                amount = carbs,
                goal = carbsGoal,
                indicatorColor = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
private fun MacroProgressItem(
    label: String,
    amount: Float,
    goal: Float,
    indicatorColor: Color
) {
    // Compute 0..1 progress, clamp to [0,1]
    val progress = (amount / goal).coerceIn(0f, 1f)

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "${amount.toInt()}/${goal.toInt()} g",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = progress,
            color = indicatorColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        )
    }
}
