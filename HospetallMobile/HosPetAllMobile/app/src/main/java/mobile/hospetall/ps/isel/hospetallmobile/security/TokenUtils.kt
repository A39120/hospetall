package mobile.hospetall.ps.isel.hospetallmobile.security

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.utils.RequestQueueSingleton
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys
import java.util.*

object TokenUtils {

    /**
     * Defines token for request queue
     *
     * @param error: executable action to run in case of error
     * @param context: the Context to get the SharedPreferences
     */
    fun defineTokenIfExpired(context : Context, finish: (OAuthToken) -> Unit, error: Response.ErrorListener? = null) {
        val sp = context.getSharedPreferences(SharedPrefKeys.SP_NAME, MODE_PRIVATE)
        val expiration = sp.getLong(SharedPrefKeys.EXPIRATION, -1)

        val rqs = RequestQueueSingleton.getInstance()
        if(expiration - Calendar.getInstance().timeInMillis < 0)
            rqs.requestQueue.add( OAuthRequest( sp, finish, error ) )
    }
}