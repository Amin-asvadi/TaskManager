package com.saba.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class RemoteModelItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)