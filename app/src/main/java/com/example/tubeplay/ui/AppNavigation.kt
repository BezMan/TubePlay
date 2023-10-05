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
            "videoPlayer/{thumbnailUrl}",
            arguments = listOf(navArgument("thumbnailUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val thumbnailUrl = backStackEntry.arguments?.getString("thumbnailUrl")
            if (!thumbnailUrl.isNullOrBlank()) {
                VideoPlayerScreen(thumbnailUrl)
            }
        }
    }
}
