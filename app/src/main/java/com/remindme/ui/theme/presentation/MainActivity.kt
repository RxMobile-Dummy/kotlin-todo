package com.remindme.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.remindme.navigation.NavGraph
import com.remindme.presentation.model.AppThemeOptions
import com.remindme.designsystem.RemindMeTheme
import dagger.hilt.android.AndroidEntryPoint
/**
 * Main RemindMe Activity.
 */

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    //internal val mainViewModel=  hiltViewModel<MainViewModel>()
  //  private val mainViewModel by viewModels<MainViewModel>()
 //   private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
           // val isDarkTheme = rememberIsDarkTheme()
           // updateTheme(isDarkTheme)

//            RemindMeTheme(darkTheme = isDarkTheme) {
//                NavGraph()
//            }
        }
    }

    private fun updateTheme(darkTheme: Boolean) {
        window.apply {
            statusBarColor = if (darkTheme) Color.BLACK else Color.WHITE
            navigationBarColor = if (darkTheme) Color.BLACK else Color.WHITE
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars =
                !darkTheme
        }
    }

//    @Composable
//    private fun rememberIsDarkTheme(viewModel: MainViewModel = mainViewModel): Boolean {
//        val isSystemDarkTheme = isSystemInDarkTheme()
//
//        val theme by remember(viewModel) {
//            viewModel.loadCurrentTheme()
//        }.collectAsState(initial = AppThemeOptions.SYSTEM)
//
//        val isDarkTheme = when (theme) {
//            AppThemeOptions.LIGHT -> false
//            AppThemeOptions.DARK -> true
//            AppThemeOptions.SYSTEM -> isSystemDarkTheme
//        }
//        return isDarkTheme
//    }
}
