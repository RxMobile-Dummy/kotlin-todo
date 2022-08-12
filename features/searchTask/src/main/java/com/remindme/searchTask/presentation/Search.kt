package com.remindme.searchTask.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remindme.categoryapi.model.Category
import com.remindme.categoryapi.presentation.CategoryListViewModel
import com.remindme.categoryapi.presentation.CategoryState
import com.remindme.designsystem.RemindMeTheme
import com.remindme.designsystem.components.RemindMeLoadingContent
import com.remindme.designsystem.components.DefaultIconTextContent
import com.remindme.searchTask.model.TaskSearchItem
import com.remindme.searchTask.R
import com.todotask.model.Task
import com.todotask.model.TaskWithCategory
import com.todotask.presentation.category.CategorySelection
import com.todotask.presentation.detail.main.CategoryId
import com.todotask.presentation.list.CategoryStateHandler
import com.todotask.presentation.list.TaskListViewModel
import com.todotask.presentation.list.TaskListViewState
import com.todotask.presentation.list.TaskStateHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

//import org.koin.androidx.compose.getViewModel

/**
 * RemindMe Search Section.
 *
 * @param modifier the decorator
 * @param onItemClick action to be called when the item is clicked
 */
@Composable
fun SearchSection(
    modifier: Modifier = Modifier, onItemClick: (Long?) -> Unit,
) {
    SearchLoader(modifier = modifier, onItemClick = onItemClick)
}

@Composable
private fun SearchLoader(
    modifier: Modifier = Modifier,
    onItemClick: (Long?) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
    categoryViewModel: CategoryListViewModel = hiltViewModel(),

    ) {
    val (query, setQuery) = rememberSaveable { mutableStateOf("") }
    val viewState by remember(viewModel, query) { viewModel.findTasksByName(query) }
        .collectAsState(initial = SearchViewState.Loading)

    SearchScaffold(
        viewState = viewState,
        modifier = modifier,
        onItemClick = onItemClick,
        query = query,
        setQuery = setQuery, categoryViewModel = categoryViewModel
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun SearchScaffold(
    modifier: Modifier = Modifier,
    viewState: SearchViewState,
    onItemClick: (Long?) -> Unit,
    taskListViewModel: TaskListViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel(),
    categoryViewModel: CategoryListViewModel = hiltViewModel(),
    query: String,
    setQuery: (String) -> Unit,

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
        onAddClick = {}
    )
    val categoryHandler = CategoryStateHandler(
        state = categoryViewState,
        currentCategory = currentCategory,
        onCategoryChange = setCategory,
    )
    Scaffold(
        modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = { TaskFilter(categoryHandler = categoryHandler) },

        ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            SearchTextField(query, onTextChange = setQuery)

            if(query.isNotEmpty()){
                Crossfade(viewState) { state ->
                    when (state) {
                        SearchViewState.Loading -> RemindMeLoadingContent()
                        SearchViewState.Empty -> SearchEmptyContent()
                        is SearchViewState.Loaded -> SearchTextListContent(
                            taskList = state.taskList,
                            onItemClick = onItemClick
                        )
                    }
                }
            }else
            {
                Crossfade(taskHandler.state) { state ->
                    when (state) {
                        TaskListViewState.Loading -> RemindMeLoadingContent()
                        TaskListViewState.Empty -> SearchEmptyContent()
                        is TaskListViewState.Loaded -> {
                            val taskList = state.items
                            SearchListContent(
                                taskList = taskList,
                                onItemClick = onItemClick,
                                onCheckedChange = { taskWithCategory ->
                                    taskHandler.onCheckedChange(taskWithCategory)
                                    //  onShowSnackbar(taskWithCategory)
                                },
                                taskHandler = taskHandler
                            )
                        }
                        is TaskListViewState.Error -> TaskSearchError()

                    }
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

@Composable
private fun SearchTextField(text: String, onTextChange: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search_cd_icon)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
    )
}

@Composable
private fun SearchEmptyContent() {
    DefaultIconTextContent(
        icon = Icons.Outlined.ExitToApp,
        iconContentDescription = R.string.search_cd_empty_list,
        header = R.string.search_header_empty
    )
}

@Composable
private fun TaskSearchError() {
    DefaultIconTextContent(
        icon = Icons.Outlined.Close,
        iconContentDescription = R.string.task_list_cd_error,
        header = R.string.task_list_header_error
    )
}
@Composable
private fun SearchTextListContent(
    taskList: List<TaskSearchItem>,
    onItemClick: (Long?) -> Unit,

    ) {
    LazyColumn {
        items(
            items = taskList,
            itemContent = { task ->
                SearchTextItem(
                    task = task, onItemClick = onItemClick
                    //  onShowSnackbar(taskWithCategory)
                )
            }
        )
    }
}

@Composable
private fun SearchTextItem(
    task: TaskSearchItem,
    onItemClick: (Long?) -> Unit,
) {
    Column(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .clickable { onItemClick(task.task.id) },
        verticalArrangement = Arrangement.Center
    ) {

        val textDecoration: TextDecoration
        val circleColor: Color

        if (task.task.completed) {
            textDecoration = TextDecoration.LineThrough
            circleColor = MaterialTheme.colors.onSecondary
        } else {
            textDecoration = TextDecoration.None
            circleColor = task.categoryColor ?: MaterialTheme.colors.background
        }

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(circleColor)
            )
            Text(
                text = task.task.title,
                textDecoration = textDecoration,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
                    .height(24.dp)
            )
        }
    }
}


@Composable
private fun SearchListContent(
    taskList: List<TaskWithCategory>,
    onItemClick: (Long?) -> Unit,
    onCheckedChange: (TaskWithCategory) -> Unit,
    taskHandler: TaskStateHandler,

    ) {
    LazyColumn {
        items(
            items = taskList,
            itemContent = { task ->
                SearchItem(
                    task = task, onItemClick = onItemClick, onCheckedChange = onCheckedChange
                    //  onShowSnackbar(taskWithCategory)
                )
            }
        )
    }
}

@Composable
private fun SearchItem(
    task: TaskWithCategory,
    onItemClick: (Long?) -> Unit,
    onCheckedChange: (TaskWithCategory) -> Unit
) {
    Column(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .clickable { onItemClick(task.task.id) },
        verticalArrangement = Arrangement.Center
    ) {

        val textDecoration: TextDecoration
        val circleColor: Color

        if (task.task.completed) {
            textDecoration = TextDecoration.LineThrough
            circleColor = MaterialTheme.colors.onSecondary
        } else {
            textDecoration = TextDecoration.None
            circleColor = task.task.categoryColor ?: MaterialTheme.colors.background
        }

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(circleColor)
            )
            Text(
                text = task.task.title,
                textDecoration = textDecoration,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
                    .height(24.dp)
            )
        }
    }
}

