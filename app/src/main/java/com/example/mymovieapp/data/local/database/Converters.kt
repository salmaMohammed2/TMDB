package com.example.mymovieapp.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromGenreIdsList(genreIds: List<Int>?): String? {
        return gson.toJson(genreIds)
    }

    @TypeConverter
    fun toGenreIdsList(genreIdsString: String?): List<Int>? {
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(genreIdsString, listType)
    }
}