package com.banit.chewchase.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.banit.chewchase.data.entity.Order
import com.banit.chewchase.data.models.UserOrdersWithFoods

@Dao
interface OrderDAO {

    @Transaction
    @Query("""
        SELECT * FROM order_table WHERE userId = :userId
    """)
    suspend fun getUserOrdersWithFoods(userId: Int): List<UserOrdersWithFoods>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrder(order: Order): Long // Returns the new rowId for the inserted item




}
