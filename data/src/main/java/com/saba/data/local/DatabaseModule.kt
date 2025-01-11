package com.saba.data.local

import android.content.Context
import androidx.room.Room
import com.saba.base_android.uiles.Constant
import com.saba.base_android.uiles.Constant.DB_NAME
import com.saba.data.network.DbName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @DbName
    @Provides
    fun provideDbName(): String = Constant.DB_NAME

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context,@DbName dbName: String): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            dbName
        ).build()
    }

    @Provides
    fun provideTaskDao(database: AppDatabase): DatabaseDao {
        return database.taskDao()
    }
}