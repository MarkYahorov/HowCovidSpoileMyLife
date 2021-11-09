package com.example.howcovidspoilemylife.core.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.howcovidspoilemylife.core.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ProductsDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "isGoodProduct") val isGoodProduct: Int,
    @ColumnInfo(name = "time") val time: Long
)