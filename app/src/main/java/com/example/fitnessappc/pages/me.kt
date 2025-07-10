package com.example.fitnessappc.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessappc.AuthState
import com.example.fitnessappc.AuthViewModel
import com.example.fitnessappc.model.UserProfile
import com.example.fitnessappc.pages.MeCards.CaloriesCard
import com.example.fitnessappc.pages.MeCards.ProfileStatsCard

@Composable
fun Me(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel, userProfile: UserProfile?) {
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 0.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)  // ← 8.dp between items
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            ProfileStatsCard(
                modifier = Modifier.fillMaxWidth(),
                heightText = userProfile?.height ?: 0.toInt(),
                weightText = userProfile?.height ?: 0.toInt()
            )
        }
        item { CaloriesCard() }
        item {
            if (userProfile != null) {
                Text(text = userProfile.email, fontSize = 32.sp)
            }
        }
        item {
            TextButton(onClick = { authViewModel.signout() }) {
                Text("Sign out")
            }
        }
    }
}