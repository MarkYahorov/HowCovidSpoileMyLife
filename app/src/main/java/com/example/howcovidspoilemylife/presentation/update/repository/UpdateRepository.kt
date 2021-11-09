package com.example.howcovidspoilemylife.presentation.update.repository

import com.example.howcovidspoilemylife.core.dao.ProductsDao
import com.example.howcovidspoilemylife.core.models.ProductsDto
import javax.inject.Inject

class UpdateRepository @Inject constructor(private val dao: ProductsDao) {

    fun update(productsDto: ProductsDto) {
        dao.updateProduct(productsDto)
    }
}