package com.banit.chewchase.views

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.activity.addCallback
import com.banit.chewchase.R
import com.banit.chewchase.utils.PrefManager
import com.banit.chewchase.utils.loadActivity
import com.banit.chewchase.views.auth.SignInActivity
import com.banit.chewchase.views.home.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mContext = this
        val prefManager = PrefManager()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            if (prefManager.isLoggedIn()) {
                loadActivity(mContext, MainActivity::class.java)
            } else {
                    loadActivity(mContext, SignInActivity::class.java)
            }
            finish()
        }, 3000)

        onBackPressedDispatcher.addCallback(this /* lifecycle owner */) {
        }
    }
}