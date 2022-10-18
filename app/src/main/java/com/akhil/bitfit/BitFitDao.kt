package com.akhil.bitfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BitFitDao {
    @Query("SELECT * FROM bitfit_table")
    fun getAll(): Flow<List<BitFitEntity>>

    @Insert
    fun insert(bitFits: BitFitEntity)

    @Query("DELETE FROM bitfit_table")
    fun deleteAll()
}