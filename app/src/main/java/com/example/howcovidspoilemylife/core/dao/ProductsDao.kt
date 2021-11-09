package com.example.howcovidspoilemylife.core.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.howcovidspoilemylife.core.TABLE_NAME
import com.example.howcovidspoilemylife.core.models.ProductsDto

@Dao
interface ProductsDao {

    @Insert
    fun insertProducts(productsDto: ProductsDto)

    @Query("SELECT * FROM $TABLE_NAME  WHERE isGoodProduct = 0")
    fun selectAllGoodProducts(): List<ProductsDto>

    @Query("SELECT * FROM $TABLE_NAME  WHERE isGoodProduct = 1")
    fun selectAllBadProducts(): List<ProductsDto>

    @Update
    fun updateProduct(productsDto: ProductsDto)

    @Delete
    fun deleteProduct(productsDto: ProductsDto)

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAll()
}