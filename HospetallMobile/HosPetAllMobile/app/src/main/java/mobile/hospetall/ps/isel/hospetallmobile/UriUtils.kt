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

fun getPetListUri(resources: Resources) : Uri.Builder =
        getBaseUri(resources)
                .appendPath(resources.getString(R.string.pet_path))

fun getRaceUri(resources: Resources): Uri.Builder =
        getBaseUri(resources)
                .appendEncodedPath(resources.getString(R.string.race_path))

fun getSpeciesUri(resources: Resources): Uri.Builder =
        getBaseUri(resources)
                .appendEncodedPath(resources.getString(R.string.species_path))


fun getPetUri(resources: Resources, id: Int): Uri.Builder =
        getPetListUri(resources)
                .appendEncodedPath(id.toString())

fun getPetTreatmentUri(resources: Resources, id: Int): Uri.Builder =
        getPetUri(resources, id)
                .appendEncodedPath(resources.getString(R.string.treatment_path))

fun getPetConsultationUri(resources: Resources, id: Int): Uri.Builder =
        getPetUri(resources, id)
                .appendEncodedPath(resources.getString(R.string.consultation_path))

fun getPetsConsultationsUri(resources: Resources, id: Int): Uri.Builder =
        //TODO: Wait for this to be fixed
        getBaseUri(resources)
                .appendEncodedPath(resources.getString(R.string.client_path))
                .appendEncodedPath(id.toString())
                .appendPath(resources.getString(R.string.pet_path))
                .appendPath(resources.getString(R.string.consultation_path))

fun getPetsTreatmentUri(resources: Resources, id: Int): Uri.Builder =
//TODO: Wait for this to be fixed
        getBaseUri(resources)
                .appendEncodedPath(resources.getString(R.string.client_path))
                .appendEncodedPath(id.toString())
                .appendPath(resources.getString(R.string.pet_path))
                .appendPath(resources.getString(R.string.treatment_path))


fun getTreatmentUri(resources: Resources, id : Int) =
        getBaseUri(resources)
                .appendEncodedPath(resources.getString(R.string.treatment_path))
                .appendEncodedPath(id.toString())

fun getConsultationListUri(resources: Resources) =
        getBaseUri(resources)
                .appendEncodedPath(resources.getString(R.string.consultation_path))



fun getConsultationUri(resources: Resources, id : Int) =
        getConsultationListUri(resources)
                .appendEncodedPath(id.toString())