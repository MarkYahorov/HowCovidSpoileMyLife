package com.example.howcovidspoilemylife.core.di

import android.content.Context
import androidx.room.Room
import com.example.howcovidspoilemylife.core.dao.ProductsDao
import com.example.howcovidspoilemylife.core.dataBase.DataBase
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    @AppScope
    fun provideDb(context:Context): DataBase {
        return Room.databaseBuilder(context, DataBase::class.java, "covid_db")
            .build()
    }

    @Provides
    @AppScope
    fun provideDao(dataBase: DataBase): ProductsDao {
        return dataBase.productsDao()
    }
}