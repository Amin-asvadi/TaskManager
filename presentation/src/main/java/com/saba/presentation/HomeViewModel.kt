package com.saba.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saba.base_android.base_classes.AsyncResult
import com.saba.base_android.base_classes.Uninitialized
import com.saba.base_android.network.NetworkErrorData
import com.saba.base_android.network.NetworkErrorHandler
import com.saba.base_android.uiles.convertToLocalDateTime
import com.saba.base_android.uiles.toFormattedString
import com.saba.data.local.CategoryEntity
import com.saba.data.local.TaskEntity
import com.saba.domain.usecase.AddCategoryUseCase
import com.saba.domain.usecase.AddTaskUseCase
import com.saba.domain.usecase.DeleteTaskUseCase
import com.saba.domain.usecase.GetCategoriesUseCase
import com.saba.domain.usecase.GetTaskByIdUseCase
import com.saba.domain.usecase.GetTasksUseCase
import com.saba.domain.usecase.UpdateTaskReminderUseCase
import com.saba.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val updateTaskReminderUseCase: UpdateTaskReminderUseCase,
    private val networkErrorHandler: NetworkErrorHandler,
    private val deleteTaskUseCase: DeleteTaskUseCase,

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
                emit(addTaskUseCase.execute(task))
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

    fun updateTask(
    ) {
        val reminderTime =
            convertToLocalDateTime(_uiState.value.datePicker, _uiState.value.timePicker)
        val task =
            TaskEntity(
                id = _uiState.value.taskId,
                title = _uiState.value.bottomSheeTitle,
                description = _uiState.value.bottomSheetDescription,
                category = _uiState.value.taskCategory,
                reminderTime = reminderTime?.toFormattedString(),
                isReminderEnabled = if (_uiState.value.datePicker != null && _uiState.value.timePicker != null) true else false,
                markAsDone = false

            )
        viewModelScope.launch {
            flow {
                emit(updateTaskUseCase.execute(task))
            }.map {
                _uiState.value = _uiState.value.copy(bottomSheetView = false)
                val error = NetworkErrorData(
                    isError = false,
                    text = "بروزرسانی شد"
                )
                networkErrorHandler.emitEvent(error)
                getAllTasks(null)
                Result.success("Task created successfully")
            }.catch { e ->
                emit(Result.failure(Exception("Failed to create task: ${e.message}")))
            }.collect { result ->
                _addTaskEvent.emit(result)
            }
        }
    }

    fun updateReminder(
        id: Int,
        reminder: Boolean
    ) {
        viewModelScope.launch {
            getTaskByIdUseCase.execute(id)
                .catch { exception ->
                    Log.e("CategoriesViewModel", "Error fetching categories: ${exception.message}")
                }
                .collect { task ->
                    if ((task?.reminderTime ?: "").isEmpty()) {
                        val error = NetworkErrorData(
                            isError = true,
                            text = "ابتدا زمان را یاداوری را تنطیم کنید"
                        )
                        networkErrorHandler.emitEvent(error)
                    } else {
                        flow {
                            emit(
                                updateTaskReminderUseCase.execute(
                                    taskId = id,
                                    reminder = reminder
                                )
                            )
                        }.map {
                            getAllTasks(null)
                            Result.success("Task created successfully")
                        }.catch { e ->
                            emit(Result.failure(Exception("Failed to create task: ${e.message}")))
                        }.collect { result ->
                            _addTaskEvent.emit(result)
                        }
                    }
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun getAllTasks(category: String?) {
        viewModelScope.launch {
            combine(
                _search.debounce(100).distinctUntilChanged(),
                flowOf(category)
            ) { searchQuery, category ->
                getTasksUseCase.execute(
                    if (category == "همه") null else category,
                    searchQuery = searchQuery
                )
            }.flatMapLatest { it }
                .collect { taskList ->
                    _uiState.value = _uiState.value.copy(tasks = taskList)
                }
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            delay(300)
            flow {
                emit(deleteTaskUseCase.execute(task))
            }.map {
                val error = NetworkErrorData(
                    isError = false,
                    text = "آینم حذف شد"
                )
                networkErrorHandler.emitEvent(error)
                Result.success("Task created successfully")
            }.catch { e ->
                emit(Result.failure(Exception("Failed to create task: ${e.message}")))
            }.collect { result ->
                _addTaskEvent.emit(result)
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
                    Log.e("CategoriesViewModel", "Error fetching categories: ${exception.message}")
                }
                .collect { task ->
                    _uiState.value = _uiState.value.copy(
                        singleTask = task,
                        bottomSheeTitle = task?.title ?: "",
                        bottomSheetDescription = task?.description ?: "",
                        taskId = task?.id ?: -1,
                        isReminderEnabled = task?.isReminderEnabled ?: false,
                        timePicker = task?.reminderTime?.split(" ")?.get(1) ?: "",
                        datePicker = task?.reminderTime?.split(" ")?.get(0) ?: ""
                    )
                }
        }
    }

    fun onCategoryClick(category: String) {
        _uiState.value = _uiState.value.copy(activeCategory = category)
        getAllTasks(category)
    }

    fun changeremindeStatus(state: Boolean) {
        _uiState.value = _uiState.value.copy(isReminderEnabled = state)
        updateTask()
    }

    fun onValueChange(type: VALUE_CAHNGE_TYPE, value: String) {
        when (type) {
            VALUE_CAHNGE_TYPE.TITLE -> _uiState.value = _uiState.value.copy(title = value)
            VALUE_CAHNGE_TYPE.DESCRIPTION -> _uiState.value =
                _uiState.value.copy(description = value)

            VALUE_CAHNGE_TYPE.BOTTOM_SHEET_TITLE -> _uiState.value =
                _uiState.value.copy(bottomSheeTitle = value)

            VALUE_CAHNGE_TYPE.DATE -> {
                _uiState.value = _uiState.value.copy(datePicker = value)
            }

            VALUE_CAHNGE_TYPE.TIME -> {
                _uiState.value = _uiState.value.copy(timePicker = value)
            }

            VALUE_CAHNGE_TYPE.CATEGORY -> {
                _uiState.value = _uiState.value.copy(taskCategory = value)
            }

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
        DATE,
        TIME,
        CATEGORY
    }
}