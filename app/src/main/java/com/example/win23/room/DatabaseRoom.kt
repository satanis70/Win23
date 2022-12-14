package com.example.win23.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.win23.model.DatabaseModel

@Database(entities = [DatabaseModel::class], version = 1)
abstract class DatabaseRoom : RoomDatabase() {
    abstract fun bettingDao(): Dao
    companion object {
        @Volatile
        private var INSTANSE: DatabaseRoom? = null
        fun getDatabase(context: Context): DatabaseRoom {
            val tempInstance = INSTANSE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseRoom::class.java,
                    "bet_database"
                ).build()
                INSTANSE = instance
                return instance
            }
        }
    }
}