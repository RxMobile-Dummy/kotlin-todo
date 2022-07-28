package com.remindme.task.presentation.list

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.remindme.R
import com.remindme.categoryapi.model.Category
import com.remindme.categoryapi.presentation.CategoryListViewModel
import com.remindme.categoryapi.presentation.CategoryState
import com.remindme.designsystem.RemindMeTheme
import com.remindme.designsystem.components.AddFloatingButton
import com.remindme.designsystem.components.DefaultIconTextContent
import com.remindme.designsystem.components.RemindMeLoadingContent
import com.todotask.model.Task
import com.todotask.model.TaskWithCategory
import com.todotask.presentation.category.CategorySelection
import com.todotask.presentation.detail.main.CategoryId
import kotlinx.coroutines.launch
import java.util.*

/**
 * RemindMe Task Section.
 *
 * @param modifier the decorator
 * @param onItemClick action to be called when a item is clicked
 * @param onBottomShow action to be called when the bottom sheet is shown
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskListSection(
    modifier: Modifier = Modifier,
    onItemClick: (Int?) -> Unit,
    onBottomShow: () -> Unit
) {
    TaskListLoader(modifier = modifier, onItemClick = onItemClick, onAddClick = onBottomShow)
}

@Composable
private fun TaskListLoader(
    modifier: Modifier = Modifier,
    onItemClick: (Int?) -> Unit,
    onAddClick: () -> Unit,
    taskListViewModel: TaskListViewModel = hiltViewModel(),
    categoryViewModel: CategoryListViewModel = hiltViewModel()
) {
    val (currentCategory, setCategory) = rememberSaveable { mutableStateOf<CategoryId?>(null) }

    val taskViewState by remember(taskListViewModel, currentCategory) {
        taskListViewModel.loadTaskList(currentCategory?.value)
    }.collectAsState(initial = TaskListViewState.Loading)

    val categoryViewState by remember(categoryViewModel) { categoryViewModel.loadCategories() }
        .collectAsState(initial = CategoryState.Loading)

    val taskHandler = TaskStateHandler(
        state = taskViewState,
        onCheckedChange = taskListViewModel::updateTaskStatus,
        onItemClick = onItemClick,
        onAddClick = onAddClick
    )

    val categoryHandler = CategoryStateHandler(
        state = categoryViewState,
        currentCategory = currentCategory,
        onCategoryChange = setCategory,
    )

    TaskListScaffold(
        taskHandler = taskHandler,
        categoryHandler = categoryHandler,
        modifier = modifier,
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun TaskListScaffold(
    taskHandler: TaskStateHandler,
    categoryHandler: CategoryStateHandler,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val snackbarTitle = stringResource(id = R.string.task_snackbar_message_complete)
    val snackbarButton = stringResource(id = R.string.task_snackbar_button_undo)

    val onShowSnackbar: (TaskWithCategory) -> Unit = { taskWithCategory ->
        coroutineScope.launch {
            val message = String.format(snackbarTitle, taskWithCategory.task.title)
            when (scaffoldState.snackbarHostState.showSnackbar(message, snackbarButton)) {
                SnackbarResult.Dismissed -> { /* Do nothing */
                }
                SnackbarResult.ActionPerformed -> taskHandler.onCheckedChange(taskWithCategory)
            }
        }
    }

    BoxWithConstraints {
        val fabPosition = if (this.maxHeight > maxWidth) FabPosition.Center else FabPosition.End
        Scaffold(
            modifier = modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            backgroundColor = MaterialTheme.colors.background,
            topBar = { TaskFilter(categoryHandler = categoryHandler) },
            floatingActionButton = {
                AddFloatingButton(
                    contentDescription = R.string.task_cd_add_task,
                    onClick = { taskHandler.onAddClick() }
                )
            },
            floatingActionButtonPosition = fabPosition
        ) {
            Crossfade(taskHandler.state) { state ->
                when (state) {
                    TaskListViewState.Loading -> RemindMeLoadingContent()
                    is TaskListViewState.Error -> TaskListError()
                    is TaskListViewState.Loaded -> {
                        val taskList = state.items
                        TaskListContent(
                            taskList = taskList,
                            onItemClick = taskHandler.onItemClick,
                            onCheckedChange = { taskWithCategory ->
                                taskHandler.onCheckedChange(taskWithCategory)
                                onShowSnackbar(taskWithCategory)
                            }
                        )
                    }
                    TaskListViewState.Empty -> TaskListEmpty()
                    else -> {}
                }
            }
        }
    }
}

@Composable
private fun TaskFilter(categoryHandler: CategoryStateHandler) {
    CategorySelection(
        state = categoryHandler.state,
        currentCategory = categoryHandler.currentCategory?.value,
        onCategoryChange = categoryHandler.onCategoryChange,
        modifier = Modifier.padding(start = 16.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TaskListContent(
    taskList: List<TaskWithCategory>,
    onItemClick: (Int?) -> Unit,
    onCheckedChange: (TaskWithCategory) -> Unit
) {
    BoxWithConstraints(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
        val cellCount = if (this.maxHeight > maxWidth) 1 else 2
        LazyVerticalGrid(
            columns = GridCells.Fixed(cellCount),
            contentPadding = PaddingValues(bottom = 48.dp)
        ) {
            items(
                items = taskList,
                itemContent = { task ->
                    TaskItem(
                        task = task,
                        onItemClick = onItemClick,
                        onCheckedChange = onCheckedChange
                    )
                }
            )
        }
    }
}

@Composable
private fun TaskListEmpty() {
    DefaultIconTextContent(
        icon = Icons.Outlined.ThumbUp,
        iconContentDescription = R.string.task_list_cd_empty_list,
        header = R.string.task_list_header_empty
    )
}

@Composable
private fun TaskListError() {
    DefaultIconTextContent(
        icon = Icons.Outlined.Close,
        iconContentDescription = R.string.task_list_cd_error,
        header = R.string.task_list_header_error
    )
}

@ExperimentalMaterialApi
@Suppress("UndocumentedPublicFunction")
@Preview
@Composable
fun TaskListScaffoldLoaded() {
    val task1 = Task(title = "Buy milk", dueDate = null)
    val task2 = Task(title = "Call Mark", dueDate = Calendar.getInstance())
    val task3 = Task(title = "Watch Moonlight", dueDate = Calendar.getInstance())

    val category1 = Category(name = "Books", color = android.graphics.Color.GREEN)
    val category2 = Category(name = "Reminders", color = android.graphics.Color.MAGENTA)

    val taskList = listOf(
        TaskWithCategory(task = task1, category = category1),
        TaskWithCategory(task = task2, category = category2),
        TaskWithCategory(task = task3, category = null)
    )

    val state = TaskListViewState.Loaded(items = taskList)

    RemindMeTheme {
        TaskListScaffold(
            taskHandler = TaskStateHandler(state = state),
            categoryHandler = CategoryStateHandler(),
            modifier = Modifier,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Suppress("UndocumentedPublicFunction")
@Preview
@Composable
fun TaskListScaffoldEmpty() {
    val state = TaskListViewState.Empty

    RemindMeTheme {
        TaskListScaffold(
            taskHandler = TaskStateHandler(state = state),
            categoryHandler = CategoryStateHandler(),
            modifier = Modifier,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Suppress("UndocumentedPublicFunction")
@Preview
@Composable
fun TaskListScaffoldError() {
    val state = TaskListViewState.Error(cause = IllegalAccessException())

    RemindMeTheme {
        TaskListScaffold(
            taskHandler = TaskStateHandler(state = state),
            categoryHandler = CategoryStateHandler(),
            modifier = Modifier,
        )
    }
}
