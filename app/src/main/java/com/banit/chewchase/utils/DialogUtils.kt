package com.banit.chewchase.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import cn.pedant.SweetAlert.SweetAlertDialog
import com.banit.chewchase.App
import com.banit.chewchase.R



object DialogUtils {

    var isPayed:Boolean = false

    fun toast(message: String) =
        Toast.makeText(App.application.applicationContext, message, Toast.LENGTH_LONG).show()

    fun errorDialog(title: String = "Oops! Error", message: String, context: Context) {
        val dialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(title)
            .setContentText(message)
        dialog.setCancelable(false)
        dialog.show()
    }

    fun errorDialog(
        title: String = "Oops! Error",
        message: String,
        context: Context,
        action: (() -> Unit)
    ) {
        val dialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(title)
            .setContentText(message)
            .setConfirmClickListener {
                it.dismiss()
                action()
            }
        dialog.setCancelable(false)
        dialog.show()
    }

    fun warningDialog(title: String = "Something is not right", message: String, context: Context) {
        val dialog = SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
            .setTitleText(title)
            .setContentText(message)
            .setConfirmText("Okay")
        dialog.setCancelable(false)
        dialog.show()
    }

    fun successDialog(
        title: String = "SUCCESS",
        message: String,
        context: Context,
        action: (() -> Unit)
    ) {
        val dialog = SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText(title)
            .setContentText(message)
            .setConfirmText("Okay")
            .setConfirmClickListener {
                it.dismiss()
                action()
            }
        dialog.setCancelable(false)
        dialog.show()
    }


    fun confirmationDialog(
        title: String = "Confirm",
        message: String,
        mContext: Context,
        actionPositive: (() -> Unit),
        actionNegative: (() -> Unit) = { },
        positiveButtonText:String="Proceed",
        negativeButtonText:String="Cancel"
    ) {
        val alertDialog: AlertDialog
        val alertBuilder: AlertDialog.Builder =
            AlertDialog.Builder(ContextThemeWrapper(mContext, R.style.MyAlertDialogTheme))
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle(title)
        alertBuilder.setMessage(message)
        alertBuilder.setPositiveButton(positiveButtonText) { dialog, _ ->
            run {
                dialog.dismiss()
                actionPositive()
            }
        }
        alertBuilder.setNegativeButton(negativeButtonText) { dialog, _ ->
            dialog.dismiss()
            actionNegative()
        }
        alertDialog = alertBuilder.create()
        alertDialog.show()
    }
}
