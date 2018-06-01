package mobile.hospetall.ps.isel.hospetallmobile

import android.content.res.Resources
import android.net.Uri


fun getBaseUri(resources: Resources): Uri.Builder{
    return Uri.Builder()
            .scheme(resources.getString(R.string.protocol))
            .authority(resources.getString(R.string.authority))
}

fun getClientsPetsUri(resources: Resources, clientId: Int) =
        getBaseUri(resources)
                .appendEncodedPath(resources.getString(R.string.client_path))
                .appendEncodedPath(clientId.toString())
                .appendEncodedPath(resources.getString(R.string.clients_pets_path))

fun getPetUri(resources: Resources, id: Int): Uri.Builder =
        getBaseUri(resources)
                .appendPath(resources.getString(R.string.pet_path))
                .appendEncodedPath(id.toString())

fun getConsultationUri(resources: Resources, id : Int) =
        getBaseUri(resources)
                .appendEncodedPath(resources.getString(R.string.consultation_path))
                .appendEncodedPath(id.toString())