package com.lambtonserviceon.dbConfig.userDetails

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "userDetailsTables") @Parcelize
class UserDetails(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="UserId") val UserId:Int,
    @ColumnInfo(name="FirstName") val FirstName:String,
    @ColumnInfo(name="LastName") val LastNmae:String,
    @ColumnInfo(name="Email") val Email:String,
    @ColumnInfo(name="Password") val Password:String,
    @ColumnInfo(name="UserImg") val UserImg:String

    ) : Parcelable
