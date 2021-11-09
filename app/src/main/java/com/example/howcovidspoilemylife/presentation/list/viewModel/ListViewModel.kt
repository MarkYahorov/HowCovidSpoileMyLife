package com.example.howcovidspoilemylife.presentation.list.viewModel

import androidx.lifecycle.*
import com.example.howcovidspoilemylife.presentation.list.repository.ListRepository
import com.example.howcovidspoilemylife.presentation.mapper.toData
import com.example.howcovidspoilemylife.presentation.mapper.toPresentation
import com.example.howcovidspoilemylife.presentation.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class ListViewModel(private val repository: ListRepository) : ViewModel() {

    private val _productLiveData = MutableLiveData<List<Common>>()
    val productLiveData: LiveData<List<Common>> = _productLiveData

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = mutableListOf<Common>()
            val productList = repository.getBadProducts().map { it.toPresentation() }
            productList.forEachIndexed { index, product ->
                if (index == 0 || product.time != productList[index - 1].time) {
                    list.add(Common(1, product, product.time))
                }
                list.add(Common(0, product, null))
            }
            _productLiveData.postValue(list)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteProduct(product.toData())
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

data class Common(
    val type: Int,
    val product: Product,
    val data: Long? = null,
)