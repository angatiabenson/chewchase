package com.banit.chewchase.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.banit.chewchase.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun AppCompatActivity.loadActivity(
    mContext: Context,
    cls: Class<*>?,
    dataObject: HashMap<String, java.io.Serializable>? = null,
    dataString: HashMap<String, String>? = null
) {
    val intent = Intent(mContext, cls)
    if (!dataObject.isNullOrEmpty()) {
        for (key in dataObject.keys) {
            intent.putExtra(key, dataObject[key])
        }
    }

    if (!dataString.isNullOrEmpty()) {
        for (key in dataString.keys) {
            intent.putExtra(key, dataString[key])
        }
    }
    startActivity(intent)
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

fun Fragment.loadActivity(
    mActivity:Activity,
    mContext: Context,
    cls: Class<*>?,
    dataObject: HashMap<String, java.io.Serializable>? = null,
    dataString: HashMap<String, String>? = null
) {
    val intent = Intent(mContext, cls)
    if (!dataObject.isNullOrEmpty()) {
        for (key in dataObject.keys) {
            intent.putExtra(key, dataObject[key])
        }
    }

    if (!dataString.isNullOrEmpty()) {
        for (key in dataString.keys) {
            intent.putExtra(key, dataString[key])
        }
    }
    startActivity(intent)
    mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

fun addZeroBefore(number: Int): String {
    return if (number < 10) {
        "0$number"
    } else {
        number.toString()
    }
}

fun checkAndRequestPermissions(context: Context?, activity: Activity?) {
    val permissionStorage =
        ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val permissionReadStorage =
        ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
    val permissionFineLocation =
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
    val permissionCoarseLocation =
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)


    val listPermissionsNeeded: MutableList<String> = ArrayList()
    if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
    if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    if (permissionCoarseLocation != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
    }
    if (permissionFineLocation != PackageManager.PERMISSION_GRANTED) {
        listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    if (listPermissionsNeeded.isNotEmpty()) {
        ActivityCompat.requestPermissions(activity!!, listPermissionsNeeded.toTypedArray(), 1001)
    }
}

fun formatCurrency(number: String): String {
    val doubleNumber = number.toDouble()
    val formatter = DecimalFormat("#,###.00")
    return formatter.format(doubleNumber)
}

@SuppressLint("SimpleDateFormat")
fun calculateTimeIntervals(date: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val previousDate = dateFormat.parse(date)
    val currentDate = Date()

    val interval = ((currentDate.time - (previousDate?.time!!)) / (24 * 60 * 60 * 1000)).toInt()

    return if (interval == 0)
        "today"
    else if (interval == 1)
        "yesterday"
    else if (interval < 7)
        "$interval days ago"
    else if (interval < 30)
        "${interval / 7} week(s) ago"
    else
        "${(interval / 30)} months ago"
}

@SuppressLint("SimpleDateFormat")
fun calculateDateDifference(date: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val previousDate = dateFormat.parse(date)
    val currentDate = Date()

    val days = ((previousDate?.time!! - currentDate.time) / (24 * 60 * 60 * 1000)).toInt()

    return if (days < 0)
        "0"
    else
        return days.toString()
}

fun formatPhoneNumber(phoneNumber: String): String {
    if (!isKenyanNumber(phoneNumber))
        return ""

    if (phoneNumber.length == 10 && phoneNumber.startsWith("0")) {
        return phoneNumber.replaceFirst("^0".toRegex(), "254")
    }

    return if (phoneNumber.length == 13 && phoneNumber.startsWith("+")) phoneNumber.replaceFirst(
        "^+".toRegex(),
        ""
    ) else phoneNumber

}

fun isKenyanNumber(number: String): Boolean {
    //(\+254|^){1}[ ]?[7]{1}([0-3]{1}[0-9]{1})[ ]?[0-9]{3}[ ]?[0-9]{3}\z
    val pattern = Regex("^(\\+254|254|0)[1-9]\\d{8}\$")
    return pattern.matches(number)
}

fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex("[a-zA-Z\\d._%+-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}")
    return emailRegex.matches(email)
}

fun getFileNameFromUri(uri: Uri, contentResolver: ContentResolver): String? {
    val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
    val cursor = contentResolver.query(uri, projection, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val displayNameIndex = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            return it.getString(displayNameIndex)
        }
    }
    return null
}


fun hideDigits(parameter: String): String {
    return if (parameter.matches(Regex("\\d+"))) {
        val hiddenLength = 3
        val visibleLength = parameter.length - hiddenLength * 2
        val visibleDigits = parameter.substring(0, visibleLength)
        val hiddenDigits = "*".repeat(hiddenLength)
        val visibleEndDigits = parameter.substring(parameter.length - visibleLength)
        "$visibleDigits$hiddenDigits$visibleEndDigits"
    } else {
        parameter
    }
}

fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())

    val date = inputFormat.parse(dateString)
    return date?.let { outputFormat.format(it) }!!
}

fun generateAcronym(name: String): String {
    val words = name.split(" ")
    val acronym = StringBuilder()

    for (word in words) {
        if (word.isNotEmpty()) {
            acronym.append(word[0].uppercaseChar())
        }
    }
    return acronym.toString()
}

fun formatTextWithBullets(textView: TextView, items: List<String>) {
    val bulletGapWidth = 20 // Adjust the bullet gap width as per your preference

    val bulletSpan = BulletSpan(bulletGapWidth)
    val builder = SpannableStringBuilder()

    for (item in items) {
        val bulletItem = "\u2022 $item" // Unicode character for bullet (U+2022)
        builder.append(bulletItem)
        builder.setSpan(bulletSpan, builder.length - item.length, builder.length, 0)
        builder.append("\n")
    }

    textView.text = builder
    textView.text = builder
}




