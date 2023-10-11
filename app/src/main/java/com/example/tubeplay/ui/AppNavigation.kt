package com.example.tubeplay.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "videoList"
    ) {
        composable("videoList") {
            VideoListScreen(navController)
        }
        composable(
            "videoPlayer/{imageUrl}",
            arguments = listOf(navArgument("imageUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val imageUrl = backStackEntry.arguments?.getString("imageUrl")
            if (!imageUrl.isNullOrBlank()) {
                VideoPlayerScreen(imageUrl)
            }
        }
    }
}