@Suppress("UndocumentedPublicFunction")
@Preview
@Composable
fun SearchLoadedListPreview(
) {
    val task1 = com.remindme.domain.model.Task(title = "Buy milk", dueDate = null)
    val task2 =
        com.remindme.domain.model.Task(title = "Call Mark", dueDate = Calendar.getInstance())
    val task3 =
        com.remindme.domain.model.Task(title = "Watch Moonlight", dueDate = Calendar.getInstance())

    val category1 = com.remindme.domain.model.Category(name = "Books", color = "#7CFC00")
    val category2 = com.remindme.domain.model.Category(name = "Reminders", color = "#FF00FF")


    val taskObject1 = TaskSearchItem(
        completed = true,
        title = "Call Me By Your Name",
        categoryColor = Color.Green,
        isRepeating = false,
        task = task1,
        category = category1
    )

    val taskObject2 = TaskSearchItem(
        completed = false,
        title = "The Crown",
        categoryColor = Color.White,
        isRepeating = true,
        task = task2,
        category = category2
    )

    val taskList = listOf(taskObject1, taskObject2)

    RemindMeTheme {
        SearchScaffold(
            modifier = Modifier,
            viewState = SearchViewState.Loaded(taskList),
            onItemClick = {},
            query = "",
            setQuery = {},
        )
    }
}

@Suppress("UndocumentedPublicFunction")
@Preview
@Composable
fun SearchEmptyListPreview(
) {
    RemindMeTheme {
        SearchScaffold(
            modifier = Modifier,
            viewState = SearchViewState.Empty,
            onItemClick = {},
            query = "",
            setQuery = {},
        )
    }
}
