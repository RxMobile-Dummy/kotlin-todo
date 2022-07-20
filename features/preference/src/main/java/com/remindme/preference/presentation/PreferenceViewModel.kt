package com.remindme.preference.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remindme.domain.usecase.preferences.LoadAppTheme
import com.remindme.domain.usecase.preferences.UpdateAppTheme
import com.remindme.preference.AppThemeOptionsMapper
import com.remindme.preference.model.AppThemeOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferenceViewModel @Inject constructor(
    private val updateThemeUseCase: UpdateAppTheme,
    private val loadAppTheme: LoadAppTheme,
    private val mapper: AppThemeOptionsMapper
) : ViewModel() {

    fun loadCurrentTheme(): Flow<AppThemeOptions> = loadAppTheme().map { mapper.toViewData(it) }

    fun updateTheme(theme: AppThemeOptions) = viewModelScope.launch {
        val updatedTheme = mapper.toDomain(theme)
        updateThemeUseCase(updatedTheme)
    }
}
