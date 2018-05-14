package mobile.hospetall.ps.isel.hospetallmobile

import android.content.res.Resources
import android.net.Uri

fun getBaseUri(resources: Resources): Uri.Builder{

    val protocol = resources.getString(R.string.protocol)
    val authority = resources.getString(R.string.authority)

    return Uri.Builder()
            .scheme(protocol)
            .authority(authority)
}