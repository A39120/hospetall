package mobile.hospetall.ps.isel.hospetallmobile.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.SP_NAME
import org.json.JSONObject

fun JSONObject.getLinks() : JSONObject{
    return this.getJSONObject("_links")
}

fun JSONObject.getLink(id: String): String? {
    return this.optJSONObject(id)?.optString("href")
}

fun getId(context : Context) : Int{
    val sp = context.getSharedPreferences(SP_NAME, MODE_PRIVATE)
    return sp.getInt(DatabaseColumns.ID, -1)
}

