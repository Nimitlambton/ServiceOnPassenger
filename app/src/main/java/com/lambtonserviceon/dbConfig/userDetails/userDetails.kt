package com.lambtonserviceon.dbConfig.userDetails

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userDetailsTables")
class UserDetails(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="cardId") val UserId:Int,
    @ColumnInfo(name="FirstName") val FirstName:String,
    @ColumnInfo(name="LastName") val LastNmae:String,
    @ColumnInfo(name="UserImg") val UserImg:String

    )
