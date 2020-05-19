package com.goldouble.android.gtomlconverter.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class], version = 2)
abstract class RecipeDB: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        private var INSTANCE: RecipeDB? = null

        fun getInstance(context: Context): RecipeDB? {
            if (INSTANCE == null) {
                synchronized(RecipeDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        RecipeDB::class.java, "recipe.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}