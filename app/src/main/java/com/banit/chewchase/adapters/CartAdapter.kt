package com.banit.chewchase.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.banit.chewchase.R
import com.banit.chewchase.data.entity.Menu
import com.banit.chewchase.data.models.CartItem
import com.banit.chewchase.utils.formatCurrency

class CartAdapter(private val cartItems: ArrayList<CartItem>, private val isView: Boolean = true) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    lateinit var onQuantityChangeListener: OnQuantityChangeListener

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtQty: TextView = itemView.findViewById(R.id.txt_qty)
        private val txtName: TextView = itemView.findViewById(R.id.txt_name)
        private val txtDescription: TextView = itemView.findViewById(R.id.txt_description)
        private val txtAmount: TextView = itemView.findViewById(R.id.txt_amount)
        private val txtDecrement: TextView = itemView.findViewById(R.id.txt_remove)
        private val txtIncrement: TextView = itemView.findViewById(R.id.txt_add)

        @SuppressLint("SetTextI18n")
        fun bind(cartItem: CartItem) {
            txtQty.text = "${cartItem.quantity} x"
            txtName.text = cartItem.menu.name
            txtDescription.text = cartItem.menu.description
            txtAmount.text =
                "$${formatCurrency((cartItem.menu.price * cartItem.quantity).toString())}"
            txtDecrement.setOnClickListener {
                onQuantityChangeListener.onQuantityChange(cartItem.menu, false)
            }
            txtIncrement.setOnClickListener {
                onQuantityChangeListener.onQuantityChange(cartItem.menu, true)
            }

            if (!isView){
                txtDecrement.visibility = View.GONE
                txtIncrement.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.food_item, null, false)
        )
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    fun updateData(mCartItems: ArrayList<CartItem>) {
        cartItems.clear()
        cartItems.addAll(mCartItems)
        notifyDataSetChanged()
    }

    interface OnQuantityChangeListener {
        fun onQuantityChange(menu: Menu, isIncrement: Boolean)
    }
}