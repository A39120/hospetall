package mobile.hospetall.ps.isel.hospetallmobile.models

import android.arch.persistence.room.ColumnInfo
import mobile.hospetall.ps.isel.hospetallmobile.models.base.Base

/**
 * Procedure open class that will share the same attributes
 * as Consultation and Treatment.This information will come
 * from the api.
 */
open class Procedure(
        uri: String,
        id: Int,

        @ColumnInfo(name=DATE)
        val date: String,

        @ColumnInfo(name= CASE_HISTORY)
        val caseHistory: String?,

        @ColumnInfo(name= DIAGNOSIS)
        val diagnosis: String?,

        @ColumnInfo(name= TREATMENT)
        val treatment: String?,

        @ColumnInfo(name= OBSERVATIONS)
        val observations: String?,

        @ColumnInfo(name= PET_URI)
        val petUri: String
) : Base(uri, id){
    companion object {
        /**
         * Constants that specify the name of a column on the
         * database or key in the JSON format.
         */
        const val DATE = "date"
        const val CASE_HISTORY = "caseHistory"
        const val DIAGNOSIS = "diagnosis"
        const val TREATMENT = "treatment"
        const val OBSERVATIONS = "observations"
        const val PET_URI = "pet_uri"
        const val PETS = "pet"
    }
}

