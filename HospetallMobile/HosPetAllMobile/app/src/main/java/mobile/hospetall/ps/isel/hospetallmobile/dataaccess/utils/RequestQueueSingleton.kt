package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import com.android.volley.toolbox.DiskBasedCache

class RequestQueueImpl {
    val cache = DiskBasedCache(cacheDir)

}