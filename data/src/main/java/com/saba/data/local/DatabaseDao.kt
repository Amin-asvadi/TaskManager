package com.saba.data.local

import androidx.room.*
import com.saba.data.model.local.CategoryEntity
import com.saba.data.model.local.RemoteModelItemEntity
import com.saba.data.model.local.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRemote(remoteModelItem: RemoteModelItemEntity): Long // Returns the row ID of the inserted item

    @Query("UPDATE tasks SET isReminderEnabled = :isReminderEnabled WHERE id = :taskId")
    suspend fun updateReminder(taskId: Int, isReminderEnabled: Boolean)

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Query("""
    SELECT * FROM tasks 
    WHERE (:category IS NULL OR :category = '' OR category = :category)
    AND (:searchQuery = '' OR title LIKE '%' || :searchQuery || '%' OR description LIKE '%' || :searchQuery || '%')
    ORDER BY title ASC
""")
    fun getAllTasks(category: String? = null, searchQuery: String = ""): Flow<List<TaskEntity>>

    @Query("""
    SELECT * FROM category_table 
    ORDER BY 
        CASE WHEN category = 'همه' THEN 0 ELSE 1 END, 
        category ASC
""")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): TaskEntity?

    @Query("SELECT * FROM category_table WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Int): CategoryEntity?
}
