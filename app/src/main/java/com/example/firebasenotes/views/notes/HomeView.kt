package com.example.firebasenotes.views.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.feature_appbar.navigation.Screens
import com.example.feature_appbar.navigation.listOfNavItems
import com.example.firebasenotes.views.login.PruebaDos
import com.example.firebasenotes.views.login.Pruebas
@Composable
fun HomeView() {
    BottomAppBarExample()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomAppBarExample() {
    val navController = rememberNavController()

    NavHost(
        navController = navController as NavHostController,
        startDestination = Screens.Menu.name
    ) {
        composable(route = Screens.Menu.name) {
            Scaffold(bottomBar = { BottomBar(navController) }) {
                PruebaDos()
            }
        }

        composable(route = Screens.Registro.name) {
            Scaffold(bottomBar = { BottomBar(navController) }) {
                Pruebas()
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination: NavDestination? = navBackStackEntry?.destination
        val customColor = Color(99999)
        listOfNavItems.forEach { navItem ->
            val backgroundColor = when (navItem.route) {
                Screens.Registro.name -> customColor
                Screens.Menu.name -> customColor
                else -> Color.Transparent
            }

            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = navItem.label)
                },
                modifier = Modifier.background(backgroundColor)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BottomAppBarExample()
}