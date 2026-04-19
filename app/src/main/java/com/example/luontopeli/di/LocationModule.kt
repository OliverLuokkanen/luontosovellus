package com.example.luontopeli.di

import android.content.Context
import com.example.luontopeli.location.LocationHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Provides
    @Singleton
    fun provideLocationHelper(@ApplicationContext context: Context): LocationHelper =
        LocationHelper(context)
}
