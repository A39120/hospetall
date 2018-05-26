package mobile.hospetall.ps.isel.hospetallmobile

import android.content.res.Resources
import android.net.Uri


fun getBaseUri(resources: Resources): Uri.Builder{
    return Uri.Builder()
            .scheme(resources.getString(R.string.protocol))
            .authority(resources.getString(R.string.authority))
}

fun getPetUri(resources: Resources, id: Int): Uri.Builder =
        getBaseUri(resources)
                .appendPath("pet")
                .appendEncodedPath(id.toString())

