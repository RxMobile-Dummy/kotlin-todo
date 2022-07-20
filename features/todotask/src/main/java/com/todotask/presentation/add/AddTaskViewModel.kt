package com.remindme.task.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remindme.domain.model.Task
import com.remindme.domain.usecase.task.AddTask
import com.remindme.task.presentation.detail.main.CategoryId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTask
) : ViewModel() {

    fun addTask(title: String, categoryId: CategoryId?) {
        if (title.isBlank()) return

        viewModelScope.launch {
            val task = Task(title = title, categoryId = categoryId?.value)
            addTaskUseCase.invoke(task)
        }
    }
}
