package com.example.smartfit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "activity_logs")
data class ActivityLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,   // ✅ use String instead of LocalDate
    val type: String,
    val durationMin: Int? = null,
    val steps: Int? = null,
    val calories: Int? = null,
    val notes: String? = null,
    val sourceProvider: String? = null,
    val sourceId: String? = null,
    val sourceTitle: String? = null,
    val imageUrl: String? = null
)

