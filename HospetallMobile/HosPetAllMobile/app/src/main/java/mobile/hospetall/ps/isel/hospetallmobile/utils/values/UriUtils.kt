package mobile.hospetall.ps.isel.hospetallmobile.utils.values

import android.content.res.Resources
import android.net.Uri
import mobile.hospetall.ps.isel.hospetallmobile.R


/**
 * File that contains methods that build the uri to the wanted
 * resource representation.
 */
object UriUtils {

    /**
     * Gets base uri, used by the other uri methods.
     * @param resources: contains information on about the
     * api (where it's hosted)  and its security (protocol http or
     * https)
     */
    private fun getBaseUri(resources: Resources): Uri.Builder {
        return Uri.Builder()
                .scheme(resources.getString(R.string.protocol))
                .authority(resources.getString(R.string.authority))
    }

    fun getClientUri(resources: Resources, id: Int) =
            getBaseUri(resources)
                    .appendEncodedPath(resources.getString(R.string.client_path))
                    .appendEncodedPath(id.toString())

    /**
     * Gets the uri for the resource that contains the clients pets.
     * @param id: client id
     */
    fun getClientsPetsUri(resources: Resources, id: Int) =
            getClientUri(resources, id)
                    .appendEncodedPath(resources.getString(R.string.clients_pets_path))

    /**
     * Gets the full pet list. The client shouldn't have authorization
     * for the full pet list therefore this is only used to build other
     * uri.
     */
    fun getPetListUri(resources: Resources): Uri.Builder =
            getBaseUri(resources)
                    .appendPath(resources.getString(R.string.pet_path))

    /**
     * Gets an uri for a representation of [Pet]
     * @param id: wanted pet id
     */
    fun getPetUri(resources: Resources, id: Int): Uri.Builder =
            getPetListUri(resources)
                    .appendEncodedPath(id.toString())

    /**
     * Gets an uri for the representation of all the treatments
     * made by a certain pet.
     * @param id: wanted pet id
     */
    fun getPetTreatmentUri(resources: Resources, id: Int): Uri.Builder =
            getPetUri(resources, id)
                    .appendEncodedPath(resources.getString(R.string.treatment_path))

    /**
     * Gets an uri for the representation of all the consultations
     * made by a certain pet.
     * @param id: wanted pet id
     */
    fun getPetConsultationUri(resources: Resources, id: Int): Uri.Builder =
            getPetUri(resources, id)
                    .appendEncodedPath(resources.getString(R.string.consultation_path))

    /**
     * Gets an uri for the representation of all the consultations
     * made by a all the clients pets.
     * @param id: client id
     */
    fun getPetsConsultationsUri(resources: Resources, id: Int): Uri.Builder =
    //TODO: Wait for this to be fixed
            getClientUri(resources, id)
                    .appendPath(resources.getString(R.string.pet_path))
                    .appendPath(resources.getString(R.string.consultation_path))

    /**
     * Gets an uri for the representation of all the treatments
     * made by a all the clients pets.
     * @param id: client id
     */
    fun getPetsTreatmentUri(resources: Resources, id: Int): Uri.Builder =
//TODO: Wait for this to be fixed
            getClientUri(resources, id)
                    .appendPath(resources.getString(R.string.pet_path))
                    .appendPath(resources.getString(R.string.treatment_path))

    /**
     * Gets an uri for the representation of a [Treatment]
     * @param id: treatments id
     */
    fun getTreatmentUri(resources: Resources, id: Int) =
            getBaseUri(resources)
                    .appendEncodedPath(resources.getString(R.string.treatment_path))
                    .appendEncodedPath(id.toString())

    /**
     * Gets an uri for the representation of a [Consultation] list
     */
    fun getConsultationListUri(resources: Resources) =
            getBaseUri(resources)
                    .appendEncodedPath(resources.getString(R.string.consultation_path))


    /**
     * Gets an uri for the representation of a [Consultation]
     * @param id: consultation id
     */
    fun getConsultationUri(resources: Resources, id: Int) =
            getConsultationListUri(resources)
                    .appendEncodedPath(id.toString())
}