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
import com.banit.chewchase.data.models.OrderFoodsWithMenu
import com.banit.chewchase.utils.formatCurrency

class ViewFoodAdapter(private val orderFoods: ArrayList<OrderFoodsWithMenu>) :
    RecyclerView.Adapter<ViewFoodAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtQty: TextView = itemView.findViewById(R.id.txt_qty)
        private val txtName: TextView = itemView.findViewById(R.id.txt_name)
        private val txtDescription: TextView = itemView.findViewById(R.id.txt_description)
        private val txtAmount: TextView = itemView.findViewById(R.id.txt_amount)
        private val txtDecrement: TextView = itemView.findViewById(R.id.txt_remove)
        private val txtIncrement: TextView = itemView.findViewById(R.id.txt_add)

        @SuppressLint("SetTextI18n")
        fun bind(orderFoodsWithMenu: OrderFoodsWithMenu) {
            txtQty.text = "${orderFoodsWithMenu.orderFoods.quantity} x"
            txtName.text = orderFoodsWithMenu.menu.name
            txtDescription.text = orderFoodsWithMenu.menu.description
            txtAmount.text =
                "$${formatCurrency((orderFoodsWithMenu.menu.price * orderFoodsWithMenu.orderFoods.quantity).toString())}"
            txtDecrement.visibility = View.GONE
            txtIncrement.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.food_item, null, false)
        )
    }

    override fun getItemCount(): Int = orderFoods.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderFoods[position])
    }

    fun updateData(mOrderFoods: ArrayList<OrderFoodsWithMenu>) {
        orderFoods.clear()
        orderFoods.addAll(mOrderFoods)
        notifyDataSetChanged()
    }
}