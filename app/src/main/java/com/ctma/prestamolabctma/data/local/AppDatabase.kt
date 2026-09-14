package com.ctma.prestamolabctma.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ctma.prestamolabctma.data.local.dao.EquipoDao
import com.ctma.prestamolabctma.data.local.dao.SolicitudDao
import com.ctma.prestamolabctma.data.local.entity.EquipoEntity
import com.ctma.prestamolabctma.data.local.entity.SolicitudEntity

@Database(
    entities = [
        EquipoEntity::class,
        SolicitudEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun equipoDao(): EquipoDao

    abstract fun solicitudDao(): SolicitudDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "prestamolab_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}