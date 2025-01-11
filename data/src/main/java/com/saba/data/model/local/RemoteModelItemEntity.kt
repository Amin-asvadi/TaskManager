package com.saba.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.saba.data.model.RemoteModelItem

@Entity(tableName = "remote")
data class RemoteModelItemEntity(
    @PrimaryKey val id: Int,
    val body: String,
    val title: String,
    val userId: Int
)
fun RemoteModelItem.toEntity(): RemoteModelItemEntity {
    return RemoteModelItemEntity(
        id = this.id,
        body = this.body,
        title = this.title,
        userId = this.userId
    )
}
fun List<RemoteModelItem>.toEntityList(): List<RemoteModelItemEntity> {
    return this.map { it.toEntity() }
}