package com.saba.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saba.data.local.CategoryEntity
import com.saba.data.local.TaskEntity
import com.saba.domain.usecase.AddCategoryUseCase
import com.saba.domain.usecase.AddTaskUseCase
import com.saba.domain.usecase.GetCategoriesUseCase
import com.saba.domain.usecase.GetTaskByIdUseCase
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
class HomeViewModel @Inject constructor(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState
    private val _search = MutableStateFlow("")
    val search: StateFlow<String> = _search
    private val _categories = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val categories: StateFlow<List<CategoryEntity>> = _categories
    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks
    private val _addTaskEvent = MutableSharedFlow<Result<String>>()
    val addTaskEvent: SharedFlow<Result<String>> = _addTaskEvent

    init {
        fetchCategories()
        getAllTasks(category = null)
    }

    fun searchQuery(keyWord: String) {
        _search.value = keyWord
    }

    fun addToCategory(value: CategoryEntity) {
        viewModelScope.launch {
            addCategoryUseCase.execute(value)
        }
    }

    fun addTask(
    ) {
        val task =
            TaskEntity(
                title = _uiState.value.title,
                description = _uiState.value.description,
                category = "همه"
            )
        viewModelScope.launch {
            flow {
                emit(addTaskUseCase.execute(task)) // تسک را وارد دیتابیس می‌کنیم
            }.map {
                _uiState.value = _uiState.value.copy(dialogAddTask = false)
                getAllTasks(null)
                Result.success("Task created successfully")
            }.catch { e ->
                emit(Result.failure(Exception("Failed to create task: ${e.message}")))
            }.collect { result ->
                _addTaskEvent.emit(result)
            }
        }
    }

    private fun getAllTasks(category: String?) {
        viewModelScope.launch {
            getTasksUseCase.execute(category)
                .collect { taskList ->
                    _uiState.value = _uiState.value.copy(tasks = taskList)
                }
        }
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            getCategoriesUseCase.execute()
                .catch { exception ->
                    // Handle errors
                    Log.e("CategoriesViewModel", "Error fetching categories: ${exception.message}")
                }
                .collect { categoryList ->
                    _categories.value = categoryList
                }
        }
    }

    fun fetchTaskById(id: Int) {
        viewModelScope.launch {
            getTaskByIdUseCase.execute(id)
                .catch { exception ->
                    // Handle errors
                    Log.e("CategoriesViewModel", "Error fetching categories: ${exception.message}")
                }
                .collect { task ->
                    _uiState.value = _uiState.value.copy(
                        singleTask = task,
                        bottomSheeTitle = task?.title ?: "",
                        bottomSheetDescription = task?.description ?: ""
                    )
                }
        }
    }

    fun onCategoryClick(category: String) {
        _uiState.value = _uiState.value.copy(activeCategory = category)
        getAllTasks(category)
    }

    fun onValueChange(type: VALUE_CAHNGE_TYPE, value: String) {
        when (type) {
            VALUE_CAHNGE_TYPE.TITLE -> _uiState.value = _uiState.value.copy(title = value)
            VALUE_CAHNGE_TYPE.DESCRIPTION -> _uiState.value =
                _uiState.value.copy(description = value)

            VALUE_CAHNGE_TYPE.BOTTOM_SHEET_TITLE -> _uiState.value =
                _uiState.value.copy(bottomSheeTitle = value)

            else -> _uiState.value = _uiState.value.copy(bottomSheetDescription = value)
        }
    }

    fun showAddTaskDialog(state: Boolean) {
        _uiState.value = _uiState.value.copy(dialogAddTask = state)
    }

    fun bottomSheetState(state: Boolean) {
        _uiState.value = _uiState.value.copy(bottomSheetView = state)
    }

    enum class VALUE_CAHNGE_TYPE {
        TITLE,
        DESCRIPTION,
        BOTTOM_SHEET_TITLE,
        BOTTOM_SHEET_DESCRIPTION,
    }
}