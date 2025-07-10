package com.example.fitnessappc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fitnessappc.AuthViewModel
import com.example.fitnessappc.pages.LoginPage
import com.example.fitnessappc.pages.Me
import com.example.fitnessappc.pages.Signup
import com.example.fitnessappc.model.UserProfile
import com.example.fitnessappc.pages.AddPage
import com.example.fitnessappc.pages.WeeklyPage

@Composable
fun LoginNavigation(navController: NavHostController, modifier: Modifier = Modifier, authViewModel: AuthViewModel) {

    NavHost(navController = navController, startDestination = "login", builder = {
        composable("login"){
            LoginPage(modifier,navController,authViewModel)
        }
        composable("signup"){
            Signup(modifier,navController,authViewModel)
        }
        composable("add"){
            AddPage(modifier)
        }
        composable("weekly"){
            WeeklyPage(modifier)
        }
        composable("me"){ entry ->
            val userProfile = navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<UserProfile>("user")
            Me(modifier, navController, authViewModel, userProfile)
        }

    })
}