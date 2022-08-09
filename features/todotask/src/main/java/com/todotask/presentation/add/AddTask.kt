package com.todotask.presentation.add

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.remindme.R
import com.remindme.categoryapi.presentation.CategoryListViewModel
import com.remindme.categoryapi.presentation.CategoryState
import com.remindme.designsystem.RemindMeTheme
import com.remindme.designsystem.components.RemindMeInputTextField
import com.remindme.designsystem.components.RemindMeToolbar
import com.remindme.task.presentation.add.AddTaskViewModel
import com.todotask.model.Task
import com.todotask.presentation.category.CategorySelection
import com.todotask.presentation.detail.TaskDetailActions
import com.todotask.presentation.detail.alarm.AlarmSelection
import com.todotask.presentation.detail.main.CategoryId
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * RemindMe Add Task Bottom Sheet.
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddTaskBottomSheet(onUpPress: () -> Unit,navController: NavController,task: Task,
                       actions: TaskDetailActions,) {
    Scaffold(
        topBar = { RemindMeToolbar(onUpPress = onUpPress) },
        content = {  AddTaskLoader(navController = navController, task = task, actions = actions) }
    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
 fun AddTaskLoader(
    addTaskViewModel: AddTaskViewModel = hiltViewModel(),
    categoryViewModel: CategoryListViewModel = hiltViewModel(),
   // onHideBottomSheet: () -> Unit
    navController: NavController,
    task: Task,
    actions: TaskDetailActions,

    ) {
    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize(),

    ) {
        var taskInputText by rememberSaveable { mutableStateOf("") }

        val categoryState by remember(categoryViewModel) { categoryViewModel }.loadCategories()
            .collectAsState(initial = CategoryState.Empty)
        var currentCategory by rememberSaveable { mutableStateOf<CategoryId?>(null) }
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            delay(FocusDelay)
           // focusRequester.requestFocus()
        }

        RemindMeInputTextField(
            label = stringResource(id = R.string.task_add_label),
            text = taskInputText,
            onTextChange = { text -> taskInputText = text },
            modifier = Modifier
                .fillMaxWidth()
                .focusable(false)
        )

        Spacer(modifier = Modifier.height(30.dp))

        CategorySelection(
            state = categoryState,
            currentCategory = currentCategory?.value,
            onCategoryChange = { categoryId -> currentCategory = categoryId }
        )
        Spacer(modifier = Modifier.height(30.dp))

        AlarmSelection(
            calendar = task.dueDate,
            interval = task.alarmInterval,
            onAlarmUpdate = actions.onAlarmUpdate,
            onIntervalSelect = actions.onIntervalSelect,
            hasAlarmPermission = actions.hasAlarmPermission
        )
        Spacer(modifier = Modifier.height(30.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = {
                addTaskViewModel.addTask(taskInputText, currentCategory)
                taskInputText = ""
                navController.popBackStack()
              //  onHideBottomSheet()
            }
        ) {
            Text(stringResource(id = R.string.task_add_save))
        }
    }
}

private const val FocusDelay = 300L

@OptIn(ExperimentalMaterialApi::class)
@Suppress("UndocumentedPublicFunction")
@Preview
@Composable
fun TaskListScaffoldError() {
    RemindMeTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(256.dp)
                .background(MaterialTheme.colors.background)
        ) {
          //  AddTaskBottomSheet()
        }
    }
}
