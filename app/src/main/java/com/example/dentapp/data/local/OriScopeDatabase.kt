package com.example.dentapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dentapp.data.local.dao.AppointmentDao
import com.example.dentapp.data.local.dao.PatientDao
import com.example.dentapp.data.local.entity.AppointmentEntity
import com.example.dentapp.data.local.entity.PatientEntity
import com.example.dentapp.data.local.utils.Converters

@Database(
    entities = [PatientEntity::class, AppointmentEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class OriScopeDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun appointmentDao(): AppointmentDao

    companion object {
        @Volatile
        private var INSTANCE: OriScopeDatabase? = null

        fun getDatabase(context: Context): OriScopeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OriScopeDatabase::class.java,
                    "medical_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}