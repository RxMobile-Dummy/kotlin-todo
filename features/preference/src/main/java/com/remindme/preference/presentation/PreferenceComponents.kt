package com.remindme.preference.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.remindme.preference.R
import com.remindme.preference.localData.NotificationImpl
import com.todotask.model.AlarmInterval
import com.todotask.presentation.detail.TaskDetailActions
import androidx.lifecycle.lifecycleScope
import com.remindme.preference.localData.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
internal  fun NotificationPreferenceItem(
    title: String,
    description: String? = null,
    onItemClick: () -> Unit = { },
    change: Boolean, onCheckedChange: (Boolean) -> Unit,    prefsDataStore: DataStore<Preferences>
) {
    val mCheckedState = remember { mutableStateOf(false) }
    val coroutineScope: CoroutineScope = MainScope()
    var notificationImpl: Notification? = null
    notificationImpl = NotificationImpl(prefsDataStore)

    coroutineScope.launch {
        notificationImpl.getNotificationState().collect {state->
            mCheckedState.value = state;
            Log.e("value",state.toString())
        }
    }
    Column(
        modifier = Modifier
            .clickable { onItemClick() }
            .padding(start = 32.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxWidth()
            .height(48.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f)
            )
            if (description != null) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.body2
                )
            }
            Switch(
                checked = mCheckedState.value,
                onCheckedChange = {
                    mCheckedState.value = it
                    if (it) {
                        coroutineScope.launch {
                            notificationImpl.saveNotificationState(it)

                        }
                    } else {
                        coroutineScope.launch {
                            notificationImpl.saveNotificationState(it)
                        }

                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Blue,
                    uncheckedThumbColor = Color.LightGray,
                    checkedTrackColor = Color.Blue,
                    uncheckedTrackColor = Color.LightGray
                )
            )
        }
    }
}



@Composable
internal fun PreferenceItem(
    title: String,
    description: String? = null,
    onItemClick: () -> Unit = { },

    ) {
    Column(
        modifier = Modifier
            .clickable { onItemClick() }
            .padding(start = 32.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxWidth()
            .height(48.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1
        )
        if (description != null) {
            Text(
                text = description,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
internal fun PreferenceAlertSettingItem(
    title: String,
    description: String? = null,
    onItemClick: () -> Unit = { },
    actions: TaskDetailActions

) {
    Column(
        modifier = Modifier
            .clickable { onItemClick() }
            .padding(start = 32.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1
        )
        if (description != null) {
            Text(
                text = description,
                style = MaterialTheme.typography.body2
            )
        }
        AlertSettings(onIntervalSelect = actions.onIntervalSelect)
    }
}

@Composable
private fun AlertSettings(
    onIntervalSelect: (com.todotask.model.AlarmInterval) -> Unit
) {
    val intervalList = stringArrayResource(id = R.array.task_alarm_repeating)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(intervalList[0]) }
    Column {
        repeat(intervalList.size) { index ->
            AlarmListItem(
                title = intervalList.get(index),
                index = index,
                onIntervalSelect = onIntervalSelect,
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected
            )
        }


    }
}

@Composable
private fun AlarmListItem(
    title: String,
    index: Int,
    onIntervalSelect: (AlarmInterval) -> Unit,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = title == selectedOption,
                onClick = {
                    onOptionSelected(title)
                }
            )
    ) {
        RadioButton(
            selected = title == selectedOption,
            onClick = {
                onOptionSelected(title)

            }
        )

        Text(
            text = title,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .clickable {
                    val interval =
                        AlarmInterval
                            .values()
                            .find { it.index == index } ?: AlarmInterval.NEVER
                    onIntervalSelect(interval)
                },
        )
    }

}
