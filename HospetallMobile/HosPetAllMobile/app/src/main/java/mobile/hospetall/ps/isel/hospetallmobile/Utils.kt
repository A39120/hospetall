package mobile.hospetall.ps.isel.hospetallmobile

import android.content.Intent
import android.content.res.Resources
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.utils.OnPetListener
import org.json.JSONObject
import java.util.concurrent.Callable
import java.util.function.Consumer

fun JSONObject.getLinks() : JSONObject{
    return this.getJSONObject("_links")
}

fun JSONObject.getLink(id: String): String? {
    return this.optJSONObject(id)?.optString("href")
}

fun getId() = 1