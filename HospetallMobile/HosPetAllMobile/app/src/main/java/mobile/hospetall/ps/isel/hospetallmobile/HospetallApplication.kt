package mobile.hospetall.ps.isel.hospetallmobile

import android.app.Application
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase

class HospetallApplication : Application() {
    companion object {
        const val TAG = "Application"
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "OnCreate called;")
    }
    val database : MobileDatabase by lazy { MobileDatabase.getInstance(this) }
    val rQueue: RequestQueue by lazy { Volley.newRequestQueue(this)}

}

val Application.database
    get() = (this as HospetallApplication).database

val Application.requestQueue : RequestQueue
    get() = (this as HospetallApplication).rQueue