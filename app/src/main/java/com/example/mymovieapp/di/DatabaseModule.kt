package com.example.mymovieapp.di

import android.content.Context
import androidx.room.Room
import com.example.mymovieapp.data.local.database.MoviesDao
import com.example.mymovieapp.data.local.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMoviesDatabase(@ApplicationContext context: Context?): MoviesDatabase {
        return Room.databaseBuilder(context!!, MoviesDatabase::class.java, "movies_database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesDao(moviesDatabase: MoviesDatabase): MoviesDao {
        return moviesDatabase.getMoviesDao()
    }
}