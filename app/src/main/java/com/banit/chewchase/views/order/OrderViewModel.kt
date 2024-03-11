package com.banit.chewchase.views.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.banit.chewchase.data.entity.Order
import com.banit.chewchase.data.entity.OrderFoods
import com.banit.chewchase.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val repository: OrderRepository) : ViewModel() {

    private val _orderPlaced = MutableLiveData<Boolean>()
    val orderPlaced: LiveData<Boolean>
        get() = _orderPlaced

    fun fetchMenuItemById(menuId: String) = liveData(Dispatchers.IO) {
        val value = repository.getMenuItemById(menuId)
        emit(value)
    }

    fun placeOrder(order: Order, orderFoods: List<OrderFoods>) = viewModelScope.launch {
        repository.saveOrderAndFoods(order, orderFoods)
        _orderPlaced.postValue(true)
    }
}