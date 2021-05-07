package com.edhou.taskmaster.di

import android.content.Context
import com.edhou.taskmaster.db.AppDatabase
import com.edhou.taskmaster.db.TasksDao
import com.edhou.taskmaster.db.TeamsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideTasksDao(appDatabase: AppDatabase): TasksDao {
        return appDatabase.tasksDao()
    }

//    @Provides
//    fun provideGardenPlantingDao(appDatabase: AppDatabase): TeamsDao {
//        return appDatabase.teamsDao()
//    }
}