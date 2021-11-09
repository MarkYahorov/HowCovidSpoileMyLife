package com.example.howcovidspoilemylife.core.di

import android.content.Context
import com.example.howcovidspoilemylife.core.dao.ProductsDao
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
@AppScope
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun getProductsDao(): ProductsDao
}