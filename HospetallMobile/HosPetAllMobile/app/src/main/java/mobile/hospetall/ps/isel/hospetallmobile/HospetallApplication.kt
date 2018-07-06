package mobile.hospetall.ps.isel.hospetallmobile

import android.app.Application
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.utils.RequestQueueSingleton

class HospetallApplication : Application() {
    companion object {
        const val TAG = "Application"
    }

    private val cacheSize = 1000

    //val executors by lazy { AppExecutors() }
    val requestQueueSingleton by lazy { RequestQueueSingleton.getInstance(applicationContext) }
    val database by lazy { MobileDatabase.getInstance(applicationContext) }
    //val eventCache : LruCache<Int, Event?> by lazy { LruCache<Int, Event?>(cacheSize)}
}

/*
val Application.executors
    get() = (this as HospetallApplication).executors
*/

/*
val Application.eventCache
    get() = (this as HospetallApplication).eventCache
*/