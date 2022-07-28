package com.remindme.task.presentation.add

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remindme.R
import com.remindme.categoryapi.presentation.CategoryListViewModel
import com.remindme.categoryapi.presentation.CategoryState
import com.remindme.designsystem.RemindMeTheme
import com.remindme.designsystem.components.RemindMeInputTextField
import com.todotask.presentation.category.CategorySelection
import com.todotask.presentation.detail.main.CategoryId
import kotlinx.coroutines.delay

/**
 * RemindMe Add Task Bottom Sheet.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddTaskBottomSheet(onHideBottomSheet: () -> Unit) {
    AddTaskLoader(onHideBottomSheet = onHideBottomSheet,)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun AddTaskLoader(
    addTaskViewModel: AddTaskViewModel  = hiltViewModel(),
    categoryViewModel: CategoryListViewModel = hiltViewModel(),
    onHideBottomSheet: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        var taskInputText by rememberSaveable { mutableStateOf("") }
        val categoryState by remember(categoryViewModel) { categoryViewModel }.loadCategories()
            .collectAsState(initial = CategoryState.Empty)
        var currentCategory by rememberSaveable { mutableStateOf<CategoryId?>(null) }
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            delay(FocusDelay)
            focusRequester.requestFocus()
        }

        RemindMeInputTextField(
            label = stringResource(id = R.string.task_add_label),
            text = taskInputText,
            onTextChange = { text -> taskInputText = text },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )

        CategorySelection(
            state = categoryState,
            currentCategory = currentCategory?.value,
            onCategoryChange = { categoryId -> currentCategory = categoryId }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = {
                addTaskViewModel.addTask(taskInputText, currentCategory)
                taskInputText = ""
                onHideBottomSheet()
            }
        ) {
            Text(stringResource(id = R.string.task_add_save))
        }
    }
}

private const val FocusDelay = 300L

@OptIn(ExperimentalMaterialApi::class)
@Suppress("UndocumentedPublicFunction")
@Composable
fun TaskListScaffoldError(context: Context) {
    RemindMeTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(256.dp)
                .background(MaterialTheme.colors.background)
        ) {
            AddTaskBottomSheet(onHideBottomSheet = {})
        }
    }
}
