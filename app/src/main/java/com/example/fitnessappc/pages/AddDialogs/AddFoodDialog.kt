package com.example.fitnessappc.pages.AddDialogs
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
                }

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text("Delete")
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
