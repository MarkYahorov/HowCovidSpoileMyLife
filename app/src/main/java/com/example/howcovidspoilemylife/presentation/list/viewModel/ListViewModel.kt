package com.example.howcovidspoilemylife.presentation.list.viewModel

import androidx.lifecycle.*
import com.example.howcovidspoilemylife.presentation.list.repository.ListRepository
import com.example.howcovidspoilemylife.presentation.mapper.toPresentation
import com.example.howcovidspoilemylife.presentation.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class ListViewModel(private val repository: ListRepository) : ViewModel() {

    private val _badProductLiveData = MutableLiveData<List<Product>>()
    val badProductLiveData: LiveData<List<Product>> = _badProductLiveData
    private val _goodProductLiveData = MutableLiveData<List<Product>>()
    val goodProductLiveData: LiveData<List<Product>> = _goodProductLiveData

    fun getBadProducts() {
        viewModelScope.launch (Dispatchers.IO){
            _badProductLiveData.postValue(repository.getBadProducts().map { it.toPresentation() })
        }
    }

    fun getGoodProducts() {
        viewModelScope.launch (Dispatchers.IO){
           _goodProductLiveData.postValue(repository.getGoodProducts().map { it.toPresentation() })
        }
    }

    class Factory @Inject constructor(private val repository: Provider<ListRepository>) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ListViewModel(repository.get()) as T
        }

    }
}