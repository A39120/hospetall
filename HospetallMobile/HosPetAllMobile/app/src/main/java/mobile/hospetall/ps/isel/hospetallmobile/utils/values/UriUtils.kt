package mobile.hospetall.ps.isel.hospetallmobile.utils.values

import android.net.Uri


/**
 * File that contains methods that build the uri to the wanted
 * resource representation.
 */
object UriUtils {
    private const val PROTOCOL = "http"
    private const val AUTHORITY = "192.168.1.84"
    private const val CLIENT_PATH = "client"
    private const val PETS_PATH = "pets"
    private const val PET_PATH = "pet"
    private const val TREATMENT_PATH = "treatment"
    private const val CONSULTATION_PATH = "consultation"

    /**
     * Gets base uri, used by the other uri methods.
     * @param resources: contains information on about the
     * api (where it's hosted)  and its security (protocol http or
     * https)
     */
    private fun getBaseUri(): Uri.Builder {
        return Uri.Builder()
                .scheme(PROTOCOL)
                .authority(AUTHORITY)
    }

    fun getClientUri(id: Int) =
            getBaseUri()
                    .appendEncodedPath(CLIENT_PATH)
                    .appendEncodedPath(id.toString())

    /**
     * Gets the uri for the resource that contains the clients pets.
     * @param id: client id
     */
    fun getClientsPetsUri(id: Int) =
            getClientUri(id)
                    .appendEncodedPath(PETS_PATH)

    /**
     * Gets the full pet list. The client shouldn't have authorization
     * for the full pet list therefore this is only used to build other
     * uri.
     */
    private fun getPetListUri(): Uri.Builder =
            getBaseUri()
                    .appendPath(PET_PATH)

    /**
     * Gets an uri for a representation of [Pet]
     * @param id: wanted pet id
     */
    fun getPetUri(id: Int): Uri.Builder =
            getPetListUri()
                    .appendEncodedPath(id.toString())

    /**
     * Gets an uri for the representation of all the treatments
     * made by a certain pet.
     * @param id: wanted pet id
     */
    fun getPetTreatmentUri(id: Int): Uri.Builder =
            getPetUri(id)
                    .appendEncodedPath(TREATMENT_PATH)

    /**
     * Gets an uri for the representation of all the consultations
     * made by a certain pet.
     * @param id: wanted pet id
     */
    fun getPetConsultationUri(id: Int): Uri.Builder =
            getPetUri(id)
                    .appendEncodedPath(CONSULTATION_PATH)

    /**
     * Gets an uri for the representation of all the consultations
     * made by a all the clients pets.
     * @param id: client id
     */
    fun getPetsConsultationsUri(id: Int): Uri.Builder =
            getClientUri(id)
                    .appendPath(PET_PATH)
                    .appendPath(CONSULTATION_PATH)

    /**
     * Gets an uri for the representation of all the treatments
     * made by a all the clients pets.
     * @param id: client id
     */
    fun getPetsTreatmentUri(id: Int): Uri.Builder =
            getClientUri(id)
                    .appendPath(PET_PATH)
                    .appendPath(TREATMENT_PATH)

    /**
     * Gets an uri for the representation of a [Treatment]
     * @param id: treatments id
     */
    fun getTreatmentUri(id: Int) =
            getBaseUri()
                    .appendEncodedPath(TREATMENT_PATH)
                    .appendEncodedPath(id.toString())

    /**
     * Gets an uri for the representation of a [Consultation] list
     */
    fun getConsultationListUri() =
            getBaseUri()
                    .appendEncodedPath(CONSULTATION_PATH)


    /**
     * Gets an uri for the representation of a [Consultation]
     * @param id: consultation id
     */
    fun getConsultationUri(id: Int) =
            getConsultationListUri()
                    .appendEncodedPath(id.toString())
}