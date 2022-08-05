package com.remindme.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.window.SplashScreen
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.remindme.ui.theme.presentation.home.Home
import com.remindme.preference.presentation.About
import com.remindme.splitinstall.LoadFeature
import com.todotask.presentation.detail.main.TaskDetailSection
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.remindme.alarmapi.AlarmPermission
import com.remindme.preference.model.AppThemeOptions
import com.remindme.ui.theme.presentation.home.SplashScreen
import com.todotask.presentation.add.AddTaskBottomSheet
import com.todotask.presentation.detail.TaskDetailActions

/**
 * Navigation Graph to control the RemindMe navigation.
 *
 * @param startDestination the start destination of the graph
 */
@OptIn(ExperimentalAnimationApi::class)
@Suppress("LongMethod", "MagicNumber")
@Composable
fun NavGraph(startDestination: String,alarmPermission: AlarmPermission,actions1: TaskDetailActions,
) {
    val navController = rememberAnimatedNavController()
    val context = LocalContext.current
    val appThemeOptions:AppThemeOptions = AppThemeOptions.LIGHT
    val actions = remember(navController) { Actions(navController, context) }
    AnimatedNavHost(navController = navController, startDestination = startDestination) {

        composable(
            route = Destinations.Home,
            deepLinks = listOf(navDeepLink { uriPattern = DestinationDeepLink.HomePattern }),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            }
        ) {
            Home(
                onTaskClick = actions.openTaskDetail,
                onAboutClick = actions.openAbout,
                onTrackerClick = actions.openTracker,
                appThemeOptions = appThemeOptions,
                actions1 =actions1,
                navController = navController
            )
        }

        composable(
            route = "${Destinations.TaskDetail}/{${DestinationArgs.TaskId}}",
            arguments = listOf(navArgument(DestinationArgs.TaskId) { type = NavType.LongType }),
            deepLinks = listOf(navDeepLink { uriPattern = DestinationDeepLink.TaskDetailPattern }),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            TaskDetailSection(
                taskId = arguments.getLong(DestinationArgs.TaskId),
                onUpPress = actions.onUpPress,
                alarmPermission = alarmPermission

            )
        }
        composable(
            route = Destinations.Splash,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },
        ) { backStackEntry ->
            SplashScreen(navController =navController )
        }
        composable(
            route = Destinations.AddTask,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            AddTaskBottomSheet(onUpPress = {
                navController.popBackStack()
            },navController)
        }

        composable(
            route = Destinations.About,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },
        ) {
            About(onUpPress = actions.onUpPress)
        }

        dialog(Destinations.Tracker) {
            LoadFeature(
                context = context,
                featureName = FeatureTracker,
                onDismiss = actions.onUpPress
            ) {
                // Workaround to be able to use Dynamic Feature with Compose
                // https://issuetracker.google.com/issues/183677219
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(TrackerDeepLink)
                    `package` = context.packageName
                }
                context.startActivity(intent)
            }
        }
    }
}

internal data class Actions(val navController: NavHostController, val context: Context) {

    val openTaskDetail: (Long?) -> Unit = { taskId ->
        navController.navigate("${Destinations.TaskDetail}/$taskId")
    }

    val openAbout: () -> Unit = {
        navController.navigate(Destinations.About)
    }

    val openTracker: () -> Unit = {
        navController.navigate(Destinations.Tracker)
    }

    val onUpPress: () -> Unit = {
        navController.navigateUp()
    }
}

private const val FeatureTracker = "tracker"

private const val TrackerDeepLink = "app://com.remindme.tracker"
