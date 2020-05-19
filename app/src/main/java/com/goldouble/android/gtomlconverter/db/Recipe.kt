package com.goldouble.android.gtomlconverter.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class Recipe (@PrimaryKey(autoGenerate = true) var id: Long? = null,
              @ColumnInfo(name = "name") var name: String = "",
              @ColumnInfo(name = "g") var g: Int = 0,
              @ColumnInfo(name = "ml") var ml: Int = 0)