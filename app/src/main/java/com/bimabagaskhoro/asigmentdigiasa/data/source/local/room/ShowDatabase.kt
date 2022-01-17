package com.bimabagaskhoro.asigmentdigiasa.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bimabagaskhoro.asigmentdigiasa.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 3, exportSchema = false)
abstract class ShowDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var INSTANCE: ShowDatabase? = null

        fun getInstance(context: Context): ShowDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ShowDatabase::class.java,
                    "movie.db"
                ).fallbackToDestructiveMigration().build().apply { INSTANCE = this }
            }
    }
}