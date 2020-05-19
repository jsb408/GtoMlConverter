package com.goldouble.android.gtomlconverter.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAll(): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe)

    @Query("DELETE FROM recipe WHERE id = :saveId")
    fun deleteData(saveId: Long)

    @Query("DELETE FROM recipe")
    fun deleteAll()
}