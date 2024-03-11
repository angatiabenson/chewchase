package com.banit.chewchase.views.order

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.banit.chewchase.adapters.ViewFoodAdapter
import com.banit.chewchase.data.models.UserOrdersWithFoods
import com.banit.chewchase.databinding.ActivityViewOrderBinding
import com.banit.chewchase.utils.formatCurrency

class ViewOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewOrderBinding
    private lateinit var mContext: Context
    private lateinit var viewFoodAdapter: ViewFoodAdapter
    private lateinit var userOrdersWithFoods: UserOrdersWithFoods

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewOrderBinding.inflate(layoutInflater)
        mContext = this
        setContentView(binding.root)

        userOrdersWithFoods = intent.getSerializableExtra("data") as UserOrdersWithFoods

        viewFoodAdapter = ViewFoodAdapter(ArrayList(userOrdersWithFoods.foods))
        binding.recyclerView.adapter = viewFoodAdapter

        binding.txtOrderName.text = userOrdersWithFoods.order.name
        binding.txtTip.text = "$${formatCurrency(userOrdersWithFoods.order.tip.toString())}"
        binding.txtSubTotal.text =
            "$${formatCurrency(userOrdersWithFoods.order.subtotal.toString())}"
        binding.txtTotal.text =
            "$${formatCurrency((userOrdersWithFoods.order.subtotal + userOrdersWithFoods.order.tip).toString())}"

    }
}