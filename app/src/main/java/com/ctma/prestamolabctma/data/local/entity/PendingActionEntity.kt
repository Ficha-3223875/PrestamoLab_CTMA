package com.ctma.prestamolabctma.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_actions")
data class PendingActionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val tipo: String,

    val solicitudId: Int,

    val estado: String,

    val fechaCreacion: Long = System.currentTimeMillis()
)