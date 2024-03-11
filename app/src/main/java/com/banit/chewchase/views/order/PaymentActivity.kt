package com.banit.chewchase.views.order

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.banit.chewchase.databinding.ActivityPaymentBinding
import com.banit.chewchase.utils.DialogUtils
import com.banit.chewchase.utils.formatCurrency

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPaymentBinding
    private lateinit var mContext:Context

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        mContext = this
        setContentView(binding.root)

        val amount = intent.getStringExtra("amount")!!
        binding.btnPay.text = "Pay Now $${formatCurrency(amount)}"
        binding.btnPay.setOnClickListener {
            pay()
        }

        binding.edCardNumber.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                // Avoid re-triggering afterTextChanged method
                if (isFormatting) return
                isFormatting = true

                val cleaned = StringBuilder(s.toString().replace(" ", ""))
                val formatted = StringBuilder()

                for (i in cleaned.indices) {
                    if (i != 0 && i % 4 == 0) {
                        formatted.append(" ")
                    }
                    formatted.append(cleaned[i])
                }

                s?.replace(0, s.length, formatted)
                isFormatting = false
            }
        })

        binding.edExpiryDate.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                // Avoid re-triggering afterTextChanged method
                if (isFormatting) return
                isFormatting = true

                val cleaned = StringBuilder(s.toString().replace("/", ""))
                val formatted = StringBuilder()

                for (i in cleaned.indices) {
                    if (i != 0 && i % 2 == 0) {
                        formatted.append("/")
                    }
                    formatted.append(cleaned[i])
                }

                s?.replace(0, s.length, formatted)
                isFormatting = false
            }

        })
    }

    private fun pay() {

        if (binding.edCardNumber.text.toString().isEmpty()){
            binding.edCardNumberLayout.error="Card number required!"
            return
        }

        if (binding.edExpiryDate.text.toString().isEmpty()){
            binding.edExpiryDateLayout.error="Expiry date required!"
            return
        }

        if (binding.edCvc.text.toString().isEmpty()){
            binding.edCvcLayout.error="CVC required!"
            return
        }

        DialogUtils.isPayed = true
        finish()
    }
}