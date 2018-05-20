package mobile.hospetall.ps.isel.hospetallmobile

import android.net.Uri


fun getBaseUri(authority: String): Uri.Builder{
    val protocol = "http"
    return Uri.Builder()
            .scheme(protocol)
            .authority(authority)
}
