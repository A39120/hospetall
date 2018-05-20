package mobile.hospetall.ps.isel.hospetallmobile

import org.json.JSONObject

fun JSONObject.getLinks() : JSONObject{
    return this.getJSONObject("_links")
}

fun JSONObject.getLink(id: String): String{
    return this.getJSONObject(id).getString("href")
}