package com.todotask.presentation.detail.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.remindme.R
import com.remindme.alarmapi.AlarmPermission
import com.remindme.categoryapi.model.Category
import com.remindme.categoryapi.presentation.CategoryListViewModel
import com.remindme.categoryapi.presentation.CategoryState
import com.remindme.core.view.DateTimePickerDialog
import com.remindme.designsystem.RemindMeTheme
import com.remindme.designsystem.components.RemindMeLoadingContent
import com.remindme.designsystem.components.RemindMeToolbar
import com.remindme.designsystem.components.DefaultIconTextContent
import com.todotask.model.Task
import com.todotask.presentation.category.CategorySelection
import com.todotask.presentation.category.editCategorySelection
import com.todotask.presentation.detail.LeadingIcon
import com.todotask.presentation.detail.TaskDetailActions
import com.todotask.presentation.detail.TaskDetailSectionContent
import com.todotask.presentation.detail.alarm.AlarmSelection
import com.todotask.presentation.detail.alarm.TaskAlarmViewModel
import com.todotask.presentation.detail.main.TaskDetailState
import com.todotask.presentation.detail.main.TaskDetailViewModel
import dagger.hilt.android.internal.migration.InjectedByHilt
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

/**
 * RemindMe Task Detail Section.
 *
 * @param taskId the id from the task to be shown
 * @param onUpPress action to be called when the back button is pressed
 */
@Composable
fun TaskDetailSection(
    taskId: Long,
    onUpPress: () -> Unit,
    alarmPermission: AlarmPermission,
    navController: NavController
) {
    TaskDetailLoader(
        taskId = taskId,
        onUpPress = onUpPress,
        alarmPermission,
        navController = navController
    )
}

