package com.lambtonserviceon.dbConfig.userDetails

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lambtonserviceon.dbConfig.CardDetails.CardDetails

@Dao
interface userDetailsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend  fun insert(user: UserDetails )


    @Query("SELECT * from userDetailsTables")
    fun getalldata(): LiveData<List<UserDetails>>




}