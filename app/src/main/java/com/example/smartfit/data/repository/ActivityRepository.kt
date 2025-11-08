package com.example.smartfit.data.repository

import com.example.smartfit.data.local.ActivityDao
import com.example.smartfit.data.model.ActivityLog
import kotlinx.coroutines.flow.Flow

class ActivityRepository(private val dao: ActivityDao) {
    fun getAllActivities(): Flow<List<ActivityLog>> = dao.getAll()
    fun getById(id: Long): Flow<ActivityLog?> = dao.getById(id)
    suspend fun insert(log: ActivityLog): Long = dao.insert(log)
    suspend fun update(log: ActivityLog) = dao.update(log)
    suspend fun delete(log: ActivityLog) = dao.delete(log)
}
