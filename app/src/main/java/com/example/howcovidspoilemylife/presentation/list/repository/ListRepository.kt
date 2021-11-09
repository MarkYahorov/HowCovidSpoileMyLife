package com.example.howcovidspoilemylife.presentation.list.repository

import com.example.howcovidspoilemylife.core.dao.ProductsDao
import com.example.howcovidspoilemylife.presentation.mapper.toPresentation
import javax.inject.Inject

class ListRepository @Inject constructor(private val dao: ProductsDao) {

    fun getBadProducts() = dao.selectAllBadProducts()
    fun getGoodProducts() = dao.selectAllGoodProducts()
}