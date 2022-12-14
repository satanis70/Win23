package com.example.win23.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.win23.model.DatabaseModel

@androidx.room.Dao
interface Dao {
    @Query("SELECT * FROM tableRoom")
    fun getAll():List<DatabaseModel>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(databaseModel: DatabaseModel)
    @Delete()
    suspend fun delete(databaseModel: DatabaseModel)
    @Query("DELETE FROM tableRoom")
    suspend fun deleteDatabase()
    @Query("UPDATE tableRoom SET status=:status WHERE position LIKE:position")
    suspend fun update(status: String, position: Int)
    @Query("UPDATE tableRoom SET capital=:capital WHERE position LIKE:position")
    suspend fun updateCapital(capital:String, position: Int)
}