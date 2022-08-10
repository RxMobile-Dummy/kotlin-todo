package com.remindme.ui.theme.presentation

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.core.view.WindowInsetsControllerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.room.Dao
import com.remindme.alarmapi.AlarmPermission
import com.remindme.navigation.NavGraph
import com.remindme.ui.theme.presentation.model.AppThemeOptions
import com.remindme.designsystem.RemindMeTheme
import com.remindme.glance.presentation.TaskListGlanceViewModel
import com.remindme.glance.presentation.TaskListGlanceWidget
import com.remindme.navigation.Destinations
import com.todotask.presentation.detail.TaskDetailActions
import com.todotask.presentation.list.CategoryStateHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject
import  com.remindme.R
import com.remindme.alarm.TaskReceiver
import com.remindme.categoryapi.model.Category
import com.remindme.categoryapi.presentation.CategoryListViewModel
import com.remindme.local.provider.DaoProvider
import com.remindme.local.provider.DatabaseProvider
import com.remindme.preference.localData.PrefClass.Companion.dataStore
import com.remindme.preference.localData.PrefManager
import com.remindme.ui.theme.presentation.home.SheetContentState
import com.todotask.model.Task
import com.todotask.presentation.detail.alarm.TaskAlarmViewModel
import com.todotask.presentation.detail.main.TaskDetailState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.prefs.Preferences

/**
 * Main RemindMe Activity.
 */

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var mAlarmPermission: AlarmPermission
    private val taskAlarmViewModel: TaskAlarmViewModel by viewModels()

    //val Context.datastore by preferencesDataStore(name=PrefManager.PREFERENCES_NOTIFICATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TaskListGlanceWidget.viewModel =
            ViewModelProvider(this)[TaskListGlanceViewModel::class.java]
        TaskListGlanceWidget.context = this
        var category:Category?=null
        var sheetContentState:SheetContentState = SheetContentState.CategorySheet(category)
        var coroutineScope:CoroutineScope = CoroutineScope(Dispatchers.Default)
        var databaseProvider:DatabaseProvider = DatabaseProvider(this,coroutineScope)
        var daoProvider:DaoProvider? = DaoProvider(databaseProvider)

        setContent {

            val isDarkTheme = rememberIsDarkTheme()
            updateTheme(isDarkTheme)
            var action: TaskDetailActions = TaskDetailActions()


            RemindMeTheme(darkTheme = isDarkTheme) {
                NavGraph(Destinations.Splash, mAlarmPermission, action,this.dataStore, sheetContentState = sheetContentState,taskAlarmViewModel= taskAlarmViewModel,daoProvider=daoProvider!!)
            }
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

    @Composable
    private fun rememberIsDarkTheme(viewModel: MainViewModel = mainViewModel): Boolean {
        val isSystemDarkTheme = isSystemInDarkTheme()

        val theme by remember(viewModel) {
            viewModel.loadCurrentTheme()
        }.collectAsState(initial = AppThemeOptions.SYSTEM)

        val isDarkTheme = when (theme) {
            AppThemeOptions.LIGHT -> false
            AppThemeOptions.DARK -> true
            AppThemeOptions.SYSTEM -> isSystemDarkTheme
        }
        return isDarkTheme
    }


}

