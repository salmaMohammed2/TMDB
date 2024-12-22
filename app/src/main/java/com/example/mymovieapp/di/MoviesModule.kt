package com.example.mymovieapp.di

import com.example.mymovieapp.data.local.MoviesLocalDataSource
import com.example.mymovieapp.data.local.MoviesLocalDataSourceImpl
import com.example.mymovieapp.data.remote.MoviesRemoteDataSource
import com.example.mymovieapp.data.remote.MoviesRemoteDataSourceImpl
import com.example.mymovieapp.data.repo.DatabaseRepoImpl
import com.example.mymovieapp.data.repo.MoviesRepoImpl
import com.example.mymovieapp.domain.repo.DatabaseRepo
import com.example.mymovieapp.domain.repo.MoviesRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesModule {

    @Binds
    abstract fun bindRemoteDataSource(moviesRemoteDataSourceImpl: MoviesRemoteDataSourceImpl): MoviesRemoteDataSource

    @Binds
    abstract fun bindRemoteRepository(moviesRepoImpl: MoviesRepoImpl): MoviesRepo

    @Binds
    abstract fun bindLocalDataSource(moviesLocalDataSourceImpl: MoviesLocalDataSourceImpl): MoviesLocalDataSource

    @Binds
    abstract fun bindLocalRepository(databaseRepoImpl: DatabaseRepoImpl): DatabaseRepo
}