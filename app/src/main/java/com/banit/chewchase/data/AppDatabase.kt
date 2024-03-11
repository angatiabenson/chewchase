package com.banit.chewchase.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.banit.chewchase.data.dao.MenuDAO
import com.banit.chewchase.data.dao.OrderDAO
import com.banit.chewchase.data.dao.OrderFoodsDAO
import com.banit.chewchase.data.dao.UserDAO
import com.banit.chewchase.data.entity.Menu
import com.banit.chewchase.data.entity.Order
import com.banit.chewchase.data.entity.OrderFoods
import com.banit.chewchase.data.entity.Transaction
import com.banit.chewchase.data.entity.User

@Database(
    entities = [User::class, Order::class, OrderFoods::class, Transaction::class, Menu::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDAO
    abstract fun orderDAO(): OrderDAO
    abstract fun orderFoodsDAO(): OrderFoodsDAO
    abstract fun menuDAO(): MenuDAO
}
