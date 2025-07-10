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
import com.example.fitnessappc.AddWorkOutViewModel

@Composable
fun AddWorkOutDialog(show: Boolean,onDismiss: () -> Unit) {
    val AddWorkOutViewModel = viewModel<AddWorkOutViewModel>()
    //var showDialog by remember { mutableStateOf(false) }
    var duration by remember {
        mutableStateOf("")
    }


    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Add Food") },
            text = {
                Column {
                    OutlinedTextField(
                        value = duration,
                        onValueChange = {
                            duration = it
                        },
                        label = {
                            Text(text = "Duration in minutes")
                        }
                    )

                    TextButton(onClick = {

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