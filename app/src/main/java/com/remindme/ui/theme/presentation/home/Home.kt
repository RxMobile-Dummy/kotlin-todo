package com.remindme.ui.theme.presentation.home

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.remindme.category_task.presentation.bottomsheet.CategoryBottomSheet
import com.remindme.category_task.presentation.list.CategoryListSection
import com.remindme.model.HomeSection

import com.remindme.model.HomeSection.*
import com.remindme.preference.model.AppThemeOptions
import com.remindme.preference.presentation.PreferenceSection
import com.remindme.searchTask.presentation.SearchSection

import com.remindme.ui.theme.RemindMeTheme
import com.todotask.presentation.detail.TaskDetailActions
import com.todotask.presentation.list.TaskListSection
import kotlinx.coroutines.launch

/**
 * RemidMe Home screen.
 */
@Composable
fun Home(onTaskClick: (Long?) -> Unit, onAboutClick: () -> Unit,
         onTrackerClick: () -> Unit,appThemeOptions: AppThemeOptions,actions1: TaskDetailActions,navController: NavController) {
    val (currentSection, setCurrentSection) = rememberSaveable { mutableStateOf(Tasks) }
    val navItems = values().toList()
    val homeModifier = Modifier.padding(bottom = 56.dp)

    val actions = HomeActions(
        onTaskClick = onTaskClick,
        onAboutClick = onAboutClick,
        onTrackerClick = onTrackerClick,
        setCurrentSection = setCurrentSection,
    )

    Crossfade(currentSection) { homeSection ->
        RemindMeHomeScaffold(
            homeSection = homeSection,
            modifier = homeModifier,
            navItems = navItems,
            actions = actions,
            appThemeOptions= appThemeOptions,
            actions1 =actions1
        , navController = navController
        )
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RemindMeHomeScaffold(
    homeSection: HomeSection,
    modifier: Modifier,
    navItems: List<HomeSection>,
    actions: HomeActions,
    appThemeOptions: AppThemeOptions,
    actions1: TaskDetailActions,
  //  onBottomShow: (@Composable () -> Unit),
    navController: NavController

    ) {
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var sheetContentState by rememberSaveable {
        mutableStateOf<SheetContentState>(SheetContentState.Empty)
    }

    val focusManager = LocalFocusManager.current
    LaunchedEffect(modalSheetState, focusManager) {
        snapshotFlow { modalSheetState.isVisible }.collect { isVisible ->
            if (isVisible.not()) {
                focusManager.clearFocus()
                sheetContentState = SheetContentState.Empty
            }
        }
    }

    val onShowBottomSheet: (SheetContentState) -> Unit = { contentState ->
        sheetContentState = contentState
        coroutineScope.launch { modalSheetState.show() }
    }

    val onHideBottomSheet: () -> Unit = { coroutineScope.launch { modalSheetState.hide() } }

    if (modalSheetState.isVisible) {
        BackHandler { coroutineScope.launch { modalSheetState.hide() } }
    }

    RemindMeBottomSheetLayout(
        modalSheetState = modalSheetState,
        sheetContentState = sheetContentState,
        onHideBottomSheet = onHideBottomSheet,
        navController =  navController
    ) {
        Scaffold(
            topBar = {
                RemindMeTopBar(currentSection = homeSection)
            },
            content = {
                RemindMeContent(homeSection, modifier, actions, onShowBottomSheet,appThemeOptions,actions1,navController)
            },
            bottomBar = {
                RemindMeBottomNav(
                    currentSection = homeSection,
                    onSectionSelect = actions.setCurrentSection,
                    items = navItems
                )
            }
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RemindMeBottomSheetLayout(
    modalSheetState: ModalBottomSheetState,
    sheetContentState: SheetContentState,
    onHideBottomSheet: () -> Unit,
    navController: NavController,
    content: @Composable () -> Unit,
) {

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp)
                    .padding(horizontal = 2.dp)
            ) {
                when (sheetContentState) {
                    SheetContentState.TaskListSheet ->
                        //Log.e("click","click")
                        navController.navigate("add_task")
                   // AddTaskBottomSheet(onHideBottomSheet)


                        //AddTaskBottomSheet(onHideBottomSheet = onHideBottomSheet)
                    is SheetContentState.CategorySheet ->
                        CategoryBottomSheet(
                            category = sheetContentState.category,
                            onHideBottomSheet = onHideBottomSheet
                        )
                    SheetContentState.Empty ->
                        Box(modifier = Modifier.fillMaxSize())
                }
            }
        }
    )
    {
        content()

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RemindMeContent(
    homeSection: HomeSection,
    modifier: Modifier,
    actions: HomeActions,
  // navigationClick: (@Composable () -> Unit),
    onShowBottomSheet: (SheetContentState) -> Unit,
    appThemeOptions: AppThemeOptions,actions1: TaskDetailActions,
    navController: NavController
) {
    when (homeSection) {
        Tasks ->
            TaskListSection(
                modifier = modifier,
                onItemClick = actions.onTaskClick,
                onBottomShow = {
                    navController.navigate("add_task")
                    //  onShowBottomSheet(SheetContentState.TaskListSheet)
                      },
                navController = navController
            )
        Search ->
            SearchSection(modifier = modifier, onItemClick = actions.onTaskClick)
       Categories ->
            CategoryListSection(
                modifier = modifier,
                onShowBottomSheet = { category ->
                    onShowBottomSheet(SheetContentState.CategorySheet(category))
                }
            )
       Settings ->
            PreferenceSection(
                modifier = modifier,
                onAboutClick = actions.onAboutClick,
                onTrackerClick = actions.onTrackerClick,
                theme = appThemeOptions,
                actions1 = actions1
            )

    }
}

@Composable
private fun RemindMeTopBar(currentSection: HomeSection) {
    TopAppBar(backgroundColor = MaterialTheme.colors.background, elevation = 0.dp) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.h5,
                text = stringResource(currentSection.title)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RemindMeBottomNav(
    currentSection: HomeSection,
    onSectionSelect: (HomeSection) -> Unit,
    items: List<HomeSection>
) {
    BottomAppBar(backgroundColor = MaterialTheme.colors.background) {
        items.forEach { section ->
            val selected = section == currentSection
            val colorState = animateColorAsState(
                if (selected) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.onSecondary
                }
            )
            RemindMeBottomIcon(
                section = section,
                tint = colorState.value,
                onSectionSelect = onSectionSelect,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun RemindMeBottomIcon(
    section: HomeSection,
    tint: Color,
    onSectionSelect: (HomeSection) -> Unit,
    modifier: Modifier
) {
    val title = stringResource(section.title)
    IconButton(
        onClick = { onSectionSelect(section) },
        modifier = modifier
    ) {
        Icon(imageVector = section.icon, tint = tint, contentDescription = title)
    }
}

@Suppress("UndocumentedPublicFunction")
@Preview(showBackground = true)
@Composable
fun RemindMeTopBarPreview() {
   RemindMeTheme {
        RemindMeTopBar(Tasks)
    }
}

@Suppress("UndocumentedPublicFunction")
@Preview(showBackground = true)
@Composable
fun RemindMeBottomNavPreview() {
    RemindMeTheme {
        RemindMeBottomNav(
            currentSection = Tasks,
            onSectionSelect = {},
            items = values().toList()
        )
    }
}
