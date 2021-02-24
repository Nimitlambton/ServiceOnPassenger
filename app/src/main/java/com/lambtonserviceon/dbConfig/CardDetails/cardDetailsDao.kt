package com.lambtonserviceon.dbConfig.CardDetails


import androidx.lifecycle.LiveData
import androidx.room.*
import com.lambtonserviceon.dbConfig.CardDetails.CardDetails


@Dao
interface CardDetailsDao {


   @Insert()
   suspend  fun insert(card: CardDetails)


   @Query("SELECT * from carddetailstable")
   fun getalldata(): LiveData<List<CardDetails>>



   //Delete all data
   @Query("DELETE FROM carddetailstable")
   suspend fun deleteAll()




   @Update
   suspend fun update(card : CardDetails)


}