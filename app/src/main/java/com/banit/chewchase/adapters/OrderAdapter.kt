package com.banit.chewchase.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.banit.chewchase.R
import com.banit.chewchase.data.models.UserOrdersWithFoods
import com.banit.chewchase.utils.formatCurrency
import com.google.android.material.card.MaterialCardView

class OrderAdapter(private val orders: ArrayList<UserOrdersWithFoods>) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    lateinit var onOrderClickListener: OnOrderClickListener

    inner class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderCard: MaterialCardView = itemView.findViewById(R.id.card_order)
        private val txtTitle: TextView = itemView.findViewById(R.id.txt_title)
        private val txtFoods: TextView = itemView.findViewById(R.id.txt_food)
        private val txtAmount: TextView = itemView.findViewById(R.id.txt_amount)

        @SuppressLint("SetTextI18n")
        fun bind(userOrdersWithFoods: UserOrdersWithFoods) {
            txtTitle.text = userOrdersWithFoods.order.name
            txtFoods.text = userOrdersWithFoods.foods.joinToString(", ") { it.menu.name }
            txtAmount.text = "$${
                formatCurrency((userOrdersWithFoods.order.subtotal + userOrdersWithFoods.order.tip).toString())
            }"
            orderCard.setOnClickListener {
                onOrderClickListener.onOrderClick(userOrdersWithFoods)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.order_item, null, false)
        )
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    fun updateData(mOrders: List<UserOrdersWithFoods>) {
        orders.clear()
        orders.addAll(mOrders)
        notifyDataSetChanged()
    }


    interface OnOrderClickListener {
        fun onOrderClick(userOrdersWithFoods: UserOrdersWithFoods)
    }
}