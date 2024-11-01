package com.example.mymovieapp.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymovieapp.domain.entities.Movie

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Query("select * from movies_table")
    fun getAllMovies(): List<Movie>

    @Query("DELETE from movies_table WHERE id = :movieId")
    fun deleteMovie(movieId: Int)
}
