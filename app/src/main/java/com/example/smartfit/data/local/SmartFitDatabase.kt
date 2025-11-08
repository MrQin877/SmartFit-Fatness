package com.example.smartfit.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smartfit.data.model.ActivityLog

/**
 * The Room database for SmartFit.
 * Holds tables for activity logs and provides the DAO.
 */
@Database(
    entities = [
        ActivityLog::class,
        User::class // ✅ ADD THIS LINE
    ],
    version = 1,
    exportSchema = false
)
abstract class SmartFitDatabase : RoomDatabase() {

    abstract fun activityDao(): ActivityDao
    abstract fun userDao(): UserDao
    companion object {
        @Volatile
        private var INSTANCE: SmartFitDatabase? = null

        /**
         * Creates or returns a singleton database instance.
         * Safe for use in both runtime and preview environments.
         */
        fun create(context: Context): SmartFitDatabase {
            // Return existing instance if already created
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SmartFitDatabase::class.java,
                    "smartfit.db"
                )
                    .fallbackToDestructiveMigration() // clears DB on version change
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
