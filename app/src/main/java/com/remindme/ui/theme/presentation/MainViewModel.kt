package com.remindme.presentation

import androidx.lifecycle.ViewModel
import com.remindme.presentation.mapper.AppThemeOptionsMapper
import com.remindme.presentation.model.AppThemeOptions
import com.remindme.domain.usecase.preferences.LoadAppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadAppTheme: LoadAppTheme,
    private val mapper: AppThemeOptionsMapper
) : ViewModel() {

     fun loadCurrentTheme(): Flow<AppThemeOptions> = loadAppTheme().map { mapper.toViewData(it) }
}
