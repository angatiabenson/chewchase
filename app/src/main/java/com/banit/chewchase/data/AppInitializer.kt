package com.banit.chewchase.data

import android.content.Context
import com.banit.chewchase.R
import com.banit.chewchase.data.entity.Menu
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import javax.inject.Inject

class AppInitializer @Inject constructor(
    private val appDatabase: AppDatabase,
    @ApplicationContext private val context: Context,
    private val dispatcher: CoroutineDispatcher
){
    fun init() {
        CoroutineScope(dispatcher).launch {
            populateMenuIfNeeded()
        }
    }

    private fun populateMenuIfNeeded() {
        // Check if the database is empty and populate it
        if (appDatabase.menuDAO().getAllMenuItems().isEmpty()) {
            val menuList = loadMenuFromJson()
            appDatabase.menuDAO().insertAll(menuList)
        }
    }

    private fun loadMenuFromJson(): List<Menu> {
        val menuList = mutableListOf<Menu>()
        val inputStream = context.resources.openRawResource(R.raw.menu)
        val json = String(inputStream.readBytes())
        val jsonArray = JSONArray(json)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val menuItem = Menu(
                jsonObject.getString("id"),
                jsonObject.getString("name"),
                jsonObject.getString("description"),
                jsonObject.getDouble("price")
            )
            menuList.add(menuItem)
        }

        return menuList
    }
}
