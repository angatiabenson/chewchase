package com.banit.chewchase.views.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.banit.chewchase.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: OrderRepository) : ViewModel() {

    fun fetchOrdersForUser(userId: Int) = liveData(Dispatchers.IO) {
        val orders = repository.getAllOrdersForUser(userId)
        emit(orders)
    }
}