package mobile.hospetall.ps.isel.hospetallmobile.utils

import android.content.Context
import android.net.ConnectivityManager
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.ALLOW_ROAMING
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.DEFAULT_ALLOW_ROAMING
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.SP_NAME

object ConnectionUtils {

    fun isConnectionAvailable(context: Context) : Boolean{
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val preference = sp.getBoolean(ALLOW_ROAMING, DEFAULT_ALLOW_ROAMING)

        val actnet = manager.activeNetworkInfo
        return actnet.isAvailable && (actnet.isRoaming == preference || actnet.isConnected)
    }

}