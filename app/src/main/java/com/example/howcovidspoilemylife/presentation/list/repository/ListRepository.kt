package com.example.howcovidspoilemylife.presentation.list.repository

import com.example.howcovidspoilemylife.core.dao.ProductsDao
import com.example.howcovidspoilemylife.core.models.ProductsDto
import javax.inject.Inject

class ListRepository @Inject constructor(private val dao: ProductsDao) {

    fun getBadProducts() = dao.selectAllProducts().sortedBy { it.time }
    fun deleteProduct(product: ProductsDto) {
        dao.deleteProduct(product)
    }
}