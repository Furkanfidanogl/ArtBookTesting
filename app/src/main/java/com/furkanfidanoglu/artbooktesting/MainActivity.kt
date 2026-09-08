package com.furkanfidanoglu.artbooktesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.furkanfidanoglu.artbooktesting.navigation.Navigation
import com.furkanfidanoglu.artbooktesting.screen.AddScreen
import com.furkanfidanoglu.artbooktesting.screen.DetailScreen
import com.furkanfidanoglu.artbooktesting.screen.HomeScreen
import com.furkanfidanoglu.artbooktesting.screen.SearchScreen
import com.furkanfidanoglu.artbooktesting.ui.theme.ArtBookTestingTheme
import com.furkanfidanoglu.artbooktesting.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ArtBookTestingTheme {
                val navController = rememberNavController()
                val viewModel = hiltViewModel<ArtViewModel>()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Navigation.HomeScreen,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Navigation.HomeScreen> {
                            HomeScreen(
                                goToDetailScreen = { id ->
                                    navController.navigate(Navigation.DetailScreen(id))
                                },
                                goToAddScreen = { navController.navigate(Navigation.AddScreen) },
                                viewModel = viewModel
                            )
                        }
                        composable<Navigation.DetailScreen> { backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("id") ?: 0
                            DetailScreen(
                                id = id,
                                viewModel = viewModel,
                                goBack = { navController.popBackStack() }
                            )
                        }
                        composable<Navigation.AddScreen> {
                            AddScreen(
                                goToSearchScreen = { navController.navigate(Navigation.SearchScreen) },
                                viewModel = viewModel,
                                goBack = { navController.popBackStack() }
                            )
                        }
                        composable<Navigation.SearchScreen> {
                            SearchScreen(
                                viewModel = viewModel,
                                goBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
