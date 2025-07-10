package com.example.fitnessappc.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitnessappc.AuthState
import com.example.fitnessappc.AuthViewModel
import com.example.fitnessappc.MeViewModel
import com.example.fitnessappc.model.UserProfile
import com.example.fitnessappc.pages.MeCards.CaloriesCard
import com.example.fitnessappc.pages.MeCards.ProfileStatsCard
import androidx.compose.runtime.getValue
import com.example.fitnessappc.pages.MeCards.MacrosProgressCard

@Composable
fun Me(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel, Profile: UserProfile?) {
    val authState = authViewModel.authState.observeAsState()
    val meViewModel = viewModel<MeViewModel>()
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }
    val userProfile by authViewModel.userProfile.observeAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 0.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            ProfileStatsCard(
                modifier = Modifier.fillMaxWidth(),
                heightText = userProfile?.height ?: 0.toInt(),
                weightText = userProfile?.weight ?: 0.toInt()
            )
        }
        item { CaloriesCard(modifier = modifier,food = meViewModel.calories) }
        item {
            if (userProfile != null) {
                //Text(text = userProfile.email, fontSize = 32.sp)
            }
        }
//        item {
//            MacrosProgressCard(modifier,20f, 30f, 0.4f, 0.5f, 0.6f, 0.7f)
//        }
        item {
            TextButton(onClick = { authViewModel.signout() }) {
                Text("Sign out")
            }
        }
    }
}