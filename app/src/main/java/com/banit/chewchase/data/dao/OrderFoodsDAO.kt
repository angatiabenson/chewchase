package com.banit.chewchase.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.banit.chewchase.data.entity.OrderFoods
import com.banit.chewchase.data.models.FoodDetailsFromOrder

@Dao
interface OrderFoodsDAO {

    @Transaction
    @Query("""
        SELECT 
            order_foods_table.orderFoodsOrderId,
            order_foods_table.quantity,
            menu_table.name,
            menu_table.description,
            menu_table.price FROM order_foods_table 
        INNER JOIN menu_table ON order_foods_table.menuItem = menu_table.id 
        WHERE order_foods_table.orderFoodsOrderId = :orderId
    """)
    suspend fun getFoodsWithDetailsFromOrder(orderId: Int): List<FoodDetailsFromOrder>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrderFood(orderFood: OrderFoods)


}
