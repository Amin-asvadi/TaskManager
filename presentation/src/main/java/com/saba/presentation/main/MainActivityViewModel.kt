package com.saba.presentation.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saba.base_android.base_classes.AsyncResult
import com.saba.base_android.base_classes.Success
import com.saba.base_android.base_classes.Uninitialized
import com.saba.base_android.network.NetworkErrorData
import com.saba.base_android.network.NetworkErrorHandler
import com.saba.data.repositoryimp.local.PreferencesStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    networkErrorHandler: NetworkErrorHandler,
    private val preferencesStorage: PreferencesStorage
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainActivityUiState(darkMode =  preferencesStorage.isDarkMode()))
    val uiState: StateFlow<MainActivityUiState> = _uiState
    val _networkErrorState: MutableState<AsyncResult<NetworkErrorData>> =
        mutableStateOf(Uninitialized)
    val networkErrorState: State<AsyncResult<NetworkErrorData>> = _networkErrorState
    init {
        networkHandler(networkErrorHandler.event)
    }
    fun changeTheme(state: Boolean) {
        _uiState.value = _uiState.value.copy(darkMode = state)
        viewModelScope.launch {
            preferencesStorage.darkMode(state)
        }

    }
    fun networkHandler(networkFlow: SharedFlow<NetworkErrorData>) {
        viewModelScope.launch {
            networkFlow.collect {
                _networkErrorState.value = Success(it)
                delay(3000)
                _networkErrorState.value = Uninitialized
            }
        }
    }

}