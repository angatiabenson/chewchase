package com.banit.chewchase.views.order

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.banit.chewchase.adapters.CartAdapter
import com.banit.chewchase.data.entity.Menu
import com.banit.chewchase.data.entity.Order
import com.banit.chewchase.data.entity.OrderFoods
import com.banit.chewchase.databinding.ActivityNewOrderBinding
import com.banit.chewchase.utils.CartManager
import com.banit.chewchase.utils.DialogUtils
import com.banit.chewchase.utils.PrefManager
import com.banit.chewchase.utils.formatCurrency
import com.banit.chewchase.utils.loadActivity
import com.banit.chewchase.views.BarcodeScannerActivity
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class NewOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewOrderBinding
    private lateinit var mContext: Context
    private val viewModel: OrderViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartManager: CartManager

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            val originalIntent = result.originalIntent
            if (originalIntent == null) {
                Toast.makeText(mContext, "Cancelled", Toast.LENGTH_LONG).show()
            } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                Timber.tag("MainActivity").d("Cancelled scan due to missing camera permission")
                Toast.makeText(
                    mContext,
                    "Cancelled due to missing camera permission",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            fetchMenuItems(result.contents)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewOrderBinding.inflate(layoutInflater)
        mContext = this
        setContentView(binding.root)

        binding.btnAddFood.setOnClickListener {
            val options = ScanOptions().setOrientationLocked(false).setCaptureActivity(
                BarcodeScannerActivity::class.java
            )
            barcodeLauncher.launch(options)
        }

        val stamp = Date().time.toString()
        binding.edName.setText("Order$stamp")

        cartAdapter = CartAdapter(arrayListOf())
        cartAdapter.onQuantityChangeListener = object : CartAdapter.OnQuantityChangeListener {
            override fun onQuantityChange(menu: Menu, isIncrement: Boolean) {
                if (isIncrement) {
                    cartManager.addMenuItemToCart(menu)
                } else {
                    cartManager.removeMenuItemFromCart(menu)
                }
                updateUI()
            }
        }
        binding.recyclerView.adapter = cartAdapter
        cartManager = CartManager()

        DialogUtils.isPayed = false

        binding.btnContinue.setOnClickListener {
            if (cartManager.cartItems.isNotEmpty()){
                val data = HashMap<String, String>()
                data["amount"] = cartManager.calculateTotal().toString()
                loadActivity(mContext, PaymentActivity::class.java, dataString = data)
            }else{
                DialogUtils.toast("Scan at least one menu item")
            }
        }
    }

    private fun fetchMenuItems(menuID: String) {
        viewModel.fetchMenuItemById(menuID).observe(this, Observer { menu ->
            if (menu != null) {
                cartManager.addMenuItemToCart(menu)
                updateUI()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    fun updateUI() {
        if (cartManager.cartItems.isNotEmpty())
            binding.emptyLayout.visibility=View.GONE

        cartAdapter.updateData(cartManager.cartItems)

        binding.txtTip.text = "$${formatCurrency((cartManager.calculateTip()).toString())}"
        binding.txtSubTotal.text =
            "$${formatCurrency((cartManager.calculateSubtotal()).toString())}"
        binding.txtTotal.text = "$${formatCurrency((cartManager.calculateTotal()).toString())}"
    }

    private fun placeOrder() {
        val orderName = binding.edName.text.toString()
        val cartItems = cartManager.cartItems
        val subtotal = cartManager.calculateSubtotal()
        val tip = cartManager.calculateTip()
        val userId = PrefManager().getUserID()
        val orderDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())

        val order = Order(
            userId = userId,
            name = orderName,
            subtotal = subtotal,
            tip = tip,
            orderDate = orderDate
        )
        val orderFoods = cartItems.map { cartItem ->
            OrderFoods(menuItem = cartItem.menu.id, quantity = cartItem.quantity)
        }

        viewModel.placeOrder(order, orderFoods)
        viewModel.orderPlaced.observe(this) { isOrderPlaced ->
            if (isOrderPlaced) {
                Toast.makeText(this, "Order Placed Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (DialogUtils.isPayed)
            placeOrder()
    }
}