@Suppress("LongParameterList")
@Composable
private fun TaskDetailLoader(
    taskId: Long,
    onUpPress: () -> Unit,
    alarmPermission: AlarmPermission,
    detailViewModel: TaskDetailViewModel = hiltViewModel(),
    categoryViewModel: CategoryListViewModel = hiltViewModel(),
    alarmViewModel: TaskAlarmViewModel = hiltViewModel(),
    navController: NavController
) {


    val id = TaskId(taskId)
    val detailViewState by
    remember(detailViewModel, taskId) { detailViewModel.loadTaskInfo(taskId = id) }
        .collectAsState(initial = TaskDetailState.Loading)

    val categoryViewState by
    remember(categoryViewModel, taskId) { categoryViewModel.loadCategories() }
        .collectAsState(initial = CategoryState.Loading)

    val taskDetailActions = TaskDetailActions(
        onTitleChange = { title -> detailViewModel.updateTitle(id, title) },
        onDescriptionChange = { desc -> detailViewModel.updateDescription(id, desc) },
        onCategoryChange = { categoryId -> detailViewModel.updateCategory(id, categoryId) },
        onAlarmUpdate = { calendar -> alarmViewModel.updateAlarm(id, calendar) },
        onIntervalSelect = { interval -> alarmViewModel.setRepeating(id, interval) },
        hasAlarmPermission = { alarmPermission.hasExactAlarmPermission() },
        onUpPress = onUpPress
    )

    TaskDetailRouter(
        detailViewState = detailViewState,
        categoryViewState = categoryViewState,
        actions = taskDetailActions,
        navController = navController
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun TaskDetailRouter(
    detailViewState: TaskDetailState,
    categoryViewState: CategoryState,
    actions: TaskDetailActions,
    navController: NavController
) {
    Scaffold(topBar = { RemindMeToolbar(onUpPress = actions.onUpPress) }) {
        Crossfade(detailViewState) { state ->
            when (state) {
                TaskDetailState.Loading -> RemindMeLoadingContent()
                TaskDetailState.Error -> TaskDetailError()
                is TaskDetailState.Loaded ->
                    TaskDetailContent(
                        task = state.task,
                        categoryViewState = categoryViewState,
                        actions = actions,
                        navController = navController
                    )
            }
        }
    }
}

@Composable
private fun TaskDetailContent(
    task: Task,
    categoryViewState: CategoryState,
    actions: TaskDetailActions,
    navController: NavController
) {
    var enabledEdittext by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Surface(color = MaterialTheme.colors.background) {
        Column {

            Card(
                elevation = 5.dp,
                backgroundColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 15.dp)
            ) {
                EditTaskTitleTextField(
                    text = task.title,
                    onTitleChange = actions.onTitleChange,
                    onEditClick = {
                        enabledEdittext = true;

                    },
                    enabledEdittext
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                elevation = 5.dp,
                backgroundColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 15.dp)
            ) {
                TaskDetailSectionContent(
                    imageVector = Icons.Outlined.Bookmark,
                    contentDescription = R.string.task_detail_cd_icon_category,
                ) {
                    editCategorySelection(
                        state = categoryViewState,
                        currentCategory = task.categoryId,
                        onCategoryChange = actions.onCategoryChange,
                        isEditTextEnabled = enabledEdittext
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            Card(
                elevation = 5.dp,
                backgroundColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 15.dp)
            ) {

                TaskDescriptionTextField(
                    text = task.description,
                    onDescriptionChange = actions.onDescriptionChange,
                    enabledEdittext
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            AlarmSelection(
                calendar = task.dueDate,
                interval = task.alarmInterval,
                onAlarmUpdate = actions.onAlarmUpdate,
                onIntervalSelect = actions.onIntervalSelect,
                hasAlarmPermission = actions.hasAlarmPermission
            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if(task.title.isNotEmpty() || task.description!!.isNotEmpty())
                    {
                        navController.popBackStack()
                    }else
                    {
                        Toast.makeText(context, "Please enter value in title or description field ", Toast.LENGTH_SHORT).show()

                    }
                     },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                modifier = Modifier.padding(horizontal = 15.dp)
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.update_task_detail),
                    color = MaterialTheme.colors.background
                )
            }

        }
    }
}

@Composable
private fun TaskDetailError() {
    DefaultIconTextContent(
        icon = Icons.Outlined.Close,
        iconContentDescription = R.string.task_detail_cd_error,
        header = R.string.task_detail_header_error
    )
}

@Composable
private fun TaskTitleTextField(text: String, onTitleChange: (String) -> Unit) {
    val textState = remember { mutableStateOf(TextFieldValue(text)) }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = textState.value,
        onValueChange = {
            onTitleChange(it.text)
            textState.value = it
        },
        textStyle = MaterialTheme.typography.h4,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
    )
}

@Composable
private fun EditTaskTitleTextField(
    text: String,
    onTitleChange: (String) -> Unit,
    onEditClick: () -> Unit,
    isEditTextEnabled: Boolean
) {
    val textState = remember { mutableStateOf(TextFieldValue(text)) }
      val isEdit = remember { mutableStateOf(isEditTextEnabled) }
    var title:String=""
    if (isEditTextEnabled){
        title = "Enter Title"
    }else{
        title = ""
    }
    var focusRequester = FocusRequester()
    val inputService = LocalTextInputService.current

    if(isEditTextEnabled){
      focusRequester = remember { FocusRequester() }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        TextField(
            modifier = Modifier
                .weight(2f).focusRequester(focusRequester),
            value = textState.value,
            enabled = isEditTextEnabled,
            onValueChange = {
                onTitleChange(it.text)
                textState.value = it
            },
            label = {  Text(text=title)},
            textStyle = MaterialTheme.typography.h4,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                textColor = Color.Black,focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        IconButton(onClick = onEditClick) {
            Icon(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
                    .clickable(onClick = onEditClick),
                imageVector = Icons.Outlined.Edit,
                tint = MaterialTheme.colors.secondary,
                contentDescription = stringResource(
                    id = R.string.edit_task_detail
                )
            )
        }

    }
    LaunchedEffect(Unit) {
        delay(300)
        inputService?.showSoftwareKeyboard()
        focusRequester.requestFocus()
    }
}

@Composable
private fun TaskDescriptionTextField(
    text: String?,
    onDescriptionChange: (String) -> Unit,
    isEditTextEnabled: Boolean
) {
    val textState = remember { mutableStateOf(TextFieldValue(text ?: "")) }
    var desc:String=""

    if(isEditTextEnabled){
        desc=" Enter Description"
    }else{
        desc=""
    }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            LeadingIcon(
                imageVector = Icons.Default.Description,
                contentDescription = R.string.task_detail_cd_icon_description
            )
        },
        label = { Text(desc) },
                value = textState.value,
        enabled = isEditTextEnabled,
        onValueChange = {
            onDescriptionChange(it.text)
            textState.value = it
        },
        textStyle = MaterialTheme.typography.body1,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface,focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent)
    )
}

@Suppress("ModifierOrder")
@JvmInline
@Parcelize
value class TaskId(val value: Long?) : Parcelable

@Suppress("ModifierOrder")
@JvmInline
@Parcelize
value class CategoryId(val value: Long?) : Parcelable

@Suppress("UndocumentedPublicFunction")
@Composable
fun TaskDetailPreview(navController: NavController) {
    val task = Task(title = "Buy milk", description = "This is a amazing task!", dueDate = null)
    val category1 = Category(name = "Groceries", color = android.graphics.Color.CYAN)
    val category2 = Category(name = "Books", color = android.graphics.Color.RED)
    val category3 = Category(name = "Movies", color = android.graphics.Color.MAGENTA)

    val categories = listOf(category1, category2, category3)

    RemindMeTheme {
        TaskDetailContent(
            task = task,
            categoryViewState = CategoryState.Loaded(categories),
            actions = TaskDetailActions(),
            navController = navController
        )
    }
}
