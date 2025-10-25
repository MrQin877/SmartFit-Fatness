package com.example.smartfit.data.local

import androidx.room.*
import com.example.smartfit.data.model.ActivityLog
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Query("SELECT * FROM activity_logs ORDER BY date DESC")
    fun getAll(): Flow<List<ActivityLog>>

    @Query("SELECT * FROM activity_logs WHERE id = :id")
    fun getById(id: Long): Flow<ActivityLog?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: ActivityLog): Long

    @Update
    suspend fun update(log: ActivityLog)

    @Delete
    suspend fun delete(log: ActivityLog)
}
