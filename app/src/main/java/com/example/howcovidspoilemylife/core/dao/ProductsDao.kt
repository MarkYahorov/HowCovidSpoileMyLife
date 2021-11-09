package com.example.howcovidspoilemylife.core.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.howcovidspoilemylife.core.TABLE_NAME
import com.example.howcovidspoilemylife.core.models.ProductsDto

@Dao
interface ProductsDao {

    @Insert
    fun insertProducts(productsDto: ProductsDto)

    @Query("SELECT * FROM $TABLE_NAME ORDER BY id ASC")
    fun selectAllProducts(): List<ProductsDto>

    @Update
    fun updateProduct(productsDto: ProductsDto)

    @Delete
    fun deleteProduct(productsDto: ProductsDto)

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAll()
}