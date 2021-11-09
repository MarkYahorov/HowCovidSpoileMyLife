package com.example.howcovidspoilemylife.presentation.update.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.howcovidspoilemylife.presentation.mapper.toData
import com.example.howcovidspoilemylife.presentation.models.Product
import com.example.howcovidspoilemylife.presentation.update.repository.UpdateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class UpdateViewModel(private val repository: UpdateRepository) : ViewModel() {

    fun update(product: Product) {
        viewModelScope.launch (Dispatchers.IO){
            repository.update(product.toData())
        }
    }

    class Factory @Inject constructor(private val repository: Provider<UpdateRepository>) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UpdateViewModel(repository.get()) as T
        }

    }
}