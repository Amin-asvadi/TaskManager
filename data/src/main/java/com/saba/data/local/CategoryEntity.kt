package com.saba.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Entity(tableName = "category_table", indices = [Index(value = ["category"], unique = true)])
@Serializable
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String,
)