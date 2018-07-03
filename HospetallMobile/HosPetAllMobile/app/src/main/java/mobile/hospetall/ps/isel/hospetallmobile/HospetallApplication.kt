package mobile.hospetall.ps.isel.hospetallmobile

import android.app.Application
import android.util.Log
import android.util.LruCache
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.utils.RequestQueueSingleton
import mobile.hospetall.ps.isel.hospetallmobile.models.Event

class HospetallApplication : Application() {
    companion object {
        const val TAG = "Application"
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "OnCreate called;")
        requestQueueSingleton = RequestQueueSingleton.getInstance(applicationContext)
    }

    private val cacheSize = 1000

    private lateinit var requestQueueSingleton : RequestQueueSingleton
    val database : MobileDatabase by lazy { MobileDatabase.getInstance(this) }
    val eventCache : LruCache<Int, Event?> by lazy { LruCache<Int, Event?>(cacheSize)}
}

val Application.database
    get() = (this as HospetallApplication).database

val Application.eventCache
    get() = (this as HospetallApplication).eventCache