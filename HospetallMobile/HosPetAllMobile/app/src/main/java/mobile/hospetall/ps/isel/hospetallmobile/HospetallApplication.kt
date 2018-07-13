package mobile.hospetall.ps.isel.hospetallmobile

import android.app.Application
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.utils.RequestQueueSingleton


class HospetallApplication : Application() {
    companion object {
        const val TAG = "Application"
    }

    override fun onCreate() {
        super.onCreate()

        database = MobileDatabase.getInstance(applicationContext)
        requestQueueSingleton = RequestQueueSingleton.getInstance(applicationContext)
    }

    //val executors by lazy { AppExecutors() }
    private lateinit var requestQueueSingleton : RequestQueueSingleton
    private lateinit var database : MobileDatabase
    //val eventCache : LruCache<Int, Event?> by lazy { LruCache<Int, Event?>(cacheSize)}
}
