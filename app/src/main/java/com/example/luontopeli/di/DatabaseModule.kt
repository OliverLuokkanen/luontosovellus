package com.example.luontopeli.di

import android.content.Context
import androidx.room.Room
import com.example.luontopeli.data.db.AppDatabase
import com.example.luontopeli.data.db.NatureSpotDao
import com.example.luontopeli.data.db.WalkSessionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "luontopeli_database"
        ).build()
    }

    @Provides
    fun provideWalkSessionDao(database: AppDatabase): WalkSessionDao = database.walkSessionDao()

    @Provides
    fun provideNatureSpotDao(database: AppDatabase): NatureSpotDao = database.natureSpotDao()
}
