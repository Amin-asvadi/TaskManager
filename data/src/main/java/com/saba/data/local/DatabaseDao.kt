package com.saba.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Update
    suspend fun updateCategory(category:CategoryEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Query("""
    SELECT * FROM tasks 
    WHERE (:category IS NULL OR :category = '' OR category = :category) 
    ORDER BY title ASC
""")
    fun getAllTasks(category: String? = null): Flow<List<TaskEntity>>

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
