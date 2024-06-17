package com.example.infinite_android_base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.infinite_android_base.database.Item
import com.example.infinite_android_base.database.ItemDao
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ItemViewModel(private val itemDao: ItemDao) : ViewModel() {

    fun queryAllItem(): Deferred<Flow<List<Item>>> {
        val items=viewModelScope.async(Dispatchers.IO) {
            itemDao.getItems()
        }
        return items
    }
    fun addNewItem(itemName: String, itemPassword: String) {
        val newItem = getNewItemEntry(itemName, itemPassword)
        viewModelScope.launch {
            itemDao.insert(newItem)
        }
    }

    private fun getNewItemEntry(itemName: String, itemPassword: String): Item {
        return Item(
            name = itemName,
            password = itemPassword
        )
    }

    fun deleteAllItem(){
        viewModelScope.launch(Dispatchers.IO) {
//            println("测试是否为主线程" + (Thread.currentThread() == Looper.getMainLooper().thread))
            itemDao.deleteAllItem()
        }
    }

    fun delete(item:Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class ItemViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItemViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}