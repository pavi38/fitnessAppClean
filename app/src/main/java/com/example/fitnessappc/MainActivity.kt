package com.example.fitnessappc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.fitnessappc.ui.theme.FitnessAppCTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fitnessappc.navigation.LoginNavigation
import com.example.fitnessappc.ui.theme.theme
import com.google.firebase.FirebaseApp


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

object MyNavItemDefaults {
    @Composable
    fun colors() = NavigationBarItemDefaults.colors(
        selectedIconColor    = theme,                     // tint for selected icon
        selectedTextColor    = theme,                     // tint for selected label
        indicatorColor       = theme.copy(alpha = 0.2f),
        unselectedIconColor    = MaterialTheme.colorScheme.onSurfaceVariant,
        unselectedTextColor    = MaterialTheme.colorScheme.onSurfaceVariant
    )
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            FitnessAppCTheme {
               // val navController = rememberNavController()
                val authViewModel : AuthViewModel by viewModels()
                val navController = rememberNavController()
                // observe the back-stack to get the current route
                val backStack by navController.currentBackStackEntryAsState()
                val currentRoute = backStack?.destination?.route
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            if (currentRoute != "login" && currentRoute != "signup") {
                                BottomBar(navController = navController)
                            }
                        }
                    ){ innerPadding ->
                        LoginNavigation(navController, modifier = Modifier.padding(innerPadding), authViewModel)
                    }

                }
            }
        }
    }
}

@Composable
fun BottomBar(modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    val items = listOf(
        BottomNavigationItem(
            title = getString(context,R.string.add),
            selectedIcon = Icons.Filled.Add,
            unselectedIcon = Icons.Outlined.Add,
        ),
        BottomNavigationItem(
            title = getString(context,R.string.me),
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
        ),
        BottomNavigationItem(
            title = getString(context,R.string.weekly),
            selectedIcon = Icons.Filled.DateRange,
            unselectedIcon = Icons.Outlined.DateRange,
        )
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(1)
    }
    NavigationBar{
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                colors = MyNavItemDefaults.colors(),
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.title)
                },
                label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title,
                        tint = if (index == selectedItemIndex) {
                            MaterialTheme.colorScheme.primary
                        }
                        else MaterialTheme.colorScheme.onSurfaceVariant

                    )
                }
            )
        }
    }
}
