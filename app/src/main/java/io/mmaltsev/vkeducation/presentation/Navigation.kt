package io.mmaltsev.vkeducation.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.mmaltsev.vkeducation.presentation.appdetails.AppDetailsScreen
import io.mmaltsev.vkeducation.presentation.applist.AppListScreen

private object Routes {
    const val APP_LIST = "app_list"
    const val APP_DETAILS = "app_details/{appId}"

    fun appDetails(appId: String) = "app_details/$appId"
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.APP_LIST,
        modifier = modifier,
    ) {
        composable(route = Routes.APP_LIST) {
            AppListScreen(
                onAppClick = { appId ->
                    navController.navigate(Routes.appDetails(appId))
                },
            )
        }

        composable(
            route = Routes.APP_DETAILS,
            arguments = listOf(
                navArgument("appId") { type = NavType.StringType }
            ),
        ) {
            AppDetailsScreen(
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}