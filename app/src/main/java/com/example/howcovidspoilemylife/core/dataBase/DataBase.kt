package com.example.howcovidspoilemylife.core.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.howcovidspoilemylife.core.dao.ProductsDao
import com.example.howcovidspoilemylife.core.models.ProductsDto

@Database(entities = [ProductsDto::class], version = 1, exportSchema = false)
abstract class DataBase: RoomDatabase() {

    abstract fun productsDao(): ProductsDao
}