package com.example.aitest

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

// Define our navigation routes
sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object EventDetail : Screen("event_detail_screen/{title}/{date}/{location}/{description}") {
        fun createRoute(title: String, date: String, location: String, description: String): String {
            val encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString())
            val encodedDate = URLEncoder.encode(date, StandardCharsets.UTF_8.toString())
            val encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8.toString())
            val encodedDescription = URLEncoder.encode(description, StandardCharsets.UTF_8.toString())
            return "event_detail_screen/$encodedTitle/$encodedDate/$encodedLocation/$encodedDescription"
        }
    }
    object Payment : Screen("payment_screen")
    object Confirmation : Screen("confirmation_screen")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        // Main screen
        composable(route = Screen.Main.route) {
            EventApp(
                onEventClick = { title, date, location, description ->
                    navController.navigate(Screen.EventDetail.createRoute(title, date, location, description))
                }
            )
        }

        // Event detail screen
        composable(
            route = Screen.EventDetail.route,
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("date") { type = NavType.StringType },
                navArgument("location") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val date = backStackEntry.arguments?.getString("date") ?: ""
            val location = backStackEntry.arguments?.getString("location") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""

            EventDetailScreen(
                eventTitle = title,
                eventDate = date,
                eventLocation = location,
                eventDescription = description,
                onBackClick = { navController.popBackStack() },
                onBuyTicket = { navController.navigate(Screen.Payment.route) }
            )
        }

        // Payment screen
        composable(route = Screen.Payment.route) {
            PaymentMethodScreen(
                onBackClick = { navController.popBackStack() },
                onPaymentConfirmed = { navController.navigate(Screen.Confirmation.route) }
            )
        }

        // Add confirmation screen
        composable(route = Screen.Confirmation.route) {
            PaymentConfirmationScreen(
                onBackToHome = { navController.navigate(Screen.Main.route) {
                    popUpTo(Screen.Main.route) { inclusive = true }
                }}
            )
        }
    }
}

@Composable
fun PaymentConfirmationScreen(onBackToHome: () -> Unit) {
    ConfirmationScreen(onBackToHome)
}

