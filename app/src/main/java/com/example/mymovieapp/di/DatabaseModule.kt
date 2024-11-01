package com.example.mymovieapp.di

import android.content.Context
import androidx.room.Room
import com.example.mymovieapp.data.local.MoviesLocalDataSource
import com.example.mymovieapp.data.local.MoviesLocalDataSourceImpl
import com.example.mymovieapp.data.local.database.MoviesDao
import com.example.mymovieapp.data.local.database.MoviesDatabase
import com.example.mymovieapp.data.repo.DatabaseRepoImpl
import com.example.mymovieapp.domain.repo.DatabaseRepo
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

    @Singleton
    @Provides
    fun provideMoviesLocalDataSource(moviesDao: MoviesDao): MoviesLocalDataSource {
        return MoviesLocalDataSourceImpl(moviesDao)
    }

    @Singleton
    @Provides
    fun provideDatabaseRepo(moviesLocalDataSource: MoviesLocalDataSource): DatabaseRepo {
        return DatabaseRepoImpl(moviesLocalDataSource)
    }
}