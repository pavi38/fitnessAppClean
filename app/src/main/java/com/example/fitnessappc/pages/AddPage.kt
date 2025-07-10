package com.example.fitnessappc.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import com.example.fitnessappc.pages.AddDialogs.AddFoodDialog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.DirectionsWalk
import com.example.fitnessappc.pages.AddDialogs.AddWorkOutDialog

data class ActionItem(
    val icon: ImageVector,
    val label: String,
    val circleColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPage(modifier: Modifier = Modifier) {
    val items = listOf(
        ActionItem(Icons.Default.Search,       "Log Food",    Color(0xFF007AFF)),
        ActionItem(Icons.Default.QrCodeScanner,"Barcode Scan",Color(0xFFFF2D55)),
        ActionItem(Icons.Default.FitnessCenter,"Workout Log",   Color(0xFF8E44AD)),
        ActionItem(Icons.Default.DirectionsWalk,    "Step Count",   Color(0xFF1ABC9C))
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(items) { item ->
            ActionCard(item)
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionCard(item: ActionItem) {
    var showAddFoodDialog by remember { mutableStateOf(false) }
    var showAddWorkoutDialog by remember { mutableStateOf(false) }

    Card(
        onClick = {
            if (item.label == "Log Food") {
                showAddFoodDialog = true
            } else if (item.label == "Workout Log") {
                showAddWorkoutDialog = true
            }
        },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(item.circleColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.label,
                fontSize = 12.sp,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
    if(showAddFoodDialog){
        AddFoodDialog(true,onDismiss = { showAddFoodDialog = false })
    } else if(showAddWorkoutDialog){
        AddWorkOutDialog(true,onDismiss = { showAddWorkoutDialog = false })
    }
}
