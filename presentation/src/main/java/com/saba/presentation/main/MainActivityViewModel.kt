package com.saba.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saba.data.local.CategoryEntity
import com.saba.data.local.TaskEntity
import com.saba.domain.usecase.AddCategoryUseCase
import com.saba.domain.usecase.AddTaskUseCase
import com.saba.domain.usecase.GetCategoriesUseCase
import com.saba.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainActivityUiState())
    val uiState: StateFlow<MainActivityUiState> = _uiState

    fun changeTheme(state: Boolean) {
        _uiState.value = _uiState.value.copy(darkMode = state)
    }
}