package mobile.hospetall.ps.isel.hospetallmobile.security

import android.content.SharedPreferences
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.EXPIRATION
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.TOKEN
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils
import org.json.JSONObject
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.HashMap

class OAuthRequest(
        private val sharedPreferences: SharedPreferences,
        finish: ((OAuthToken) -> Unit)?,
        error: Response.ErrorListener? = null,
        private val startTime : Long = Calendar.getInstance().timeInMillis)
    : StringRequest(
        Request.Method.POST,
        UriUtils.getOauthToken().build().toString(),
        Response.Listener {
            val json = JSONObject(it)
            val token = OAuthToken.parse(json, startTime)

            sharedPreferences.edit().apply {
                putLong(EXPIRATION, token.expiresIn)
                putString(TOKEN, token.token)
                commit()
            }

            finish?.invoke(token)
        },
        Response.ErrorListener {
            sharedPreferences.edit().apply {
                remove(EXPIRATION)
                remove(TOKEN)
                commit()
            }
            error?.onErrorResponse(it)
        }) {

    override fun getHeaders(): MutableMap<String, String> {
        val map =  HashMap<String, String>()

        val username = "android_client"
        val password = "android_secret_6749"

        val data  = "$username:$password".toByteArray(Charset.forName("UTF-8"))
        map["Authorization"] = "Basic ${android.util.Base64.encodeToString(data, android.util.Base64.NO_WRAP)}"
        return map
    }

    override fun getParams(): MutableMap<String, String> {
        val map =  HashMap<String, String>()
        map[SharedPrefKeys.USERNAME] = sharedPreferences.getString(SharedPrefKeys.USERNAME,"")
        map[SharedPrefKeys.PASSWORD] = sharedPreferences.getString(SharedPrefKeys.PASSWORD, "")
        map["grant_type"] = "password"
        return map
    }
}
