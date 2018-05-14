package mobile.hospetall.ps.isel.hospetallmobile

import android.app.Application
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class HospetallApplication : Application() {
    companion object {
        const val TAG = "Application"
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "OnCreate called;")
    }

    private val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(this)}
}

val Application.requestQueue : RequestQueue
    get() = (this as HospetallApplication).requestQueue