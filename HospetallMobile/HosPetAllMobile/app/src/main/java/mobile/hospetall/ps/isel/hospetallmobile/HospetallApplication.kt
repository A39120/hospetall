package mobile.hospetall.ps.isel.hospetallmobile

import android.app.Application
import android.content.Context
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.utils.RequestQueueSingleton
import mobile.hospetall.ps.isel.hospetallmobile.security.TokenUtils
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys


class HospetallApplication : Application() {
    companion object {
        const val TAG = "Application"
    }

    override fun onCreate() {
        super.onCreate()

        database = MobileDatabase.getInstance(applicationContext)
        requestQueueSingleton = RequestQueueSingleton.getInstance(applicationContext)

        val sp = getSharedPreferences(SharedPrefKeys.SP_NAME, Context.MODE_PRIVATE)
        if(sp.getLong(SharedPrefKeys.EXPIRATION, -1) <= 0)
            TokenUtils.defineTokenIfExpired(applicationContext, { })

    }

    //val executors by lazy { AppExecutors() }
    private lateinit var requestQueueSingleton : RequestQueueSingleton
    private lateinit var database : MobileDatabase
    //val eventCache : LruCache<Int, Event?> by lazy { LruCache<Int, Event?>(cacheSize)}
}
