package com.example.howcovidspoilemylife.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val isGoodProduct: Int,
    val time: Long
):Parcelable
