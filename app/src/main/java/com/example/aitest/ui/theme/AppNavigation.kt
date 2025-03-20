package com.example.aitest

import EventDetailScreen
import PaymentMethodScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Define our navigation routes
sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object EventDetail : Screen("event_detail_screen/{title}/{date}/{location}") {
        fun createRoute(title: String, date: String, location: String): String {
            return "event_detail_screen/$title/$date/$location"
        }
    }
    object Payment : Screen("payment_screen")
}

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        // Main screen
        composable(route = Screen.Main.route) {
            EventApp(
                onEventClick = { title: String, date: String, location: String, description: String ->
                    navController.navigate(Screen.EventDetail.createRoute(title, date, location))
                }
            )
        }

        // Event detail screen
        composable(route = Screen.EventDetail.route) { backStackEntry ->
            val title: String = backStackEntry.arguments?.getString("title") ?: ""
            val date: String = backStackEntry.arguments?.getString("date") ?: ""
            val location: String = backStackEntry.arguments?.getString("location") ?: ""

            // Default description
            val description: String = "Experience the amazing $title event!"

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
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}