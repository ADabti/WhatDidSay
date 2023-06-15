package com.example.whatdidsay.DataBase

import androidx.room.*
import com.example.whatdidsay.Models.Hadith
import kotlinx.coroutines.flow.Flow

@Dao
interface HadithDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(hadith: Hadith)

    @Query("SELECT * FROM hadith_table WHERE keywords LIKE :searchQuery")
    fun searchHadiths(searchQuery: String): Flow<List<Hadith>>

    @Query("SELECT COUNT(*) FROM hadith_table")
    suspend fun count(): Int

    @Query("SELECT * FROM hadith_table")
    fun getAllHadiths(): Flow<List<Hadith>>

    @Query("DELETE FROM hadith_table")
    suspend fun deleteAllHadiths()


}

