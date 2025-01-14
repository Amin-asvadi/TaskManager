package com.saba.domain

import com.saba.data.model.local.TaskEntity
import com.saba.data.repository.local.TaskRepository
import com.saba.domain.usecase.AddTaskUseCase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddTaskUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var addTaskUseCase: AddTaskUseCase

    @Before
    fun setUp() {
        taskRepository = mockk(relaxed = true)
        addTaskUseCase = AddTaskUseCase(taskRepository)
    }

    @Test
    fun `execute should call addTask on repository`() = runTest {
        val task = TaskEntity(
            title = "Test Task",
            description = "This is a test task",
            category = "Test",
            reminderTime = "2025-01-12T10:00:00",
            isReminderEnabled = true,
            markAsDone = false
        )

        addTaskUseCase.execute(task)

        coVerify { taskRepository.addTask(task) }
    }
}
