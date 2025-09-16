package com.taskverse.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.taskverse.app.ui.screen.HomeScreen
import com.taskverse.app.ui.screen.LoginScreen
import com.taskverse.app.ui.screen.ProfileScreen
import com.taskverse.app.ui.screen.RegisterScreen
import com.taskverse.app.ui.screen.addtask.screen.AddTaskScreen

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val ADD_TASK = "addTask"
    const val PROFILE = "profile"
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Routes.LOGIN
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Login Screen
        composable(Routes.LOGIN) {
            LoginScreen(
                navController = navController
            )
        }

        // Register Screen
        composable(Routes.REGISTER) {
            RegisterScreen(
                navController = navController
            )
        }

        // Home Screen
        composable(Routes.HOME) {
            HomeScreen(
                onAddTaskClick = { navController.navigate(Routes.ADD_TASK) },
                onProfileClick = { navController.navigate(Routes.PROFILE) }
            )
        }

        // Add Task Screen
        composable(Routes.ADD_TASK) {
            AddTaskScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // Profile Screen
        composable(Routes.PROFILE) {
            ProfileScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
