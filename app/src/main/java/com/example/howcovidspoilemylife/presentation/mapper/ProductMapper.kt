package com.example.howcovidspoilemylife.presentation.mapper

import com.example.howcovidspoilemylife.core.models.ProductsDto
import com.example.howcovidspoilemylife.presentation.models.Product

fun ProductsDto.toPresentation() = Product(id, name, isGoodProduct, time)
fun Product.toData() = ProductsDto(id,name, isGoodProduct, time)