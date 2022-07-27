package com.remindme.ui.theme.presentation.home

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.remindme.category.presentation.bottomsheet.CategoryBottomSheet
import com.remindme.category.presentation.list.CategoryListSection
import com.remindme.model.HomeSection

import com.remindme.model.HomeSection.*
import com.remindme.preference.model.AppThemeOptions
import com.remindme.preference.presentation.PreferenceSection
import com.remindme.preference.presentation.PreferenceViewModel
import com.remindme.presentation.home.HomeActions
import com.remindme.presentation.home.SheetContentState
import com.remindme.search.presentation.SearchSection

import com.remindme.task.presentation.add.AddTaskBottomSheet
import com.remindme.task.presentation.list.TaskListSection
import kotlinx.coroutines.launch

/**
 * RemidMe Home screen.
 */
@Composable
fun Home(onTaskClick: (Int?) -> Unit, onAboutClick: () -> Unit, onTrackerClick: () -> Unit,appThemeOptions: AppThemeOptions) {
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
            appThemeOptions= appThemeOptions
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
    appThemeOptions: AppThemeOptions
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

    ) {
        Scaffold(
            topBar = {
                RemindMeTopBar(currentSection = homeSection)
            },
            content = {
                RemindMeContent(homeSection, modifier, actions, onShowBottomSheet,appThemeOptions)
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
    content: @Composable () -> Unit
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
                        AddTaskBottomSheet(onHideBottomSheet = onHideBottomSheet)
                    is SheetContentState.CategorySheet ->
                        CategoryBottomSheet(
                            category = sheetContentState.category,
                            onHideBottomSheet = onHideBottomSheet
                        )
                    SheetContentState.Empty ->
                        Box(modifier = Modifier.fillMaxSize())
                    else -> {}
                }
            }
        }
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RemindMeContent(
    homeSection: HomeSection,
    modifier: Modifier,
    actions: HomeActions,
    onShowBottomSheet: (SheetContentState) -> Unit,
    appThemeOptions: AppThemeOptions
) {

    when (homeSection) {
        Tasks ->
            TaskListSection(
                modifier = modifier,
                onItemClick = actions.onTaskClick,
                onBottomShow = { onShowBottomSheet(SheetContentState.TaskListSheet) }
            )
        HomeSection.Search ->
            SearchSection(modifier = modifier, onItemClick = actions.onTaskClick)
        HomeSection.Categories ->
            CategoryListSection(
                modifier = modifier,
                onShowBottomSheet = { category ->
                    onShowBottomSheet(SheetContentState.CategorySheet(category))
                }
            )
        HomeSection.Settings ->
            PreferenceSection(
                modifier = modifier,
                onAboutClick = actions.onAboutClick,
                onTrackerClick = actions.onTrackerClick,
                theme = appThemeOptions
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
  //  RemindMeTheme {
        RemindMeTopBar(Tasks)
    //}
}

@Suppress("UndocumentedPublicFunction")
@Preview(showBackground = true)
@Composable
fun RemindMeBottomNavPreview() {
  //  RemindMeTheme {
        RemindMeBottomNav(
            currentSection = Tasks,
            onSectionSelect = {},
            items = values().toList()
        )
    //}
}
