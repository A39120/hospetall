package mobile.hospetall.ps.isel.hospetallmobile.utils

import org.json.JSONObject

fun JSONObject.getLinks() : JSONObject{
    return this.getJSONObject("_links")
}

fun JSONObject.getLink(id: String): String? {
    return this.optJSONObject(id)?.optString("href")
}

fun getId() = 1