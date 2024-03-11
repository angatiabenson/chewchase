package com.banit.chewchase.utils

import android.content.Context
import android.content.SharedPreferences
import com.banit.chewchase.App

class PrefManager {
    private val PREF_NAME = "digipay"
    private val isLoggedIn = "isLoggedIn"
    private val isFirstTime = "isFirstTime"
    private val sessionToken = "session-token"

    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null


    init {
        pref =
            App.application.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref!!.edit()
    }

    fun getPref(): SharedPreferences? {
        return pref
    }

    fun isLoggedIn(): Boolean {
        return pref!!.getBoolean(isLoggedIn, false)
    }

    fun isBalanceVisible(): Boolean {
        return pref!!.getBoolean("balance_visible", false)
    }

    fun setBalanceVisible(isBalance:Boolean) {
        editor!!.putBoolean("balance_visible", isBalance)
        editor!!.commit()
    }

    fun isFirstTime(): Boolean {
        return pref!!.getBoolean(isFirstTime, true)
    }

    fun setIsFirstTime() {
        editor!!.putBoolean(isFirstTime, false)
        editor!!.commit()
    }

    fun getUserID(): Int {
        return pref!!.getString("user_id", "0")!!.toInt()
    }
    fun getReferralCode(): String {
        return pref!!.getString("ref_code", "XXXXX")!!
    }
    fun setLoggedIn(userID: String) {
        editor!!.putString("user_id", userID)
        editor!!.putBoolean(isLoggedIn, true)
        editor!!.commit()
    }

    fun logout() {
        editor!!.clear()
        editor!!.apply()
    }

    fun updateBalance(balance: String) {
        editor!!.putString("balance", balance)
        editor!!.commit()
    }
}