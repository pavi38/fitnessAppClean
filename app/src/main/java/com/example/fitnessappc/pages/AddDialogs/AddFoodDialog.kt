package com.example.fitnessappc.pages.AddDialogs
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitnessappc.AddFoodViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp


@Composable
fun AddFoodDialog(show: Boolean,onDismiss: () -> Unit) {
    val AddFoodViewModel = viewModel<AddFoodViewModel>()
    //var showDialog by remember { mutableStateOf(false) }
    var dish by remember {
        mutableStateOf("")
    }

    var Quintity by remember {
        mutableStateOf("")
    }

    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Add Food") },
            text = {
                Column {
                    OutlinedTextField(
                        value = dish,
                        onValueChange = {
                            dish = it
                        },
                        label = {
                            Text(text = "Search for food")
                        }
                    )
                    OutlinedTextField(
                        value = Quintity,
                        onValueChange = {
                            Quintity = it
                        },
                        label = {
                            Text(text = "Weight in grams")
                        }
                    )
                    TextButton(onClick = {
                        AddFoodViewModel.searchFood(dish,Quintity)
                    }){
                        Text("Search")
                    }
                    if(AddFoodViewModel.macrosVisible){
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .aspectRatio(3f)
                        ){
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                Row(
                                    modifier = Modifier
                                        .padding(top = 16.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically

                                ){
                                    Text(
                                        text = buildAnnotatedString {
                                            // Bold the prefix
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("Calories: ")
                                            }
                                            append("${AddFoodViewModel.calories} cal")
                                        },
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = buildAnnotatedString {
                                            // Bold the prefix
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("Protein: ")
                                            }
                                            append("${AddFoodViewModel.protein} g")
                                        },
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically

                                ){
                                    Text(
                                        text = buildAnnotatedString {
                                            // Bold the prefix
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("Carbs: ")
                                            }
                                            append("${AddFoodViewModel.carb} cal")
                                        },
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = buildAnnotatedString {
                                            // Bold the prefix
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("fats: ")
                                            }
                                            append("${AddFoodViewModel.fat} g")
                                        },
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                        AddFoodViewModel.uploadMacros()
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}
