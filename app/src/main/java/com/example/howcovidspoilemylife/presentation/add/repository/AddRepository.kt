package com.example.howcovidspoilemylife.presentation.add.repository

import com.example.howcovidspoilemylife.core.dao.ProductsDao
import com.example.howcovidspoilemylife.core.models.ProductsDto
import javax.inject.Inject

class AddRepository @Inject constructor(private val dao: ProductsDao) {

    fun addProduct(product: ProductsDto) {
        dao.insertProducts(product)
    }
}