package com.banit.chewchase.utils

import com.banit.chewchase.data.entity.Menu
import com.banit.chewchase.data.models.CartItem

class CartManager {

    val cartItems: ArrayList<CartItem> = ArrayList()

    fun addMenuItemToCart(menu: Menu) {
        // Check if the menu item is already in the cart
        val existingItem = cartItems.find { it.menu.id == menu.id }

        if (existingItem != null) {
            // If the item exists, increment its quantity
            existingItem.quantity += 1
        } else {
            // Otherwise, add a new CartItem with quantity 1
            cartItems.add(CartItem(menu, 1))
        }
    }

    fun removeMenuItemFromCart(menu: Menu) {
        val existingItem = cartItems.find { it.menu.id == menu.id }

        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                // If quantity is more than 1, decrement its quantity
                existingItem.quantity -= 1
            } else {
                // If quantity is 1, remove the item entirely from the cart
                cartItems.remove(existingItem)
            }
        }
    }

    fun calculateSubtotal(): Double {
        var subtotal = 0.0
        for (cartItem in cartItems) {
            subtotal += cartItem.menu.price * cartItem.quantity
        }
        return subtotal
    }

    fun calculateTip(): Double {
        return calculateSubtotal() * 0.15 // 15% tip
    }

    fun calculateTotal(): Double {
        return calculateSubtotal() + calculateTip()
    }
}
