package com.example.howcovidspoilemylife.presentation.add.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.howcovidspoilemylife.presentation.add.repository.AddRepository
import com.example.howcovidspoilemylife.presentation.mapper.toData
import com.example.howcovidspoilemylife.presentation.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class AddViewModel(private val repository: AddRepository) : ViewModel() {

    fun insert(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProduct(product.toData())
        }
    }

    class Factory @Inject constructor(private val repository: Provider<AddRepository>) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AddViewModel(repository.get()) as T
        }

    }
}