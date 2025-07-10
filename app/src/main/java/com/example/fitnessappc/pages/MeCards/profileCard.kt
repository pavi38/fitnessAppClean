package com.example.fitnessappc.pages.MeCards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun ProfileStatsCard(
    modifier: Modifier = Modifier,
    heightText: Int = 0,
    weightText: Int = 0
) {
    Card(
        modifier = modifier
            .padding(18.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val themeColor = MaterialTheme.colorScheme.primary
            // Profile icon on the left
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                tint = themeColor.copy(alpha = 0.2f),
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Height and Weight on the right
            Column {
                Text(
                    text = buildAnnotatedString {
                        // Bold the prefix
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Height: ")
                        }
                        append("$heightText cm")
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = buildAnnotatedString {
                        // Bold the prefix
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Weight: ")
                        }
                        append("$weightText Kg")
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = buildAnnotatedString {
                        // Bold the prefix
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("BMI: ")
                        }
                        val heightInM = heightText / 100f
                        val bmi = weightText / (heightInM * heightInM)
                        println(bmi)

                        append("$bmi")
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
