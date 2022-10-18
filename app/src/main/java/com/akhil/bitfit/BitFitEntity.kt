package com.akhil.bitfit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bitfit_table")
class BitFitEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "foodName") val foodName: String?,
    @ColumnInfo(name = "calorieCount") val calorieCount: String?
)