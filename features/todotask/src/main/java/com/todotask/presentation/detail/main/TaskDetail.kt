package com.todotask.presentation.detail.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import androidx.compose.animation.Crossfade
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
fun TaskDetailSection(taskId: Long, onUpPress: () -> Unit,alarmPermission: AlarmPermission) {
    TaskDetailLoader(taskId = taskId, onUpPress = onUpPress,alarmPermission)
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
        onCategoryChange = { categoryId -> detailViewModel.updateCategory(id, categoryId,) },
        onAlarmUpdate = { calendar -> alarmViewModel.updateAlarm(id, calendar) },
        onIntervalSelect = { interval -> alarmViewModel.setRepeating(id, interval) },
        hasAlarmPermission = { alarmPermission.hasExactAlarmPermission() },
        onUpPress = onUpPress
    )

    TaskDetailRouter(
        detailViewState = detailViewState,
        categoryViewState = categoryViewState,
        actions = taskDetailActions
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun TaskDetailRouter(
    detailViewState: TaskDetailState,
    categoryViewState: CategoryState,
    actions: TaskDetailActions
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
                        actions = actions
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
) {
    var enabledEdittext by remember { mutableStateOf(false)}
    Surface(color = MaterialTheme.colors.background) {
        Column {
            EditTaskTitleTextField(text = task.title, onTitleChange = actions.onTitleChange, onEditClick = {
                enabledEdittext = true;
            },enabledEdittext)
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
            TaskDescriptionTextField(
                text = task.description,
                onDescriptionChange = actions.onDescriptionChange,
                enabledEdittext
            )
            AlarmSelection(
                calendar = task.dueDate,
                interval = task.alarmInterval,
                onAlarmUpdate = actions.onAlarmUpdate,
                onIntervalSelect = actions.onIntervalSelect,
                hasAlarmPermission = actions.hasAlarmPermission
            )
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
private fun EditTaskTitleTextField(text: String, onTitleChange: (String) -> Unit,onEditClick: () -> Unit,isEditTextEnabled:Boolean) {
    val textState = remember { mutableStateOf(TextFieldValue(text)) }
  //  val isEdit = remember { mutableStateOf(isEditTextEnabled) }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment= Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
    ){
        TextField(
            modifier = Modifier.weight(1f),
            value = textState.value,
            enabled = isEditTextEnabled,
            onValueChange = {
                onTitleChange(it.text)
                textState.value = it
            },

            textStyle = MaterialTheme.typography.h4,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
        )
        IconButton(onClick = onEditClick) {
            Icon(
                modifier = Modifier.weight(1f).padding(10.dp).clickable (onClick = onEditClick),
                imageVector = Icons.Outlined.Edit,
                tint = MaterialTheme.colors.secondary,
                contentDescription = stringResource(
                    id = R.string.edit_task_detail
                )
            )
        }

    }

}

@Composable
private fun TaskDescriptionTextField(text: String?, onDescriptionChange: (String) -> Unit,isEditTextEnabled:Boolean) {
    val textState = remember { mutableStateOf(TextFieldValue(text ?: "")) }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            LeadingIcon(
                imageVector = Icons.Default.Description,
                contentDescription = R.string.task_detail_cd_icon_description
            )
        },
        value = textState.value,
        enabled = isEditTextEnabled,
        onValueChange = {
            onDescriptionChange(it.text)
            textState.value = it
        },
        textStyle = MaterialTheme.typography.body1,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
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
@Preview
@Composable
fun TaskDetailPreview() {
    val task = Task(title = "Buy milk", description = "This is a amazing task!", dueDate = null)
    val category1 = Category(name = "Groceries", color = android.graphics.Color.CYAN)
    val category2 = Category(name = "Books", color = android.graphics.Color.RED)
    val category3 = Category(name = "Movies", color = android.graphics.Color.MAGENTA)

    val categories = listOf(category1, category2, category3)

    RemindMeTheme {
        TaskDetailContent(
            task = task,
            categoryViewState = CategoryState.Loaded(categories),
            actions = TaskDetailActions()
        )
    }
}
