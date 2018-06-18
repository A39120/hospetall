package mobile.hospetall.ps.isel.hospetallmobile.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns
import org.jetbrains.annotations.NotNull

/**
 * Procedure open class that will share the same attributes
 * as Consultation and Treatment.This information will come
 * from the api.
 */
open class Procedure(

        @PrimaryKey
        @ColumnInfo(name= DatabaseColumns.URI)
        val uri: String,

        @NotNull
        @ColumnInfo(name= DatabaseColumns.ID)
        val id: Int,

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
        @ForeignKey(entity=Pet::class,
                parentColumns = [DatabaseColumns.ID],
                childColumns = [PET_URI])
        val petUri: String?
) {
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
        const val PETS = "pets"
    }
}

